package it.unisa.diem.softeng.librarymanager.managers;

import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.function.Predicate;
/**
 * @brief Classe specifica per la gestione delle entità Utente.
 *
 *Questa classe implementa i metodi relativi a la logica
 *della ObservableList di Utente.
 * @author Gruppo 12
 */
public class GestoreUtente implements Gestore<Utente> {
    private final ObservableList<Utente> utentiList;

    public GestoreUtente() {
        utentiList = FXCollections.observableArrayList();
    }

    @Override
    public void add(Utente l) {}

    @Override
    public void remove(Utente l) {

    }

    @Override
    public ObservableList<Utente> getLista() {
        return null;
    }

    @Override
    public void modifica(Utente vecchio, Utente nuovo) {

    }

    @Override
    public void ordinaLista(Comparator<Utente> comparatore) {

    }

    @Override
    public void salvaLista(String nomeFile) {

    }

    public Predicate<Utente> getPredicatoUtente(String str) {
        return r -> true;
    }

    public static GestoreLibro caricaListaUtenti(String nomeFile) {
        return null;
    }

}
