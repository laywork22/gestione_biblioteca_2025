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
    }

    @Override
    public void remove(Prestito l) {

    }

    @Override
    public ObservableList<Prestito> getLista(){
        return null;
    }

    @Override
    public void modifica(Prestito vecchio, Prestito nuovo) {

    }

    @Override
    public void ordinaLista(Comparator<Prestito> comparatore) {

    }

    public Predicate<Prestito> getPredicatoPrestito(String str) {
        return r -> true;
    }

    @Override
    public void salvaLista(String nomeFile) {

    }

    public static GestorePrestito caricaListaPrestiti(String nomeFile) {
        return null;
    }

}
