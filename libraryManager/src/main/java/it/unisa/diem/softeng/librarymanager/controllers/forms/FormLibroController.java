package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FormLibroController {
    GestoreLibro gestore;
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
    private void handleSalva(ActionEvent event) {
        String titolo = titoloFld.getText();
        String autore = autoreFld.getText();
        int anno = Integer.parseInt(annoFld.getText());
        String isbn = isbnFld.getText();
        int copieDisponibili = Integer.parseInt(copieDisponibiliFld.getText());
        //MANCA GESTORE CAMPI VUOTI E GESTIONE ISBN DUPLICATI!!!
        Libro nuovoLibro = new Libro(titolo,autore, anno,isbn,copieDisponibili);
        gestore.add(nuovoLibro);

        chiudiFinestra();
    }

    @FXML
    private void handleAnnulla(ActionEvent event) {
        chiudiFinestra();
    }

    private void chiudiFinestra() {
        Stage stage = (Stage) salvaLibroBtn.getScene().getWindow();
        stage.close();
    }

    public void setGestore(GestoreLibro gestore) {
        this.gestore = gestore;
    }

    public void getLibroOnEdit(Libro l) {
        autoreFld.setText(l.getAutore());
        titoloFld.setText(l.getTitolo());
        annoFld.setText(String.valueOf(l.getAnno()));
        isbnFld.setText(l.getIsbn());
        copieDisponibiliFld.setText(String.valueOf(l.getCopieDisponibili()));
        copieTotaliFld.setText(String.valueOf(l.getCopieTotali()));
    }
}