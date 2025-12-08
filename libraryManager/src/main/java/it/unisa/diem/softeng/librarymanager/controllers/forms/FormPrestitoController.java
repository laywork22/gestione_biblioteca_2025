package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;


/**
 * @brief Controller di gestione dell'interfaccia grafica del Form di inserimento/modifica di un Prestito.
 * Mette a disposizione dei metodi che consentono all'AreaHandler di manipolare la logica della Label e dei campi del form.
 *
 * @author Gruppo 12
 *
 */
public class FormPrestitoController {

    private GestorePrestito gp;
    private GestoreLibro gl;
    private GestoreUtente gu;
    private final Prestito prestitoInModifica = null;

    @FXML
    private ComboBox<Utente> utentiCb;
    @FXML
    private Button salvaBtn;
    @FXML
    private ComboBox<Libro> libroCb;
    @FXML
    private Button annullaBtn;
    @FXML
    private DatePicker dataInizioDp;
    @FXML
    private DatePicker dataScadenzaDp;
    @FXML
    private Label insModFld;

    //salva il libro modificato nella lista
    /**
     * @brief Salva il Prestito nella lista corrispondente, rendendolo
     *  visibile in tabella
     *
     * @param event L'evento generato dal click sul pulsante salva.
     */
    @FXML
    public void salvaNuovoPrestito(ActionEvent event) {
        Prestito p = new Prestito(utentiCb.getValue(), libroCb.getValue(), dataInizioDp.getValue(), dataScadenzaDp.getValue());

        gp.add(p);
    }

    /**
     * @brief Annulla qualsiasi operazione (modifica o inserimento) nel form, chiudendo
     * la finestra.
     *
     * @param event L'evento generato dal click sul pulsante annulla.
     */
    @FXML
    public void annullaNuovoPrestito(ActionEvent event) {
        chiudiFinestra();
    }

    /**
     * @brief Consente di impostare il GestorePrestito  e di popolare le ComboBox.
     *
     * È utilizzato da un LibroHandler affinché il controller abbia un riferimento al GestoreLibro corrispondente.
     * @param gp Il gestore dellArea Prestiti
     * @param gl il gestore dell'Area Libri
     * @param gu il gestore dell'Area Utenti
     *
     * @see
     */
    public void init(GestorePrestito gp, GestoreLibro gl, GestoreUtente gu) {
        this.gp = gp;
        this.gl = gl;
        this.gu = gu;

        //TODO settare la lista di libri e utenti nei combobox
        setComboBox();
    }

    /**
     * @brief Popola le ComboBox con le rispettive liste.
     */
    private void setComboBox() {

    }

    /**
     * @brief Consente la chiusura dello Stage attivo.
     */
    private void chiudiFinestra() {
    }

    /**
     * @brief Imposta tutte le informazioni scelte sul form di modifica in modo da renderle modificabili manualmente.
     *
     * @param p il Prestito da cui estrarre gli attributi da impostare sui vari campi del form
     */
    public void setFormOnEdit(Prestito p) {

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
     * @brief Gestisce la logica di cambio testo della Label riassuntivo del form quando si sceglie Aggiungi o Modifica nell'Area Prestiti.
     *
     */
    private void setInsModLblText() {

    }

}