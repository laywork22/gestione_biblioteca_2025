package it.unisa.diem.softeng.librarymanager.managers;

import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;
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
    void add(T elem) throws Exception;

    /**@brief Rimuove un elemento dalla collezione gestita.
     * <p>
     * Implementa la logica di rimozione di un elemento dalla collezione.
     * <p>
     * Le classi che implementano la interfaccia hanno il ruolo di controllare
     * i vari vincoli(es. cancellazione utente con prestiti attivi).
     *
     * @param elem L'elemento da rimuovere
     */
    void remove(T elem) throws Exception;

    /**
     * @brief Imposta la lista del gestore con una passata come parametro incapsulandola in una nuova lista osservabile.
     * Utilizzata per le operazioni di I/O.
     * @param l La nuova lista che sostituirà quella precedente.
     */
    void setLista(List<T> l);


    /**@brief Restituisce la lista osservabile di entita'
     *
     * Restituisce una Lista adatta all'inserimento in una Tableview
     *
     * @return ObservableList di entita'
     */
    ObservableList<T> getLista();

    void modifica(T vecchio, T nuovo) throws Exception;


    Predicate<T> getPredicato(String filtro);


}
