package it.nocera.laborgata;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class PrimaryController {

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
    public void initialize() {
        // Opzionale: se i CSS sono già caricati nell'FXML, applyCss non serve qui
        // menuButton.applyCss();
        sideMenu.setTranslateX(-200);
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

    // Assicurati che nell'FXML del bottone Aggiungi ci sia: onAction="#addElement"
    @FXML
    public void addElement(ActionEvent actionEvent) {
        try {
            // 1. Creiamo il loader per il secondo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("insertBookView.fxml"));

            // 2. Carichiamo la gerarchia grafica (Parent)
            Parent root = fxmlLoader.load();

            // 3. Creiamo un nuovo Stage (una nuova finestra)
            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Inserisci nuovo libro");
            stage.setScene(new Scene(root));

            // Opzionale: blocca la finestra principale finché questa non viene chiusa
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addBtn.getScene().getWindow());

            stage.show();

        } catch (IOException e) {
            System.out.println("Errore nel caricamento di insertBookView.fxml");
        }
    }
}