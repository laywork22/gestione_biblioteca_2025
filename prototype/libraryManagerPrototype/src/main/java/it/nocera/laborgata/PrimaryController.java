package it.nocera.laborgata;


import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PrimaryController {

    @FXML
    private VBox sideMenu;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private Button menuButton;

    private boolean menuVisible = false;

    @FXML
    public void initialize() {

        menuButton.applyCss();
        sideMenu.setTranslateX(-200); // Menu nascosto all'avvio
    }

    @FXML
    private void toggleMenu() {

        TranslateTransition slideMenu =
                new TranslateTransition(Duration.millis(300), sideMenu);
        TranslateTransition slideContent =
                new TranslateTransition(Duration.millis(300), mainContent);

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
}