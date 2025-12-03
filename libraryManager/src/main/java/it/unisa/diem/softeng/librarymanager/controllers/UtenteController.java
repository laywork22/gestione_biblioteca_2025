package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class UtenteController implements AreaController{
    private final GestoreLibro gestore = new GestoreLibro();
    private FilteredList<Utente> listaFiltrata;
    private SortedList<Utente> listaOrdinata;

    @javafx.fxml.FXML
    private TextField nomeFld;
    @javafx.fxml.FXML
    private TextField cognomeFld;
    @javafx.fxml.FXML
    private Button salvaBtn;
    @javafx.fxml.FXML
    private Button annullaBtn;
    @javafx.fxml.FXML
    private TextField matricolaFld;
    @javafx.fxml.FXML
    private TextField emailFld;

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/libraryManager/UtenteView.fxml"));

            Parent root = fxmlLoader.load();

            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Nuovo Utente");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);

            stage.show();

        } catch (IOException e) {
            System.out.println("Errore nel caricamento di UtenteView.fxml");
        }
    }

    @Override
    public void onEdit(TableView<?> tabella) {

    }

    @Override
    public void setTableView(TableView<?> table) {

    }

    @Override
    public void filtraTabella(TableView<?> tabella) {

    }

}
