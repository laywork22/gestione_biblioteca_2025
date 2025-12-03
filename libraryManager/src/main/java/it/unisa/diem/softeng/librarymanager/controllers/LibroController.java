package it.unisa.diem.softeng.librarymanager.controllers;

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

public class LibroController implements AreaController {
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

    @FXML
    public void initialize() {
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("it/unisa/diem/softeng/libraryManager/LibroView.fxml"));

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
    public void onEdit() {

    }

    @Override
    public void setTableView(TableView<?> table) {

    }

    @Override
    public void filtraTabella(TableView<?> tabella) {

    }
}

