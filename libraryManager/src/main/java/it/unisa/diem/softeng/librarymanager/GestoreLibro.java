package it.unisa.diem.softeng.librarymanager;

import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestoreLibro {
    private final ObservableList<Libro> listaLibri;

    public GestoreLibro() {
        this.listaLibri = FXCollections.observableArrayList();
    }

    public void add(Libro l) {

    }

    public void remove(Libro l) {

    }

    public void salvaListaLibri(String nomeFile) {

    }

    public static GestoreLibro caricaListaLibri(String nomeFile) {
        return null;
    }


}
