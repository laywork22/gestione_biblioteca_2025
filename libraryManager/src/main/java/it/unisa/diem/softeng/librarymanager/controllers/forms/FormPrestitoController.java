package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class FormPrestitoController {

    private GestorePrestito gestore;
    private FilteredList<Prestito> listaFiltrata;
    private SortedList<Prestito> listaOrdinata;

    @FXML
    private ComboBox<String> utentiCb;
    @FXML
    private Button salvaBtn;
    @FXML
    private ComboBox<String> libroCb;
    @FXML
    private Button annullaBtn;
    @FXML
    private DatePicker dataInizioDp;
    @FXML
    private DatePicker dataScadenzaDp;

    //salva il libro modificato nella lista
    @FXML
    public void salvaNuovoPrestito(ActionEvent event) {

    }


    @FXML
    public void annullaNuovoPrestito(ActionEvent event) {
        chiudiFinestra();
    }


    private void chiudiFinestra() {
        Stage stage = (Stage) salvaBtn.getScene().getWindow();
        stage.close();
    }

    //metodo handle del ComboBox per visualizzare la lista di Prestiti
    @FXML
    public void viewListaUtenti(ActionEvent event) {

    }

    //metodo handle del ComboBox per visualizzare la lista di Libri
    @FXML
    public void viewListaLibri(ActionEvent event) {

    }

    public void init(GestorePrestito gestore, GestoreLibro gb, GestoreUtente gu) {
        this.gestore = gestore;

        //settare la lista di libri e utenti nei combobox

        //definire il modo in cui libri e utenti sono visualizzati nei combobox
    }

    public void setFormOnEdit(Prestito p) {
        //imposta tutti i campi del form


    }


}