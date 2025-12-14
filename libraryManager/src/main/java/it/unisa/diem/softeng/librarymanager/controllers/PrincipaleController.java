package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.exceptions.LibroException;
import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.exceptions.UtenteException;
import it.unisa.diem.softeng.librarymanager.handlers.AreaHandler;
import it.unisa.diem.softeng.librarymanager.handlers.LibroHandler;
import it.unisa.diem.softeng.librarymanager.handlers.PrestitoHandler;
import it.unisa.diem.softeng.librarymanager.handlers.UtenteHandler;
import it.unisa.diem.softeng.librarymanager.managers.*;
import it.unisa.diem.softeng.librarymanager.model.ArchivioDati;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private GestoreArchivio  gestoreArchivio;


    private File fileCorrente;

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
    private MenuButton ordineFiltro;
    @FXML
    private TextField barraRicerca;
    @FXML
    private Button salvaBtn;


    @FXML
    public void initialize() {     
        gestoreLibro = new GestoreLibro();
        gestorePrestito = new GestorePrestito();
        gestoreUtente = new GestoreUtente();

        gestoreArchivio = new GestoreArchivio();

        gestoreCorrente = gestorePrestito;

        sideMenu.setTranslateX(-200);


        gestorePrestito.aggiornaStati();
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
        System.out.println("Click Salva File...");
        if (fileCorrente != null) {
            //aAbbiamo già un file, sovrascriviamo
            try {
                List<Libro> libri = new ArrayList<>(gestoreLibro.getLista());
                List<Utente> utenti = new ArrayList<>(gestoreUtente.getLista());
                List<Prestito> prestiti = new ArrayList<>(gestorePrestito.getLista());

                ArchivioDati archivio = new ArchivioDati(prestiti, utenti, libri);

                gestoreArchivio.salvaArchivio(archivio, fileCorrente);

                mostraAlert(Alert.AlertType.INFORMATION, "Salvato in " + fileCorrente.getName());
            } catch (IOException e) {
                mostraAlert(Alert.AlertType.ERROR, "Errore");
            }
        } else {
            salvaFileConNome(actionEvent);
        }
    }


    @FXML
    public void salvaFileConNome(ActionEvent actionEvent) {
        System.out.println("Premuto salva con nome...");

        Window window = mainContent.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva Archivio Biblioteca");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Biblioteca (*.bib)", "*.bib"));
        fileChooser.setInitialFileName("biblioteca.bib");

        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            try {
                List<Libro> libri = new ArrayList<>(gestoreLibro.getLista());
                List<Utente> utenti = new ArrayList<>(gestoreUtente.getLista());
                List<Prestito> prestiti = new ArrayList<>(gestorePrestito.getLista());

                ArchivioDati archivio = new ArchivioDati(prestiti, utenti, libri);

                gestoreArchivio.salvaArchivio(archivio, file);

                fileCorrente = file;
                mostraAlert(Alert.AlertType.INFORMATION, "Salvataggio completato: " + "Dati salvati in " + file.getName());

            } catch (IOException e) {
                mostraAlert(Alert.AlertType.ERROR, "Errore I/O: " + e.getMessage());
            }
        }

    }

    @FXML
    public void apriFile(ActionEvent event) {
        javafx.stage.Window window = mainContent.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Apri Archivio Biblioteca");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Biblioteca (*.bib)", "*.bib"));

        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            try {
                ArchivioDati archivio = gestoreArchivio.caricaArchivio(file);

                gestoreLibro.setLista(archivio.getListaLibri());
                gestoreUtente.setLista(archivio.getListaUtenti());
                gestorePrestito.setLista(archivio.getListaPrestiti());

                fileCorrente = file;

                aggiornaVistaCorrente();

                mostraAlert(Alert.AlertType.INFORMATION, "Caricamento completato: Archivio caricato correttamente.");

            } catch (Exception e) {
                e.printStackTrace();
                mostraAlert(Alert.AlertType.ERROR, "Errore Caricamento: File non valido o corrotto.");
            }
        }

        tabella.refresh();
    }

    //metodo helper per rinfrescare la tabella visualizzata
    private void aggiornaVistaCorrente() {
        gestorePrestito.aggiornaStati();

        if (areaCorrente instanceof PrestitoHandler) {
            setArea(new PrestitoHandler(gestorePrestito, gestoreLibro, gestoreUtente));
        } else if (areaCorrente instanceof LibroHandler) {
            setArea(new LibroHandler(gestoreLibro));
        } else if (areaCorrente instanceof UtenteHandler) {
            setArea(new UtenteHandler(gestoreUtente));
        }
    }



    private void setArea(AreaHandler area) {
        areaCorrente = area;

        addBtn.setOnAction(e -> {
            areaCorrente.onAdd();
            gestorePrestito.aggiornaStati();
            tabella.refresh();
        });
        removeBtn.setOnAction(e -> {

            Object item = tabella.getSelectionModel().getSelectedItem();

            if (item != null) {
                areaCorrente.onRemove(item);
                tabella.refresh();

            }

            gestorePrestito.aggiornaStati();

            tabella.refresh();


        });
        modifyButton.setOnAction(e -> {
            areaCorrente.onEdit(tabella) ;
            gestorePrestito.aggiornaStati();
            tabella.refresh();

        });
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
        
        barraRicerca.textProperty().addListener((observable, oldValue, newValue) -> {
            if (areaCorrente != null) {
                areaCorrente.filtraTabella(newValue);
            }
        });
    }

    private void mostraAlert(Alert.AlertType alertType, String msg) {
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void caricaDatiDummy() {
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


    }

}