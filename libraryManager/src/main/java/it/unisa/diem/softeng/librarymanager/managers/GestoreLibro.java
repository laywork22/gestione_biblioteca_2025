package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.function.Predicate;
/**
 * @brief Classe specifica per la gestione delle entità Libro.
 *
 *Questa classe implementa i metodi relativi a la logica di formazione
 *della ObservableList di Libro.
 * @author Gruppo 12
 */
public class GestoreLibro implements Gestore<Libro> {
    private final ObservableList<Libro> listaLibri;

    public GestoreLibro() {
        this.listaLibri = FXCollections.observableArrayList();
    }

    @Override
    public void add(Libro l) {
        if (l == null) return;

        int index = listaLibri.indexOf(l);

        if (index != -1) {
            Libro libroEsistente = listaLibri.get(index);

            libroEsistente.incrementaCopie();

            listaLibri.set(index, libroEsistente);

            return;
        }

        listaLibri.add(l);
    }

    @Override
    public void remove(Libro l) {
        if(l == null) return;

        listaLibri.remove(l);
    }

    @Override
    public void salvaLista(String nomeFile) {

    }

    @Override
    public ObservableList<Libro> getLista() {
        return listaLibri;
    }

    @Override
    public void modifica(Libro vecchio, Libro nuovo) {
        int index = listaLibri.indexOf(vecchio);

        if (index != -1) {
            listaLibri.set(index, nuovo);
        }
    }

    @Override
    public void ordinaLista(Comparator<Libro> comparatore) {

    }

    public Predicate<Libro> getPredicatoLibro(String str) {
        return r -> true;
    }

    public static GestoreLibro caricaListaLibri(String nomeFile) {
        return null;
    }

}
