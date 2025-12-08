package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.function.Predicate;
/**
 * @brief Classe specifica per la gestione delle entità Libro.
 *
 *Questa classe implementa i metodi relativi a la logica di formazione
 *della ObservableList di Libro.
 */
public class GestoreLibro implements Gestore<Libro> {
    private final ObservableList<Libro> listaLibri;

    public GestoreLibro() {
        this.listaLibri = FXCollections.observableArrayList();
    }

    /**
     * @brief Aggiunge un libro alla lista.
     * Implementazione specifica per i Libri: controlla se il Libro è già presente
     * (tramite ISBN). Se esiste, non aggiunge una nuova istanza, ma incrementa il contatore delle copie disponibili.
     *
     * @param l il Libro da aggiungere o aggiornare
     * @see Gestore#add(Object)
     */
    @Override
    public void add(Libro l) {
        if (l == null) return;

        int index = listaLibri.indexOf(l);

        if (index != -1) {
            Libro libroEsistente = listaLibri.get(index);

            libroEsistente.incrementaCopie();

            listaLibri.set(index, libroEsistente);

            return;
        }

        listaLibri.add(l);
    }


    /**
     * @brief Rimuove un libro dalla lista logicamente: aggiorna il suo stato a "non attivo".
     * L'istanza persiste come richiesto.
     * 
     * @param l
     * 
     * @see Gestore#remove(Object) 
     */
    @Override
    public void remove(Libro l) {
        if(l == null) return;

        listaLibri.remove(l);
    }

    @Override
    public void salvaLista(String nomeFile) {

    }

    @Override
    public ObservableList<Libro> getLista() {
        return listaLibri;
    }

    @Override
    public void modifica(Libro vecchio, Libro nuovo) {

    }

    @Override
    public void ordinaLista(Comparator<Libro> comparatore) {

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
        return r -> true;
    }


    /** @brief Inizializza il gestore con la lista di osservabili di libri caricata dal file
     *
     * @pre Il file deve esistere
     *
     * @post La lista è caricata in memoria nel GestoreLibro
     *
     * @return GestoreLibro con listaLibri non vuota
     *
     * @param nomeFile Il nome del file da cui caricare la lista
      */
    public static GestoreLibro caricaListaLibri(String nomeFile) {
        return null;
    }

}
