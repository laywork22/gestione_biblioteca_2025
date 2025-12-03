package it.unisa.diem.softeng.librarymanager;

import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestoreUtente {
    private final ObservableList<Utente> utentiList;

    public GestoreUtente() {
        utentiList = FXCollections.observableArrayList();
    }

    public void add(Utente l) {
        utentiList.add(l);
    }

    public void remove(Utente l) {

    }

    public void salvaListaUtenti(String nomeFile) {

    }

    public static GestoreLibro caricaListaUtenti(String nomeFile) {
        return null;
    }

}
