package it.unisa.diem.softeng.librarymanager.managers;

import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * @brief Interfaccia generica per la gestione delle entità del sistema.
 *
 * Questa interfaccia definisce le operazioni comuni (CRUD, ordinamento, persistenza)
 * che devono essere implementate dai manager specifici (es. GestoreLibri, GestoreUtenti).
 * Utilizza i Generics per garantire la riusabilità e la tipizzazione forte.
 *
 * @author Gruppo 12
 */
public interface Gestore<T> {
    /**
     * @brief Aggiunge un nuovo elemento alla collezione gestita.
     * <p>
     * Implementa la logica di inserimento. Le classi concrete gestiscono
     * eventuali controlli.
     *
     * @param elem L'elemento da aggiungere.
     */
    void add(T elem);

    /**@brief Rimuove un elemento dalla collezione gestita.
     * <p>
     * Implementa la logica di rimozione di un elemento dalla collezione.
     * <p>
     * Le classi che implementano la interfaccia hanno il ruolo di controllare
     * i vari vincoli(es. cancellazione utente con prestiti attivi).
     *
     * @param elem L'elemento da rimuovere
     */
    void remove(T elem);

    /**@brief Restituisce la lista osservabile di entita'
     *
     * Restituisce una Lista adatta all'inserimento in una Tableview
     *
     * @return ObservableList di entita'
     */
    ObservableList<T> getLista();

    void modifica(T vecchio, T nuovo);

    /**@brief Ordina la lista in funzione del customComparator in ingresso
     *
     * Ordina la lista e rende possibile rispettare i requisiti di visualizzazione in ordine (UI-1.1.2,UI-1.2.2).
     *
     * @param comparatore Il comparatore che descrive la logica di ordinamento
     */
    void ordinaLista(Comparator<T> comparatore);


    Predicate<T> getPredicato(String filtro);

    /**@brief Salva lo stato corrente su un file.
     *
     * Implementa il requisito di salvataggio (DF-1.4)
     *
     * @param nomeFile Il nome del file su cui si salverà lo stato di una determinata area
     */
    void salvaLista(String nomeFile);

}
