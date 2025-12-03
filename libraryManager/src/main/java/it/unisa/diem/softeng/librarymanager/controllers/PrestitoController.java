package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PrestitoController implements AreaController {
    @javafx.fxml.FXML
    private TextField statoFld;
    @javafx.fxml.FXML
    private ComboBox<String> utentiCb;
    @javafx.fxml.FXML
    private TextField dataInizioFld;
    @javafx.fxml.FXML
    private Button salvaBtn;
    @javafx.fxml.FXML
    private ComboBox<String> libroCb;
    @javafx.fxml.FXML
    private TextField dataScadenzaFld;
    @javafx.fxml.FXML
    private Button annullaBtn;

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/libraryManager/PrestitoView.fxml"));

            Parent root = fxmlLoader.load();

            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Nuovo Prestito");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);

            stage.show();

        } catch (IOException e) {
            System.out.println("Errore nel caricamento di PrestitoView.fxml");
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

    @javafx.fxml.FXML
    public void annullaNuovoPrestito(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();

        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
    }

    @javafx.fxml.FXML
    public void salvaNuovoPrestito(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void viewPrestitiLista(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void viewListaPrestiti(ActionEvent actionEvent) {
    }
}
