package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.exceptions.LibroException;
import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.exceptions.UtenteException;
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
import javafx.scene.control.*;
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
    private TextField barraRicerca;
    @FXML
    public void initialize() {
        //inizializzo i gestori UNA sola volta
        gestoreLibro = new GestoreLibro();
        gestorePrestito = new GestorePrestito();
        gestoreUtente = new GestoreUtente();
        barraRicerca.textProperty().addListener((observable, oldValue, newValue) -> {
            if (areaCorrente != null) {
                areaCorrente.filtraTabella(newValue);
            }
        });


        //inserimento libri
        Libro l1 = new Libro("I Promessi Sposi", "Alessandro Manzoni", 1827, "978-8804668283", 10);
        Libro l2 = new Libro("Harry Potter e la Pietra Filosofale", "J.K. Rowling", 1997, "978-8869183157", 5);
        Libro l3 = new Libro("Clean Code", "Robert C. Martin",2008, "978-0132350884", 5);
        Libro l4 = new Libro("Dune", "Frank Herbert", 1965, "978-0441172719", 2); // Solo 2 copie
        Libro l5 = new Libro("1984", "George Orwell", 1949, "978-8804668238", 15);
        Libro l6 = new Libro("Il barone rampante", "Italo Calvino", 1957, "978-8804370858", 3);

        try {
            gestoreLibro.add(l1);
            gestoreLibro.add(l2);
            gestoreLibro.add(l3);
            gestoreLibro.add(l4);
            gestoreLibro.add(l5);
            gestoreLibro.add(l6);
        } catch (LibroException e) {
            System.err.println("Errore inserimento libri test: " + e.getMessage());
        }


        //inseirmento utenti
        Utente u1 = new Utente("Mario", "Rossi", "RSSMRA80A01H501U", "mario.rossi@email.it");
        Utente u2 = new Utente("Luigi", "Verdi", "VRDLGU90B02F205Z", "luigi.verdi@test.com");
        Utente u3 = new Utente("Giulia", "Bianchi", "BNCGLI95C45H501A", "giulia.b@email.it");
        Utente u4 = new Utente("Anna", "Neri", "NRAMNA88D55F205K", "anna.neri@post.it");

        try {
            gestoreUtente.add(u1);
            gestoreUtente.add(u2);
            gestoreUtente.add(u3);
            gestoreUtente.add(u4);
        } catch (UtenteException e) {
            return;
        }


        //prestiti
        try {
            gestorePrestito.add(new Prestito(u1, l1, LocalDate.now().minusDays(5), LocalDate.now().plusDays(25)));
            gestorePrestito.add(new Prestito(u2, l2, LocalDate.now().minusDays(2), LocalDate.now().plusDays(28)));
            gestorePrestito.add(new Prestito(u3, l4, LocalDate.now().minusDays(40), LocalDate.now().minusDays(10)));
            gestorePrestito.add(new Prestito(u4, l4, LocalDate.now().minusDays(1), LocalDate.now().plusDays(29)));
        } catch (PrestitoException ignored) {

        }



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

            Object item = tabella.getSelectionModel().getSelectedItem();

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