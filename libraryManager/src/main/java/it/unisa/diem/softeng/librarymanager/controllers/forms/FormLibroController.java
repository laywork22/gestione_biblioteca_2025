package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @brief Controller per la gestione del form di inserimento libri.
 *
 * Questa classe gestisce l'interazione con l'utente nella finestra di dialogo
 * per l'inserimento di un nuovo libro. Recupera i dati dai campi di testo,
 * effettua le conversioni necessarie e delega l'aggiunta al GestoreLibro.
 */
public class FormLibroController {

    /**
     * @brief Riferimento al gestore della logica  dei libri.
     */
    GestoreLibro gestore;

    @FXML
    private AnchorPane insertBookContent;

    /**
     * @brief Campo di testo per l'inserimento dell'anno di pubblicazione.
     */
    @FXML
    private TextField annoFld;

    /**
     * @brief Campo di testo per l'inserimento del titolo del libro.
     */
    @FXML
    private TextField titoloFld;

    /**
     * @brief Campo di testo per l'inserimento dell'autore (o autori).
     */
    @FXML
    private TextField autoreFld;

    @FXML
    private Button salvaLibroBtn;

    /**
     * @brief Campo di testo per le copie attualmente disponibili.
     */
    @FXML
    private TextField copieDisponibiliFld;

    /**
     * @brief Campo di testo per il codice ISBN univoco.
     */
    @FXML
    private TextField isbnFld;

    /**
     * @brief Campo di testo per il numero totale di copie possedute.
     */
    @FXML
    private TextField copieTotaliFld;

    @FXML
    private Button annullaBtn;

    /**
     * @brief Gestisce l'azione di salvataggio di un nuovo libro.
     *
     * Recupera i valori dai campi di testo, converte i valori numerici e crea
     * una nuova istanza di Libro. Successivamente aggiunge il libro tramite il gestore
     * e chiude la finestra.
     *
     * @param event L'evento generato dal click sul bottone "Salva".
     *
     */
    @FXML
    private void handleSalva(ActionEvent event) {

    }

    /**
     * @brief Gestisce l'azione di annullamento.
     *
     * Chiude la finestra di inserimento senza salvare alcun dato.
     *
     * @param event L'evento generato dal click sul bottone "Annulla".
     */
    @FXML
    private void handleAnnulla(ActionEvent event) {

    }

    /**
     * @brief Chiude la finestra corrente (Stage).
     *
     * Recupera lo Stage a partire dalla scena del bottone di salvataggio
     * e ne richiede la chiusura.
     */
    private void chiudiFinestra() {

    }

    /**
     * @brief Imposta il gestore dei libri.
     *
     * Permette di iniettare l'istanza del GestoreLibro necessaria per
     * l'aggiunta dei dati.
     *
     * @param gestore L'istanza di GestoreLibro da utilizzare.
     */
    public void setGestore(GestoreLibro gestore) {
        this.gestore = gestore;
    }
}