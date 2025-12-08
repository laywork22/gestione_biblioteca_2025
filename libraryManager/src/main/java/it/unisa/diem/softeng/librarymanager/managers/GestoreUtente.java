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
 */
public class GestoreUtente implements Gestore<Utente> {
    private final ObservableList<Utente> utentiList;

    public GestoreUtente() {
        utentiList = FXCollections.observableArrayList();
    }

    /**
     * @brief Aggiunge un utente alla lista.
     * Implementazione specifica per gli utent: controlla se la matricola è già presente nella lista,
     * se esiste, non aggiunge una nuova istanza e non inserisce l'Utente, altrimenti lo aggiunge..
     *
     * @param l il Libro da aggiungere o aggiornare
     *
     * @see Gestore#add(Object)
     */
    @Override
    public void add(Utente l) {
    }

    /**
     * @brief Rimuove un utente dalla lista logicamente: aggiorna il suo stato a "non attivo".
     * L'istanza persiste come richiesto.
     *
     * @param l
     * 
     * @see Gestore#remove(Object) 
     */
    @Override
    public void remove(Utente l) {

    }

    @Override
    public ObservableList<Utente> getLista() {
        return this.utentiList;
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
     *
     * @see Gestore#getPredicato(String)
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
