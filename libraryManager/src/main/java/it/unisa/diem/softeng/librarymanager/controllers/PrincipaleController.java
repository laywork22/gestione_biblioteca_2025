package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.managers.Gestore;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

/**@brief Gestore principale di schermata
 *
 * Implementa la logica di modifica dell'area a
 * seguito della pressione dei pulsanti a schermo
 * e del cambiamento di ordinamento della tabella.
 *
 * E' la classe che contiene il log dei gestori delle
 * schermate e implementa il processo di salvataggio
 * e caricamento su e da file
 *
 *
 */
public class PrincipaleController {
    private GestorePrestito gestorePrestito;
    private GestoreUtente gestoreUtente;
    private GestoreLibro gestoreLibro;

    private Gestore<?> gestoreCorrente;

    private AreaHandler areaCorrente;
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
        //inizializzo i gestori UNA sola volta
        gestoreLibro = new GestoreLibro();
        gestorePrestito = new GestorePrestito();
        gestoreUtente = new GestoreUtente();
        gestorePrestito.aggiornaStati();

        sideMenu.setTranslateX(-200);

        setArea(new PrestitoHandler(gestorePrestito, gestoreLibro, gestoreUtente));
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

    private void setArea(AreaHandler area) {
        areaCorrente = area;

        addBtn.setOnAction(e -> areaCorrente.onAdd());
        removeBtn.setOnAction(e -> {

            var item = tabella.getSelectionModel().getSelectedItem();

            if (item != null) {
                areaCorrente.onRemove(item);
                tabella.refresh();

            }

        });
        modifyButton.setOnAction(e -> areaCorrente.onEdit(tabella));
        tabella.refresh();
        //qui ci va il riempimento del MenuButton con gli ordinamenti disponibili (MenuItem)

        areaCorrente.setTableView(tabella);
        ordineFiltro.getItems().clear();
        ordineFiltro.setText("Ordina per...");

        if (areaCorrente.getCriteriOrdinamento() != null) {

            for (String criterio : (java.util.List<String>) areaCorrente.getCriteriOrdinamento()) {
                javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem(criterio);
                item.setOnAction(event -> {

                    areaCorrente.ordina(criterio);
                    ordineFiltro.setText("Ordina: " + criterio);
                    tabella.refresh();
                });

                ordineFiltro.getItems().add(item);
            }

        }

    }

    @FXML
    public void setAreaPrestiti(ActionEvent actionEvent) {
        gestoreCorrente = gestorePrestito;

        setArea(new PrestitoHandler(gestorePrestito, gestoreLibro, gestoreUtente));

        areaLbl.setText("Area Prestiti");
        toggleMenu();
    }

    @FXML
    public void setAreaLibri(ActionEvent actionEvent) {
        gestoreCorrente = gestoreLibro;

        setArea(new LibroHandler((GestoreLibro) gestoreCorrente));

        areaLbl.setText("Area Libri");
        toggleMenu();
    }

    @FXML
    public void setAreaUtenti(ActionEvent actionEvent) {
        gestoreCorrente = gestoreUtente;

        setArea(new UtenteHandler((GestoreUtente) gestoreCorrente));

        areaLbl.setText("Area Utenti");
        toggleMenu();
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



}