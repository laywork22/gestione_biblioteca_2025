package it.unisa.diem.softeng.librarymanager.controllers.forms;

import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @brief Controller per la gestione del form di inserimento utenti.
 *
 * Questa classe gestisce l'interazione con l'utente nella finestra di dialogo
 * per l'inserimento o la modifica dei dati anagrafici di un utente della biblioteca.
 */
public class FormUtenteController {

    /**
     * @brief Riferimento al gestore della logica di business degli utenti.
     */
    private GestoreUtente gestore;

    /**
     * @brief Campo di testo per l'inserimento del nome dell'utente.
     */
    @FXML
    private TextField nomeFld;

    /**
     * @brief Campo di testo per l'inserimento del cognome dell'utente.
     */
    @FXML
    private TextField cognomeFld;

    /**
     * @brief Campo di testo per l'inserimento della matricola (identificativo univoco).
     */
    @FXML
    private TextField matricolaFld;

    /**
     * @brief Campo di testo per l'inserimento dell'indirizzo email.
     */
    @FXML
    private TextField emailFld;

    @FXML
    private Button salvaBtn;

    @FXML
    private Button annullaBtn;

    /**
     * @brief Metodo di inizializzazione del controller.
     *
     * Viene chiamato automaticamente da JavaFX dopo il caricamento del file FXML.
     * Può essere usato per inizializzare validatori o impostare valori di default.
     */
    @FXML
    public void initialize() {
    }

    /**
     * @brief Gestisce l'azione di salvataggio di un nuovo utente.
     *
     * Recupera i dati anagrafici dai campi di testo, crea una nuova istanza
     * di Utente e la aggiunge al sistema tramite il GestoreUtente.
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
     * Chiude la finestra corrente ignorando qualsiasi dato inserito.
     *
     * @param event L'evento generato dal click sul bottone "Annulla".
     */
    @FXML
    private void handleAnnulla(ActionEvent event) {

    }

    /**
     * @brief Gestisce la visualizzazione della lista utenti (opzionale/navigazione).
     *
     * Metodo placeholder nel caso il form debba interagire con altre viste o
     * aggiornare liste esterne.
     *
     * @param event L'evento scatenante.
     */
    @FXML
    private void viewListaUtenti(ActionEvent event){
    }

    /**
     * @brief Gestisce la visualizzazione della lista libri (opzionale/navigazione).
     *
     * Metodo placeholder per eventuali interazioni future o navigazione.
     *
     * @param event L'evento scatenante.
     */
    @FXML
    private void viewListaLibri(ActionEvent event){
    }

    /**
     * @brief Imposta il gestore degli utenti.
     *
     * Metodo setter per iniettare la dipendenza GestoreUtente necessaria
     * per salvare i dati.
     *
     * @param gestore L'istanza di GestoreUtente da utilizzare.
     */
    public void setGestore(GestoreUtente gestore) {
        this.gestore = gestore;
    }

    /**
     * @brief Chiude la finestra corrente (Stage).
     *
     * Recupera lo Stage corrente a partire dalla scena di uno dei controlli
     * e ne richiede la chiusura.
     */
    private void chiudiFinestra() {
        // Implementazione:
        // Stage stage = (Stage) salvaBtn.getScene().getWindow();
        // stage.close();
    }
}