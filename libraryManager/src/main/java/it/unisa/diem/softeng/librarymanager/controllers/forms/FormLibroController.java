package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * @brief Controller di gestione dell'interfaccia grafica del Form di inserimento/modifica di un Libro.
 * Mette a disposizione dei metodi che consentono all'AreaHandler di manipolare la logica della Label e dei campi del form.
 *
 * @author Gruppo 12
 *
 */
public class FormLibroController {
    private GestoreLibro gestore;
    private Libro libroInModifica = null;

    @FXML
    private AnchorPane insertBookContent;
    @FXML
    private TextField annoFld;
    @FXML
    private TextField titoloFld;
    @FXML
    private TextField autoreFld;
    @FXML
    private Button salvaLibroBtn;
    @FXML
    private TextField copieDisponibiliFld;
    @FXML
    private TextField isbnFld;
    @FXML
    private TextField copieTotaliFld;
    @FXML
    private Button annullaBtn;
    @FXML
    private Label insModLbl;

    @FXML
    public void initialize() {
        setInsModLblText();
    }

    @FXML
    private void handleSalva(ActionEvent event) {

    }

    @FXML
    private void handleAnnulla(ActionEvent event) {
        chiudiFinestra();
    }

    /**
     * @brief Consente la chiusura dello Stage attivo.
     */
    private void chiudiFinestra() {
    }

    /**
     * @brief Consente di impostare il GestoreLibro corrispondente.
     *
     * È utlizzato da un LibroHandler affinchè il controller abbia un riferimento al GestoreLibro corrispondente.
     * @param gestore Il gestore dell'Area Libri
     */
    public void init(GestoreLibro gestore) {
        this.gestore = gestore;
    }

    /**
     * @brief Imposta tutte le informazioni scelte sul form di modifica in modo da renderle modificabili manualmeente.
     *
     * @param l il Libro da cui estrarre gli attributi da impostare sui vari campi del form
     */
    public void setFormOnEdit(Libro l) {

    }


    /**
     * @brief Controlla che i campi del form siano vuoti affinché si possano gestire i messaggi di avviso/errore
     *
     * @return true se almeno un campo è vuoto, false se tutti sono pieni.
     */
    private boolean isFormNotValid() {
        return false;
    }

    /**
     * @brief Gestisce la logica di cambio testo della Label riassuntivo del form quando si sceglie Aggiungi o Modifica nell'Area Libri
     *
     */
    private void setInsModLblText() {

    }

}