package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.managers.Gestore;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
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
import java.time.LocalDate;

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

        gestoreLibro.add(new Libro("Evangelion", "Shiro Sagisu", 2012, "983231-12", 10, 10));
        gestoreLibro.add(new Libro("Marc Jerkinson", "Hawk Two A", 2012, "283231-12", 12, 12));
        Libro libro=new Libro("Marc Jerkinson", "Hawk Two A", 2012, "283231-12", 12, 12);
        Utente Fabio=new Utente("fabio","volo","9832193","sasas@gmail.com");
        gestoreUtente.add(Fabio);
        gestorePrestito.add(new Prestito(Fabio,libro, LocalDate.now(),LocalDate.of(2036,12,29)));

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
        removeBtn.setOnAction(e -> areaCorrente.onRemove(tabella.getSelectionModel().getSelectedItem()));
        modifyButton.setOnAction(e -> areaCorrente.onEdit(tabella));

        //qui ci va il riempimento del MenuButton con gli ordinamenti disponibili (MenuItem)

        areaCorrente.setTableView(tabella);
        //areaCorrente.filtraTabella(tabella);
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

    @FXML
    public void ordineTabella(ActionEvent actionEvent) {
    }


}