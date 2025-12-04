package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.comparators.AutoreLibroComparator;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibroController implements AreaController {
    private final GestoreLibro gestore;
    private final Map<String, Comparator<Libro>> mappaComparatori;
    private FilteredList<Utente> listaFiltrata;
    private SortedList<Utente> listaOrdinata;


    @FXML
    private AnchorPane insertBookContent;
    @FXML
    private TextField annoFld;
    @FXML
    private TextField titoloFld;
    @FXML
    private TextField autoreFld;
    @FXML
    private Button salvaLibroBtn;
    @FXML
    private TextField copieDisponibiliFld;
    @FXML
    private TextField isbnFld;
    @FXML
    private TextField copieTotaliFld;
    @FXML
    private Button annullaBtn;

    public LibroController(GestoreLibro gestore) {
        this.gestore = gestore;

        mappaComparatori = new HashMap<>();

        mappaComparatori.put("Titolo (A-Z)", new AutoreLibroComparator());
        mappaComparatori.put("Autore (A-Z)", new AutoreLibroComparator());
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/libraryManager/LibroView.fxml"));
            fxmlLoader.setControllerFactory(param -> {

                if (param == LibroController.class) {
                    return this;
                }

                try {
                    return param.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            Parent root = fxmlLoader.load();

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
    public void onEdit(TableView<?> tabella) {

    }


    @Override
    public void setTableView(TableView<?> table) {

    }

    @Override
    public void filtraTabella(String filtro) {

    }

    @Override
    public List<String> getCriteriOrdinamento() {
        return List.of();
    }

    @Override
    public void ordina(String testo) {

    }
}

