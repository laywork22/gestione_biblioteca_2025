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

    /**
     * @brief Inizializza lo stato iniziale della vista.
     * Imposta la posizione del menu laterale e carica l'Area Prestiti come schermata di default.
     */
    @FXML
    public void initialize() {

        /*gestoreLibro.add(new Libro("Evangelion", "Shiro Sagisu", 2012, "983231-12", 10, 10));
        gestoreLibro.add(new Libro("Marc Jerkinson", "Hawk Two A", 2012, "283231-12", 12, 12));
        Libro libro=new Libro("Marc Jerkinson", "Hawk Two A", 2012, "283231-12", 12, 12);
        Utente Fabio=new Utente("fabio","volo","9832193","sasas@gmail.com");
        gestoreUtente.add(Fabio);
        gestorePrestito.add(new Prestito(Fabio,libro, LocalDate.now(),LocalDate.of(2036,12,29)));*/

        sideMenu.setTranslateX(-200);

        setArea(new PrestitoHandler(gestorePrestito, gestoreLibro, gestoreUtente));
        areaLbl.setText("Area Prestiti");

    }

    /**
     * @brief Gestisce l'animazione di apertura e chiusura del menu laterale.
     */
    @FXML
    private void toggleMenu() {

    }


    /**
     * @brief Imposta l'area di lavoro corrente (Libri, Utenti o Prestiti).
     * Aggiorna la tabella e collega le azioni dei pulsanti (Aggiungi, Rimuovi, Modifica)
     * all'handler specifico dell'area selezionata.
     * * @param area L'handler dell'area da visualizzare.
     */
    private void setArea(AreaHandler area) {
        areaCorrente = area;

        addBtn.setOnAction(e -> areaCorrente.onAdd());
        removeBtn.setOnAction(e -> areaCorrente.onRemove(tabella));
        modifyButton.setOnAction(e -> areaCorrente.onEdit(tabella));

        //qui ci va il riempimento del MenuButton con gli ordinamenti disponibili (MenuItem)

        areaCorrente.setTableView(tabella);
        //areaCorrente.filtraTabella(tabella);
    }

    /**
     * @brief Passa alla visualizzazione dell'Area Prestiti.
     * @param actionEvent L'evento generato dal click sul menu.
     */
    @FXML
    public void setAreaPrestiti(ActionEvent actionEvent) {

        setArea(new PrestitoHandler(gestorePrestito, gestoreLibro, gestoreUtente));

        areaLbl.setText("Area Prestiti");
        toggleMenu();

    }

    /**
     * @brief Passa alla visualizzazione dell'Area Libri.
     * @param actionEvent L'evento generato dal click sul menu.
     */
    @FXML
    public void setAreaLibri(ActionEvent actionEvent) {

    }

    /**
     * @brief Passa alla visualizzazione dell'Area Utenti.
     * @param actionEvent L'evento generato dal click sul menu.
     */
    @FXML
    public void setAreaUtenti(ActionEvent actionEvent) {

    }

    /**
     * @brief Salva la lista corrente sul file di default.
     * @param actionEvent L'evento generato dal click sul pulsante salva.
     */
    @FXML
    public void salvaFile(ActionEvent actionEvent) {

    }

    /**
     * @brief Apre una finestra di dialogo per salvare la lista corrente su un file specifico.
     * @param actionEvent L'evento generato dal click sul pulsante "Salva con nome".
     */
    @FXML
    public void salvaConNome(ActionEvent actionEvent) {

    }

    /**
     * @brief Apre una finestra di dialogo per caricare i dati da un file esistente.
     * @param actionEvent L'evento generato dal click sul pulsante apri.
     */
    @FXML
    public void apriFile(ActionEvent actionEvent) {

    }

    /**
     * @brief Applica l'ordinamento selezionato alla tabella visualizzata.
     * @param actionEvent L'evento generato dalla selezione di un criterio di ordinamento.
     */
    @FXML
    public void ordineTabella(ActionEvent actionEvent) {
    }


}