package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * @brief Controller di gestione dell'interfaccia grafica del Form di inserimento/modifica di un Utente.
 * Mette a disposizione dei metodi che consentono all'AreaHandler di manipolare la logica della Label e dei campi del form.
 *
 * @author Gruppo 12
 *
 */
public class FormUtenteController {

    private GestoreUtente gestore;
    private Utente utenteInModifica = null;

    @FXML
    private TextField nomeFld;
    @FXML
    private TextField cognomeFld;
    @FXML
    private TextField matricolaFld;
    @FXML
    private TextField emailFld;
    @FXML
    private Button salvaBtn;
    @FXML
    private Button annullaBtn;
    @FXML
    private Label insModLbl;


    @FXML
    private void handleSalva(ActionEvent event) {
        String nome = nomeFld.getText();
        String cognome = cognomeFld.getText();
        String matricola = matricolaFld.getText();
        String email = emailFld.getText();
        //MANCA GESTORE CAMPI VUOTI
        Utente nuovoUtente = new Utente(nome, cognome, matricola, email);
        gestore.add(nuovoUtente);

        chiudiFinestra();
    }

    @FXML
    private void handleAnnulla(ActionEvent event) {
        chiudiFinestra();
    }

    /**
     * @brief Consente di impostare il GestoreUtente corrispondente.
     *
     * È utilizzato da un UtenteHandler affinché il controller abbia un riferimento al GestoreLibro corrispondente.
     * @param gestore Il gestore dellArea Utenti
     */
    public void init(GestoreUtente gestore) {
        this.gestore = gestore;
    }

    /**
     * @brief Consente la chiusura dello Stage attivo.
     */
    private void chiudiFinestra() {
        Stage stage = (Stage) salvaBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * @brief Imposta tutte le informazioni scelte sul form di modifica in modo da renderle modificabili manualmente.
     *
     * @param u l'utente da cui estrarre gli attributi da impostare sui vari campi del form
     */
    public void setFormOnEdit(Utente u) {
        //imposta tutti i campi del form

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
     * @brief Gestisce la logica di cambio testo della Label riassuntivo del form quando si sceglie Aggiungi o Modifica nell'Area Utenti
     *
     */
    private void setInsModLblText() {

    }
}