package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;


/**
 * @brief Controller di gestione dell'interfaccia grafica del Form di inserimento/modifica di un Prestito.
 * Mette a disposizione dei metodi che consentono all'AreaHandler di manipolare la logica della Label e dei campi del form.
 *
 * @author Gruppo 12
 *
 */
public class FormPrestitoController {

    private GestorePrestito gestore;
    private Prestito prestitoInModifica = null;

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


    //salva il libro modificato nella lista
    @FXML
    public void salvaNuovoPrestito(ActionEvent event) {
        Prestito p = new Prestito(utentiCb.getValue(), libroCb.getValue(), dataInizioDp.getValue(), dataScadenzaDp.getValue());

        gestore.add(p);
    }


    @FXML
    public void annullaNuovoPrestito(ActionEvent event) {
        chiudiFinestra();
    }



    //metodo handle del ComboBox per visualizzare la lista di Prestiti
    @FXML
    public void viewListaUtenti(ActionEvent event) {

    }

    //metodo handle del ComboBox per visualizzare la lista di Libri
    @FXML
    public void viewListaLibri(ActionEvent event) {

    }


    /**
     * @brief Consente di impostare il GestorePrestito corrispondente.
     *
     * È utilizzato da un LibroHandler affinché il controller abbia un riferimento al GestoreLibro corrispondente.
     * @param gestore Il gestore dellArea Prestiti
     */
    public void init(GestorePrestito gestore, GestoreLibro gb, GestoreUtente gu) {
        this.gestore = gestore;

        //settare la lista di libri e utenti nei combobox

        //definire il modo in cui libri e utenti sono visualizzati nei combobox
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