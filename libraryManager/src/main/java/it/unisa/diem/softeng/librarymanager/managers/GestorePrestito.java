package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.function.Predicate;
/**
 * @brief Classe specifica per la gestione delle entità Prestito.
 *
 *Questa classe implementa i metodi relativi a la logica di formazione
 *della ObservableList di Prestito.
 * @author Gruppo 12
 */
public class GestorePrestito implements Gestore<Prestito>{
    private final ObservableList<Prestito> prestitoList;

    public GestorePrestito() {
        this.prestitoList = FXCollections.observableArrayList();
    }

    @Override
    public void add(Prestito l) {
        if(l==null){
            return;
        }

        prestitoList.add(l);
    }

    @Override
    public void remove(Prestito l) {
        if(l==null){return;}

        prestitoList.remove(l);
    }

    @Override
    public ObservableList<Prestito> getLista(){
        return this.prestitoList;
    }

    @Override
    public void modifica(Prestito vecchio, Prestito nuovo) {

    }

    @Override
    public void ordinaLista(Comparator<Prestito> comparatore) {

    }
    /** @brief Ottiene il Predicate corrispondente per la ricerca filtrata di un Prestito
     *
     * @param str La stringa da filtrare nella tabella per la ricerca
     *
     * @return Predicate associato alla ricerca attuale
     */
    @Override
    public Predicate<Prestito> getPredicato(String str) {
        return r -> true;
    }

    @Override
    public void salvaLista(String nomeFile) {

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
        return null;
    }

}
