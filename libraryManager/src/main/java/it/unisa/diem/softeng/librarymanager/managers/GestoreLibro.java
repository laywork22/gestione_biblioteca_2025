package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class GestoreLibro implements Gestore<Libro> {
    private final ObservableList<Libro> listaLibri;

    public GestoreLibro() {
        this.listaLibri = FXCollections.observableArrayList();
    }

    @Override
    public void add(Libro l) {

    }

    @Override
    public void remove(Libro l) {

    }

    @Override
    public void salvaLista(String nomeFile) {

    }

    @Override
    public ObservableList<Libro> getLista() {
        return listaLibri;
    }

    @Override
    public void ordinaLista(Comparator<Libro> comparatore) {
        
    }


    public static GestoreLibro caricaListaLibri(String nomeFile) {
        return null;
    }

}
