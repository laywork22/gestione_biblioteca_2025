package it.unisa.diem.softeng.librarymanager.controllers;

import javafx.scene.control.TableView;
import java.util.List;

/**
 * @brief Interfaccia implementata dai gestori delle aree.
 *
 * Questa classe definisce il modello dati per un controller di un'area,
 * includendo i metodi logici di operazioni per il cambio del
 * gestore e i metodi per la visualizzazione delle tabelle.
 *
 * @author Gruppo 12
 * @version 1.0
 */
public interface AreaHandler<E> {

    /**
     * @brief Gestisce la rimozione dell'elemento selezionato dalla tabella.
     *
     * @param elem L' elemento selezionato da rimuovere.
     */
    void onRemove(E elem);

    /**
     * @brief Gestisce la logica di apertura del form di aggiunta.
     *
     * Consente il caricamento delle scene FXML e l'inizializzazione dei
     * rispettivi controller per i form di inserimento.
     *
     * @throws
     */
    void onAdd();

    /**
     * @brief Apre la view di modifica con i campi pre-compilati.
     *
     * Recupera l'elemento selezionato dalla tabella e popola il form
     * per consentirne la modifica.
     *
     * @pre La tabella deve avere un elemento selezionato (non null).
     *
     * @param tabella La tabella contenente l'elemento da modificare.
     */
    void onEdit(TableView<E> tabella);

    /**
     * @brief Imposta la tabella con la lista di elementi dell'area corrispondente.
     *
     * Collega la ObservableList del gestore alla TableView per visualizzare i dati.
     *
     * @post La tabella è popolata con gli elementi aggiornati.
     *
     * @param tabella La tabella da inizializzare.
     */
    void setTableView(TableView<E> tabella);

    /**
     * @brief Filtra il contenuto della tabella in base alla stringa di ricerca.
     *
     * Incapsula la lista di osservabili in una FilteredList che applica il Predicate
     * definito nel gestore specifico.
     *
     * @param filtro La stringa inserita nel campo di ricerca.
     */
    void filtraTabella(String filtro);

    /**
     * @brief Restituisce la lista dei criteri di ordinamento disponibili.
     *
     * Utilizzato per popolare il ComboBox di scelta ordinamento.
     *
     * @return Una lista di stringhe rappresentanti i nomi dei criteri di ordinamento.
     */
    List<String> getCriteriOrdinamento();

    /**
     * @brief Ordina la tabella secondo il criterio specificato.
     *
     * Incapsula la FilteredList corrente in una SortedList applicando il comparatore
     * associato al criterio scelto.
     *
     * @param criterio Il nome del criterio di ordinamento da applicare.
     */
    void ordina(String criterio);
}