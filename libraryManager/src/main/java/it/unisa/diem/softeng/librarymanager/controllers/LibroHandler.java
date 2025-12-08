package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.comparators.AutoreLibroComparator;
import it.unisa.diem.softeng.librarymanager.controllers.forms.FormLibroController;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**@brief Gestore della schermata UI Area Libri
 *
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del file del form al
 * gestore del form
 *
 */
public class LibroHandler implements AreaHandler<Libro> {
    private final GestoreLibro gestore;
    private final Map<String, Comparator<Libro>> mappaComparatori;
    private FilteredList<Libro> listaFiltrata;
    private SortedList<Libro> listaOrdinata;

    public LibroHandler(GestoreLibro gestore) {
        this.gestore = gestore;

        mappaComparatori = new HashMap<>();

        mappaComparatori.put("Titolo (A-Z)", new AutoreLibroComparator());
        mappaComparatori.put("Autore (A-Z)", new AutoreLibroComparator());
    }

    @Override
    public void onRemove(Libro l) {
    }

    @Override
    public void onAdd() {

    }

    @Override
    public void onEdit(TableView<Libro> tabella) {

    }


    @Override
    public void setTableView(TableView<Libro> table) {

    }

    @Override
    public void filtraTabella(String filtro) {

    }

    @Override
    public List<String> getCriteriOrdinamento() {
        return List.of();
    }


    @Override
    public void ordina(String criterio) {

    }
}

