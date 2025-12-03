package it.unisa.diem.softeng.librarymanager.managers;

import javafx.collections.ObservableList;

import java.util.Comparator;

public interface Gestore<T> {
    void add(T elem);

    void remove(T elem);

    ObservableList<T> getLista();

    void ordinaLista(Comparator<T> comparatore);

    void salvaLista(String nomeFile);

}
