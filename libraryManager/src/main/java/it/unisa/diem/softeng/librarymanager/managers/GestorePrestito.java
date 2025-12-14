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
 * @brief Classe specifica per la gestione delle entità Prestito.
 *
 *Questa classe implementa i metodi relativi a la logica di formazione
 *della ObservableList di Prestito.
 * @author Gruppo 12
 */
public class GestorePrestito implements Gestore<Prestito>, Serializable{
    private  ObservableList<Prestito> prestitoList;

    public GestorePrestito() {
        this.prestitoList = FXCollections.observableArrayList();
    }

    @Override
    public void setLista(List<Prestito> list) {
        prestitoList = FXCollections.observableArrayList(list);
    }

    /**
     * @brief Aggiunge un libro alla lista.
     * Implementazione specifica per i Prestiti: controlla che un Utente non abbia più di tre prestiti attivi, in caso
     * contrario, il prestito viene rifiutato e l'operazione di inserimento annullata.
     * Sono ammessi più prestiti di uno stesso libro (copie disponibili permettendo) a nome di un unico utente..
     *
     * @param l il prestito da aggiungere o aggiornare
     * @see Gestore#add(Object)
     */
    @Override
    public void add(Prestito l) throws PrestitoException {
        if(l==null){
            return;
        }
        if(l.getUtente().getCountPrestiti()>=3){
            throw new PrestitoException("L'utente ha raggiunto il limite dei 3 prestiti");
        }
        if (l.getLibro().getCopieDisponibili() <= 0) {
            throw new PrestitoException("Non ci sono copie disponibili per il libro: " + l.getLibro().getTitolo());
        }
        if(!l.getLibro().isAttivo()){
            throw new PrestitoException("Il libro risulta non attivo");
        }
        if(!l.getUtente().isAttivo()){
            throw new PrestitoException("L'utente risulta non attivo");
        }
        l.getUtente().setCountPrestiti(l.getUtente().getCountPrestiti()+1);
        l.getLibro().decrementaCopie();
        prestitoList.add(l);
    }

    @Override
    public void remove(Prestito l) throws PrestitoException{
        if(l == null) return;

        if (l.getStato() == StatoPrestitoEnum.CHIUSO) {
            throw new PrestitoException("Lo stato del prestito è chiuso");
        }

        l.getUtente().setCountPrestiti(l.getUtente().getCountPrestiti() - 1);
        l.getLibro().setCopieDisponibili(l.getLibro().getCopieDisponibili() + 1);

        l.setStato(StatoPrestitoEnum.CHIUSO);
    }

    @Override
    public ObservableList<Prestito> getLista(){
        return this.prestitoList;
    }

    @Override
    public void modifica(Prestito vecchio, Prestito nuovo) throws  PrestitoException {
        int index = prestitoList.indexOf(vecchio);
        if(vecchio.getStato()==StatoPrestitoEnum.CHIUSO){
            throw new PrestitoException("Lo stato del prestito è chiuso");
        }
        if (index != -1) {
            vecchio.setStato(nuovo.getStato());
            vecchio.setUtente(nuovo.getUtente());
            vecchio.setLibro(nuovo.getLibro());
            vecchio.setDataFine(nuovo.getDataFine());
            vecchio.setDataInizio(nuovo.getDataInizio());
            prestitoList.set(index, vecchio);
        }
    }


    /** @brief Ottiene il Predicate corrispondente per la ricerca filtrata di un Prestito
     *
     * @param str La stringa da filtrare nella tabella per la ricerca
     *
     * @return Predicate associato alla ricerca attuale
     *
     * @see Gestore#getPredicato(String)
     */
    @Override
    public Predicate<Prestito> getPredicato(String str) {
        return prestito -> {
            if (str == null || str.isEmpty()) return true;
            String filtro = str.toLowerCase();

            // Cerca per Cognome Utente o Titolo Libro prestato
            return prestito.getUtente().getCognome().toLowerCase().contains(filtro) ||
                    prestito.getLibro().getTitolo().toLowerCase().contains(filtro)||
                    prestito.getUtente().getNome().toLowerCase().contains(filtro);
        };
    }

    /** @brief Inizializza il gestore con la lista di osservabili dei prestiti caricata dal file
     *
     * @pre Il file deve esistere
     *
     * @post La lista è caricata in memoria nel GestorePrestito
     *
     * @return GestoreUtente con listaUtente non vuota
     *
     * @param nomeFile Il nome del file da cui caricare la lista
     */
    public static GestorePrestito caricaListaPrestiti(String nomeFile) {
        GestorePrestito gp = new GestorePrestito();


        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(Paths.get(nomeFile))))) {
            List<Prestito> listaCaricata = (List<Prestito>) ois.readObject();

            gp.setLista(listaCaricata);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return gp;
    }

    /**
     * @brief Aggiorna lo stato dei prestiti in base alla data corrente.
     * Scorre la lista dei prestiti. Se un prestito è ancora ATTIVO ma la data di scadenza
     * è precedente a oggi, lo stato viene cambiato in SCADUTO.
     * I prestiti già CHIUSI vengono ignorati.
     */
    public void aggiornaStati() {
        LocalDate oggi = LocalDate.now();

        for (Prestito p : prestitoList) {
            if (p.getStato() == StatoPrestitoEnum.CHIUSO) {
                continue;
            }

            if (p.getDataFine().isBefore(oggi)) {
                p.setStato(StatoPrestitoEnum.SCADUTO);
            }
            else {
                p.setStato(StatoPrestitoEnum.ATTIVO);
            }
        }
    }
}
