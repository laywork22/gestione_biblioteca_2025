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


    /** @brief Ottiene il Predicate corrispondente per la ricerca filtrata di un Utente
     *
     * @param filtro La stringa da filtrare nella tabella per la ricerca
     *
     * @return Predicate associato alla ricerca attuale
     */
    @Override
    public Predicate<Utente> getPredicato(String filtro) {
        return r -> true;
    }

    /** @brief Inizializza il gestore con la lista di osservabili degli utenti caricata dal file
     *
     * @pre Il file deve esistere
     *
     * @post La lista è caricata in memoria nel GestoreUtente
     *
     * @return GestoreUtente con listaUtente non vuota
     *
     * @param nomeFile Il nome del file da cui caricare la lista
     */
    public static GestoreUtente caricaListaUtenti(String nomeFile) {
        return null;
    }

}
