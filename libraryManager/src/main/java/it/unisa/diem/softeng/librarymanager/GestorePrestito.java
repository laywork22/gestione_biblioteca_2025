package it.unisa.diem.softeng.librarymanager;

import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestorePrestito {
    private final ObservableList<Prestito> prestitoList;

    public GestorePrestito() {
        this.prestitoList = FXCollections.observableArrayList();
    }

    public void add(Prestito l) {
        prestitoList.add(l);
    }

    public void remove(Prestito l) {

    }

    public void salvaListaPrestiti(String nomeFile) {

    }

    public static GestorePrestito caricaListaPrestiti(String nomeFile) {
        return null;
    }

}
