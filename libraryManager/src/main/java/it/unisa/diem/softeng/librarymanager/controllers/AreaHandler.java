package it.unisa.diem.softeng.librarymanager.controllers;

import javafx.scene.control.TableView;

import java.util.List;
/**
 * @brief Interfaccia implementata dai gestori delle aree.
 *
 * Questa classe definisce il modello dati per un controller di un area,
 * includendo i metodi logici di operazioni per il cambio del
 * gestore e i metodi per la visualizzazione delle tabelle
 *
 *
 *
 * @author Gruppo 12
 * @version 1.0
 */
public interface AreaHandler {
    void onRemove();

    /**@brief Gestisce la logica di aggiunta di uno dei tre form.
    *
     * Consente il caricamento delle scene FXML ai rispettivi controllori dei form
     *
    **/
    void onAdd();

    /**@brief Apre la view di inserimento con i campi riempiti dalla riga scelta
    *
     * @param tabella la tabella in fase di modifica
    * */
    void onEdit(TableView<?> tabella);

    /**
    * Imposta la tabella con la lista di osservabili corrispondente
    **/
    void setTableView(TableView<?> tabella);

    /**@brief Algoritmo di scrittura della tabella in funzione del criterio di ricerca inserito
    *
     * Incapsulo unaa lista di osservabili in un una FilteredList chr segue i criteri imposti nel Predicate associato un
    * gestore specifico. Utilizzato per filtrare gli elementi della tabella durante la ricerca.
     * @param filtro il testo del campo di ricerca inserito
    **/
    void filtraTabella(String filtro);

    /**
     * Ottengo una stringa di criteri da inserire nel ComboBox
     **/
    List<String> getCriteriOrdinamento();

    /**
     *
     * Incapsula la FilteredList ottenuta da filtraTabella in una SortedList.
     * @param testo Rappresenta il criterio di ordinamento che deve essere implementato nella tabella
     **/
    void ordina(String testo);
}
