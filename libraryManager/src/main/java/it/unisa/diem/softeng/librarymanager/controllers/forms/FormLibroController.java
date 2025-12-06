package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;

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
        if (isNotValid()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Campi vuoti");
            al.setHeaderText(null);
            al.setContentText("Alcuni campi sono vuoti, impossibile salvare le modifiche effettuate");

            al.showAndWait();
        }

        String titolo = titoloFld.getText();
        String autore = autoreFld.getText();
        int anno = Integer.parseInt(annoFld.getText());
        String isbn = isbnFld.getText();
        int copieDisponibili = Integer.parseInt(copieDisponibiliFld.getText());
        int copieTotali = Integer.parseInt(copieTotaliFld.getText());
        //MANCA GESTORE CAMPI VUOTI E GESTIONE ISBN DUPLICATI!!!
        Libro nuovoLibro = new Libro(titolo,autore, anno,isbn, copieTotali, copieDisponibili);



        if (libroInModifica != null) {
            gestore.modifica(libroInModifica, nuovoLibro);
        } else {
            gestore.add(nuovoLibro);
        }

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

    public void init(GestoreLibro gestore) {
        this.gestore = gestore;
    }

    public void setLibroOnEdit(Libro l) {
        this.libroInModifica = l;

        titoloFld.setText(l.getTitolo());
        autoreFld.setText(l.getAutore());
        annoFld.setText(String.valueOf(l.getAnno()));
        isbnFld.setText(l.getIsbn());
        copieDisponibiliFld.setText(String.valueOf(l.getCopieDisponibili()));
        copieTotaliFld.setText(String.valueOf(l.getCopieTotali()));

        setInsModLblText();

    }

    private boolean isNotValid() {
        return titoloFld.getText().isEmpty() ||
                autoreFld.getText().isEmpty() ||
                annoFld.getText().isEmpty() ||
                isbnFld.getText().isEmpty() ||
                copieDisponibiliFld.getText().isEmpty() ||
                copieTotaliFld.getText().isEmpty();
    }

    private void setInsModLblText() {
        if (libroInModifica != null) {
            insModLbl.setText("Modifica libro");
        } else {
            insModLbl.setText("Inserimento nuovo libro");
        }
    }

}