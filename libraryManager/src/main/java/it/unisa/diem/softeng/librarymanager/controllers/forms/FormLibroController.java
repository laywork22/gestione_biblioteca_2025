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
        if (isFormNotValid()) {
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
        Libro nuovoLibro = new Libro(titolo,autore, anno,isbn, copieTotali);



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

    /**
     * @brief Consente la chiusura dello Stage attivo.
     */
    private void chiudiFinestra() {
        Stage stage = (Stage) salvaLibroBtn.getScene().getWindow();
        stage.close();
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
        try {
            this.libroInModifica = l;
        } catch (NullPointerException e) {
            return;
        }

        titoloFld.setText(l.getTitolo());
        autoreFld.setText(l.getAutore());
        annoFld.setText(String.valueOf(l.getAnno()));
        isbnFld.setText(l.getIsbn());
        copieDisponibiliFld.setText(String.valueOf(l.getCopieDisponibili()));
        copieTotaliFld.setText(String.valueOf(l.getCopieTotali()));

        setInsModLblText();

    }


    /**
     * @brief Controlla che i campi del form siano vuoti affinché si possano gestire i messaggi di avviso/errore
     *
     * @return true se almeno un campo è vuoto, false se tutti sono pieni.
     */
    private boolean isFormNotValid() {
        return titoloFld.getText().isEmpty() ||
                autoreFld.getText().isEmpty() ||
                annoFld.getText().isEmpty() ||
                isbnFld.getText().isEmpty() ||
                copieDisponibiliFld.getText().isEmpty() ||
                copieTotaliFld.getText().isEmpty();
    }

    /**
     * @brief Gestisce la logica di cambio testo della Label riassuntivo del form quando si sceglie Aggiungi o Modifica nell'Area Libri
     *
     */
    private void setInsModLblText() {
        if (libroInModifica != null) {
            insModLbl.setText("Modifica libro");
        } else {
            insModLbl.setText("Inserimento nuovo libro");
        }
    }

}