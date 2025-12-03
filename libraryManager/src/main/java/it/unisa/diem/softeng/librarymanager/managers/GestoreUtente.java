package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class GestoreUtente implements Gestore<Utente> {
    private final ObservableList<Utente> utentiList;

    public GestoreUtente() {
        utentiList = FXCollections.observableArrayList();
    }

    @Override
    public void add(Utente l) {
        utentiList.add(l);
    }

    @Override
    public void remove(Utente l) {

    }

    @Override
    public ObservableList<Utente> getLista() {
        return null;
    }

    @Override
    public void ordinaLista(Comparator<Utente> comparatore) {

    }

    @Override
    public void salvaLista(String nomeFile) {

    }

    public static GestoreLibro caricaListaUtenti(String nomeFile) {
        return null;
    }

}
