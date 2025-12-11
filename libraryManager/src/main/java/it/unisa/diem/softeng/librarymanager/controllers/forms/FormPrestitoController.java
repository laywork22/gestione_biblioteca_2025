package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.exceptions.LimitePrestitoException;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    private Label insModFld;

    //salva il libro modificato nella lista
    @FXML
    public void salvaNuovoPrestito(ActionEvent event) {
        if (isFormNotValid()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Campi vuoti");
            al.setHeaderText(null);
            al.setContentText("Alcuni campi sono vuoti, impossibile salvare le modifiche effettuate");

            al.showAndWait();
        }
            Prestito p = new Prestito(utentiCb.getValue(), libroCb.getValue(), dataInizioDp.getValue(), dataScadenzaDp.getValue());
            try {
                if (prestitoInModifica == null) {
                    gp.add(p);
                } else {

                    p.setStato(prestitoInModifica.getStato());
                    gp.modifica(prestitoInModifica, p);
                }
            }catch (LimitePrestitoException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        chiudiFinestra();
        }




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
        utentiCb.setItems(gu.getLista());
        libroCb.setItems(gl.getLista());

        utentiCb.setPromptText("Selezionare  Utente");
        libroCb.setPromptText("Selezionare  Libro");

        //definire il modo in cui libri e utenti sono visualizzati nei combobox
        utentiCb.setConverter(new StringConverter<Utente>() {
            @Override
            public String toString(Utente u) {
                if (u == null) return null;
                return u.getCognome() + " " + u.getNome() + " (" + u.getMatricola() + ")";
            }

            @Override
            public Utente fromString(String s) {
                return null;
            }

        });
        libroCb.setConverter(new StringConverter<Libro>() {
            @Override
            public String toString(Libro l) {
                if (l == null) return null;
                return l.getTitolo() + " - " + l.getAutore();
            }

            @Override
            public Libro fromString(String string) {
                return null;
            }
        });
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
        if (p == null) return;

        this.prestitoInModifica = p;

        utentiCb.setValue(p.getUtente());
        libroCb.setValue(p.getLibro());
        dataInizioDp.setValue(p.getDataInizio());
        dataScadenzaDp.setValue(p.getDataFine());

        utentiCb.setDisable(true);
        libroCb.setDisable(true);

        setInsModLblText();
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
        if (insModFld == null) return;

        if (prestitoInModifica == null) {
            insModFld.setText("Nuovo Prestito");
        } else {
            insModFld.setText("Modifica Prestito");
        }
    }

}