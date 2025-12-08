package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.comparators.CognomeUtenteComparator;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;

import java.util.*;
/**@brief Gestore della schermata UI Area utenti
 *
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del file del form al
 * gestore del form
 *
 */
public class UtenteHandler implements AreaHandler<Utente> {
    private GestoreUtente gestore;

    /**
     * @brief Mappa chiave-valore che associa ad una stringa un particolare Comparator.
     * Semplifica la ricerca tramite stringa del criterio scelto.
     */
    private final Map<String, Comparator<Utente>> mappaComparatori;

    /**
     * @brief Lista osservabile filtrata secondo un determinato criterio.
     */
    private FilteredList<Utente> listaFiltrata;

    /**
     * @brief Lista osservabile ordinata secondo una relazione d'ordine imposta da un
     * Comparator.
     */
    private SortedList<Utente> listaOrdinata;

    /**
     * @brief Costruttore della classe UtenteHandler.
     *
     * @param gestore Il gestore dell'area utenti.
     */
    public UtenteHandler(GestoreUtente gestore) {
        this.gestore = gestore;

        mappaComparatori = new HashMap<>();
        mappaComparatori.put("Cognome (A-Z)", new CognomeUtenteComparator());
    }

    @Override
    public void onAdd() {

    }

    @Override
    public void onRemove(Utente u) {
    }

    @Override
    public void onEdit(TableView<Utente> tabella) {
    }

    @Override
    public void setTableView(TableView<Utente> table) {

    }

    @Override
    public void filtraTabella(String filtro) {
    }

    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaComparatori.keySet());
    }

    @Override
    public void ordina(String criterio) {
    }
}