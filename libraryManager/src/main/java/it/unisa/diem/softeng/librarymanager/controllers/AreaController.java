package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.scene.control.TableView;

import java.util.List;

public interface AreaController {
    void onRemove();

    /**
    * Gestisce la logica di aggiunta di uno dei tre mnodelli
    **/
    void onAdd();

    /**
    * Apre la view di inserimento con i campi riempiti dalla riga scelta
    * */
    void onEdit(TableView<?> tabella);

    /**
    * Imposta la tabella con la lista di osservabili corrispondente
    **/
    void setTableView(TableView<?> tabella);

    /**
    * Incapsulo unaa lista di osservabili in un una FilteredList chr segue i criteri imposti nel Predicate associato un
    * gestore specifico. Utilizzato per filtrare gli elementi della tabella durante la ricerca.
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
