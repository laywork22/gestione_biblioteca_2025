package it.unisa.diem.softeng.librarymanager.model;

import java.io.Serializable;
import java.util.List;

public class ArchivioDati implements Serializable {
    private List<Prestito> listaPrestiti;
    private List<Utente> listaUtenti;
    private List<Libro> listaLibri;

    public ArchivioDati(List<Prestito> listaPrestiti, List<Utente> listaUtenti, List<Libro> listaLibri) {
        this.listaPrestiti = listaPrestiti;
        this.listaUtenti = listaUtenti;
        this.listaLibri = listaLibri;
    }

    public List<Prestito> getListaPrestiti() {
        return listaPrestiti;
    }

    public List<Libro> getListaLibri() {
        return listaLibri;
    }

    public List<Utente> getListaUtenti() {
        return listaUtenti;
    }
}
