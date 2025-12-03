package it.unisa.diem.softeng.librarymanager.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class PrincipaleController {
    private AreaController areaCorrente;
    private boolean menuVisible = false;

    @FXML
    private AnchorPane mainContent;
    @FXML
    private Button menuButton;
    @FXML
    private VBox sideMenu;
    @FXML
    private Button addBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button modifyButton;
    @FXML
    private TableView<?> tabella;
    @FXML
    private Button areaLibriBtn;
    @FXML
    private Button areaPrestitiBtn;
    @FXML
    private Button areaUtentiBtn;
    @FXML
    private Label areaLbl;
    @FXML
    private Button apriFileBtn;
    @FXML
    private Button salvaConNomeBtn;
    @FXML
    private Button salvaFileBtn;
    @FXML
    private MenuButton ordineFiltro;

    @FXML
    public void initialize() {
        sideMenu.setTranslateX(-200);

        setArea(new PrestitoController());
        areaLbl.setText("Area Prestiti");
    }

    @FXML
    private void toggleMenu() {
        TranslateTransition slideMenu = new TranslateTransition(Duration.millis(300), sideMenu);
        TranslateTransition slideContent = new TranslateTransition(Duration.millis(300), mainContent);

        if (!menuVisible) {
            slideMenu.setToX(0);
            slideContent.setToX(200);
            menuVisible = true;
        } else {
            slideMenu.setToX(-200);
            slideContent.setToX(0);
            menuVisible = false;
        }
        slideMenu.play();
        slideContent.play();
    }


    public void setArea(AreaController area) {
        areaCorrente = area;

        addBtn.setOnAction(e -> areaCorrente.onAdd());
        removeBtn.setOnAction(e -> areaCorrente.onRemove());
        modifyButton.setOnAction(e -> areaCorrente.onEdit(tabella));

        areaCorrente.setTableView(tabella);
    }

    @FXML
    public void setAreaPrestiti(ActionEvent actionEvent) {
        setArea(new PrestitoController());
        areaLbl.setText("Area Prestiti");
    }

    @FXML
    public void setAreaLibri(ActionEvent actionEvent) {
        setArea(new LibroController());
        areaLbl.setText("Area Libri");
    }

    @FXML
    public void setAreaUtenti(ActionEvent actionEvent) {
        setArea(new UtenteController());
        areaLbl.setText("Area Utenti");
    }

    @FXML
    public void salvaFile(ActionEvent actionEvent) {

    }

    @FXML
    public void salvaConNome(ActionEvent actionEvent) {

    }

    @FXML
    public void apriFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();

        Node source = (Node) actionEvent.getSource();

        Stage stage = (Stage) source.getScene().getWindow();

        File file = fc.showOpenDialog(stage);
    }

    @FXML
    public void filtraTabella(ActionEvent actionEvent) {
    }
}