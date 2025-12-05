package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormUtenteController {

    private GestoreUtente gestore;

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

    @FXML
    private void viewListaUtenti(ActionEvent event){

    }

    @FXML
    private void viewListaLibri(ActionEvent event){
    }

    public void init(GestoreUtente gestore) {
        this.gestore = gestore;
    }

    private void chiudiFinestra() {
        Stage stage = (Stage) salvaBtn.getScene().getWindow();
        stage.close();
    }

    public void setFormOnEdit(Utente u) {
        //imposta tutti i campi del form

    }
}