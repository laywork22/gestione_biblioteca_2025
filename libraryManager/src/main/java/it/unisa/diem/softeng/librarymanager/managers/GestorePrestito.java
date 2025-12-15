package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.StatoPrestitoEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Gruppo 12
 * @brief Classe specifica per la gestione delle entità Prestito.
 * <p>
 * Questa classe implementa i metodi relativi a la logica di formazione
 * della ObservableList di Prestito.
 */
public class GestorePrestito implements Gestore<Prestito>, Serializable {
    private ObservableList<Prestito> prestitoList;

    public GestorePrestito() {
        this.prestitoList = FXCollections.observableArrayList();
    }

    @Override
    public void setLista(List<Prestito> list) {
        prestitoList = FXCollections.observableArrayList(list);
    }

    /**
     * @param l il prestito da aggiungere o aggiornare
     * @brief Aggiunge un libro alla lista.
     * Implementazione specifica per i Prestiti: controlla che un Utente non abbia più di tre prestiti attivi, in caso
     * contrario, il prestito viene rifiutato e l'operazione di inserimento annullata.
     * Sono ammessi più prestiti di uno stesso libro (copie disponibili permettendo) a nome di un unico utente.
     *
     * @pre l != null
     * @pre utente.getCountPrestiti < 3
     * @pre utente.getCountPrestiti > 0
     * @pre libro.isAttivo() && libro.getCopieDisponibili > 0
     *
     * @post prestitoList.size = prestitoList.size + 1
     * @post  utente.getCountPrestiti =  utente.getCountPrestiti + 1
     * @post libro.getCopieDisponibili = libro.getCopieDiponibili - 1
     *
     * @throws PrestitoException
     *
     * @see Gestore#add(Object)
     */
    @Override
    public void add(Prestito l) throws PrestitoException {
        if (l == null) {
            return;
        }
        if (l.getUtente().getCountPrestiti() >= 3) {
            throw new PrestitoException("L'utente ha raggiunto il limite dei 3 prestiti");
        }
        if (l.getLibro().getCopieDisponibili() <= 0) {
            throw new PrestitoException("Non ci sono copie disponibili per il libro: " + l.getLibro().getTitolo());
        }
        if (!l.getLibro().isAttivo()) {
            throw new PrestitoException("Il libro risulta non attivo");
        }
        if (!l.getUtente().isAttivo()) {
            throw new PrestitoException("L'utente risulta non attivo");
        }
        l.getUtente().setCountPrestiti(l.getUtente().getCountPrestiti() + 1);
        l.getLibro().decrementaCopie();
        prestitoList.add(l);
    }

    /**
     *
     * @brief Rimuove logicamente un prestito (lo marca come chiuso)
     *
     * @pre l != null
     * @pre l.getStato != CHIUSO
     *
     * @post l.getStato = CHIUSO
     * @post l.getUtente().getCountPrestiti = l.getUtente().getCountPrestiti
     * @post l.getLibro().incrementaCopie(l.getLibro().getCopieDisponibli() + 1)
     * @post listaPrestiti.size() invariata
     *
     * @param l
     * @throws PrestitoException
     */
    @Override
    public void remove(Prestito l) throws PrestitoException {
        if (l == null) return;

        if (l.getStato() == StatoPrestitoEnum.CHIUSO) {
            throw new PrestitoException("Lo stato del prestito è chiuso");
        }

        l.getUtente().setCountPrestiti(l.getUtente().getCountPrestiti() - 1);
        l.getLibro().setCopieDisponibili(l.getLibro().getCopieDisponibili() + 1);

        l.setStato(StatoPrestitoEnum.CHIUSO);
    }

    @Override
    public ObservableList<Prestito> getLista() {
        return this.prestitoList;
    }

    /**
     *
     * @brief Permette di modificare un prestito esistente
     *
     * @pre listaPrestiti.contains(vecchio) = true
     * @pre vecchio.getStato() != CHIUSO
     * @pre nuovo != null
     *
     * @post vecchio aggiornato con i valori di nuovo
     * @post listaPrestiti.size() invariata
     *
     * @param vecchio
     * @param nuovo
     * @throws PrestitoException
     */
    @Override
    public void modifica(Prestito vecchio, Prestito nuovo) throws PrestitoException {
        int index = prestitoList.indexOf(vecchio);
        if (vecchio.getStato() == StatoPrestitoEnum.CHIUSO) {
            throw new PrestitoException("Lo stato del prestito è chiuso");
        }
        if (index != -1) {
            if (nuovo != null) {
                vecchio.setStato(nuovo.getStato());
                vecchio.setUtente(nuovo.getUtente());
                vecchio.setLibro(nuovo.getLibro());
                vecchio.setDataFine(nuovo.getDataFine());
                vecchio.setDataInizio(nuovo.getDataInizio());
                prestitoList.set(index, vecchio);
            }

        }
    }


    /**
     * @param str La stringa da filtrare nella tabella per la ricerca
     * @return Predicate associato alla ricerca attuale
     * @brief Ottiene il Predicate corrispondente per la ricerca filtrata di un Prestito
     * @see Gestore#getPredicato(String)
     */
    @Override
    public Predicate<Prestito> getPredicato(String str) {
        return prestito -> {
            if (str == null || str.isEmpty()) return true;
            String filtro = str.toLowerCase();

            // Cerca per Cognome Utente o Titolo Libro prestato
            return prestito.getUtente().getCognome().toLowerCase().contains(filtro) ||
                    prestito.getLibro().getTitolo().toLowerCase().contains(filtro) ||
                    prestito.getUtente().getNome().toLowerCase().contains(filtro);
        };
    }

    /**
     * @brief Aggiorna lo stato dei prestiti in base alla data corrente.
     * Scorre la lista dei prestiti. Se un prestito è ancora ATTIVO ma la data di scadenza
     * è precedente a oggi, lo stato viene cambiato in SCADUTO.
     * I prestiti già CHIUSI vengono ignorati.
     *
     * @post Se stato != CHIUSO aggiorna lo stato a SCADUTO se dataFine è precedente
     * alla data odierna, altrimenti ATTIVO.
     */
    public void aggiornaStati() {
        LocalDate oggi = LocalDate.now();

        for (Prestito p : prestitoList) {
            if (p.getStato() == StatoPrestitoEnum.CHIUSO) {
                continue;
            }

            if (p.getDataFine().isBefore(oggi)) {
                p.setStato(StatoPrestitoEnum.SCADUTO);
            } else {
                p.setStato(StatoPrestitoEnum.ATTIVO);
            }
        }
    }
}
