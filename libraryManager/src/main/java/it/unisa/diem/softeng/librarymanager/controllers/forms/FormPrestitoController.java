package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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

    @FXML
    public void initialize() {

    }


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


    @FXML
    public void viewListaUtenti(ActionEvent event) {

    }

    @FXML
    public void viewListaLibri(ActionEvent event) {

    }

    public void setGestore(GestorePrestito gestore) {
        this.gestore = gestore;
    }

    public void setFormOnEdit(Prestito p) {
        //imposta tutti i campi del form

    }


}