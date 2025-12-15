package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.exceptions.LibroException;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.function.Predicate;
/**
 * @brief Classe specifica per la gestione delle entità Libro.
 *
 *Questa classe implementa i metodi relativi a la logica di formazione
 *della ObservableList di Libro.
 * @author Gruppo 12
 */
public class GestoreLibro implements Gestore<Libro> {
    private ObservableList<Libro> listaLibri;

    public GestoreLibro() {
        this.listaLibri = FXCollections.observableArrayList();
    }


    @Override
    public void setLista(List<Libro> l) {
        listaLibri = FXCollections.observableArrayList(l);
    }

    /**
     * @brief Aggiunge un libro alla lista.
     * Implementazione specifica per i Libri: controlla se il Libro è già presente
     * (tramite ISBN).
     * Se è attivo, allora saranno aggiunte le copie totali a quelle dell'istanza preesistente.
     * Se non è attivo sarà riattivata con i dati inseriti nel form.
     * Se non è già presente allora viene aggiunto all'archivio.
     *
     * @pre l != null
     * @pre l.getCopieTotali() >= 0
     *
     * @post Se nuovo: listaLibri.size() = listaLibri.size() + 1
     * @post Se esistente attivo: l.getCopieTotali() = vecchieCopie + nuoveCopie
     * @post Se esistente non attivo: l.isAttivo() = true
     *
     * @param l il Libro da aggiungere o aggiornare
     *
     * @throws LibroException
     * @see Gestore#add(Object)
     */
    @Override
    public void add(Libro l) throws LibroException {
        if (l == null) return;

        if (l.getCopieTotali() < 0) {
            throw new LibroException("Impossibile aggiungere un libro con copie negative.");
        }

        int index = listaLibri.indexOf(l);

        if (index == -1) {
            listaLibri.add(l);
        } else {
            Libro libroEsistente = listaLibri.get(index);
            if (!libroEsistente.isAttivo()) {

                libroEsistente.setAttivo(true);
                libroEsistente.setCopieTotali(l.getCopieTotali());
                libroEsistente.setCopieDisponibili(l.getCopieDisponibili());
            } else {
                int nuoveCopie = l.getCopieTotali();
                libroEsistente.setCopieTotali(libroEsistente.getCopieTotali() + nuoveCopie);
                libroEsistente.setCopieDisponibili(libroEsistente.getCopieDisponibili() + nuoveCopie);
            }

            listaLibri.set(index, libroEsistente);
        }
    }


    /**
     * @param l il Libro da rimuovere
     * @brief Rimuove logicamente un libro (lo marca come non attivo).
     *
     * @pre l != null
     * @pre l.isAttivo() == true
     * @pre l.getCopieDisponibili() == l.getCopieTotali() (Nessuna copia in prestito)
     *
     * @post l.isAttivo() = false
     * @post listaLibri.size() invariata
     *
     * @throws LibroException
     */
    @Override
    public void remove(Libro l) throws LibroException {
        if(l == null) return;

        if(!l.isAttivo())
            throw new LibroException("Il libro risulta non attivo");

        if(l.getCopieDisponibili()!=l.getCopieTotali())
            throw new LibroException("Il libro ha copie ancora in prestito");

       l.setAttivo(false);
    }

    @Override
    public ObservableList<Libro> getLista() {
        return listaLibri;
    }


    /**
     * @param vecchio il libro da modificare
     * @param nuovo   il libro con i nuovi dati
     * @brief Permette di modificare i dati di un libro esistente.
     *
     * @pre listaLibri.contains(vecchio) == true
     * @pre vecchio.isAttivo() == true
     * @pre nuovo != null
     * @pre nuovo.getCopieTotali() >= (vecchio.getCopieTotali() - vecchio.getCopieDisponibili())
     *
     * @post vecchio aggiornato con i valori di nuovo (Titolo, Autore, Anno, ISBN, Copie)
     * @post listaLibri.size() invariata
     *
     * @throws LibroException
     */
    @Override
    public void modifica(Libro vecchio, Libro nuovo) throws IllegalArgumentException,LibroException {
        int index = listaLibri.indexOf(vecchio);
        if(!vecchio.isAttivo()){
            throw new LibroException("Il libro non è attivo");
        }
        if (index != -1) {
            int copieNonDisponibili = vecchio.getCopieTotali() - vecchio.getCopieDisponibili();

            if (copieNonDisponibili > nuovo.getCopieTotali()) {
                throw new IllegalArgumentException("Impossibile ridurre le copie totali a " + nuovo.getCopieTotali() +
                        " perché ci sono " + copieNonDisponibili + " copie ancora in prestito.");
            }

            int nuoveDisponibili = nuovo.getCopieTotali() - copieNonDisponibili;

            vecchio.setTitolo(nuovo.getTitolo());
            vecchio.setAutore(nuovo.getAutore());
            vecchio.setAnno(nuovo.getAnno());
            vecchio.setIsbn(nuovo.getIsbn());
            vecchio.setCopieTotali(nuovo.getCopieTotali());


            vecchio.setCopieDisponibili(nuoveDisponibili);

            listaLibri.set(index, vecchio);
        }
    }


    /** @brief Ottiene il Predicate corrispondente per la ricerca filtrata di un libro
     *
     * @param str La stringa da filtrare nella tabella per la ricerca
     *
     * @return Predicate associato alla ricerca attuale
     *
     * @see Gestore#getPredicato(String)
     */
    @Override
    public Predicate<Libro> getPredicato(String str) {
        return libro -> {
            if (str == null || str.isEmpty()) return true; // Se vuoto mostra tutto
            String filtro = str.toLowerCase();

            // Cerca in Titolo, Autore o ISBN
            return libro.getTitolo().toLowerCase().contains(filtro) ||
                    libro.getAutore().toLowerCase().contains(filtro) ||
                    libro.getIsbn().toLowerCase().contains(filtro);
        };
    }

}
