package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.function.Predicate;

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
