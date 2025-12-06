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
    public void onRemove(TableView<Libro> tabella) {
        Libro l = tabella.getSelectionModel().getSelectedItem();
        if (l != null) gestore.remove(l);

        tabella.refresh();
    }

    @Override
    public void onAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/librarymanager/LibroView.fxml"));

            Parent root = fxmlLoader.load();

            FormLibroController fu = fxmlLoader.getController();

            if(fu != null) {
                fu.init(gestore);
            }

            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Nuovo Libro");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);

            stage.show();

        } catch (IOException e) {
            System.out.println("Errore nel caricamento di LibroView.fxml");
        }
    }

    @Override
    public void onEdit(TableView<Libro> tabella) {
        //prendi il libro dalla tabella
        Libro l = tabella.getSelectionModel().getSelectedItem();

        if (l == null) return;

        //usa il controller per settare il form con setFormOnEdit
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/librarymanager/LibroView.fxml"));

            Parent root = fxmlLoader.load();

            FormLibroController fu = fxmlLoader.getController();

            if(fu != null) {
                fu.init(gestore);
            } else return;

            fu.setFormOnEdit(l);

            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Nuovo Libro");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("Errore nel caricamento di LibroView.fxml");
        }
        //apri il form con i campi riempiti

        tabella.refresh();
    }


    @Override
    public void setTableView(TableView<Libro> table) {
        TableView<Libro> t = table;

        t.getColumns().clear();

        TableColumn<Libro, String> autoreClm = new TableColumn<>("Autore");
        TableColumn<Libro, String> titoloClm = new TableColumn<>("Titolo");
        TableColumn<Libro, String> isbnClm = new TableColumn<>("ISBN");
        TableColumn<Libro, Integer> annoClm = new TableColumn<>("Anno");
        TableColumn<Libro, Integer> copieDisponibiliClm = new TableColumn<>("Copie Disponibili");
        TableColumn<Libro, Integer> copieTotaliClm = new TableColumn<>("Copie Totali");


        titoloClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getTitolo()));
        autoreClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAutore()));
        isbnClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getIsbn()));
        isbnClm.setSortable(false);
        annoClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getAnno()));
        copieTotaliClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getCopieTotali()));
        copieTotaliClm.setSortable(false);
        copieDisponibiliClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getCopieDisponibili()));
        copieDisponibiliClm.setSortable(false);

        t.getColumns().addAll(titoloClm, autoreClm, annoClm, isbnClm, copieDisponibiliClm, copieTotaliClm);

        t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        t.setItems(gestore.getLista());

        t.refresh();

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

