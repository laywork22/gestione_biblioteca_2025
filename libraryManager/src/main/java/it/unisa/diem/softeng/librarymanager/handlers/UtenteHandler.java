package it.unisa.diem.softeng.librarymanager.handlers;


import it.unisa.diem.softeng.librarymanager.controllers.forms.FormUtenteController;
import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
/**@brief Gestore della schermata UI Area utenti.
 *
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del file di inserimento o
 * modifica di un utente.
 *
 * @invariant La TableView visualizza sempre una SortedList che avvolge una FilteredList.
 * Questo garantisce che le operazioni di filtraggio e ordinamento non modifichino
 * l'ordine o il contenuto della lista dati originale nel Manager.
 * @invariant La colonna "Prestiti Attivi" riflette sempre il rapporto:
 * (Prestiti in corso / MAX_PRESTITI).
 *
 * @see GestoreUtente
 * @see FormUtenteController
 *
 * @note L'aggiornamento delle colonne è differito tramite Platform.runLater per
 * garantire la corretta inizializzazione del layout.
 */
public class UtenteHandler implements AreaHandler<Utente> {
    private final GestoreUtente gestore;
    private final Map<String, Comparator<Utente>> mappaOrdinamento;
    private FilteredList<Utente> listaFiltrata;
    private SortedList<Utente> listaOrdinata;

    public UtenteHandler(GestoreUtente gestore) {
        this.gestore = gestore;

        mappaOrdinamento = new HashMap<>();
        mappaOrdinamento.put("Cognome (A-Z)", Comparator.comparing(Utente::getCognome, String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * @brief Apre la finestra modale per l'inserimento di un nuovo utente.
     * * Carica il file FXML 'UtenteView.fxml', inizializza il form e attende la chiusura
     * della finestra (modalità WINDOW_MODAL).
     */
    @Override
    public void onAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/librarymanager/UtenteView.fxml"));

            Parent root = fxmlLoader.load();

            FormUtenteController fu = fxmlLoader.getController();

            if(fu == null) return;
            else {
                fu.init(gestore);
            }

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Nuovo Utente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException e) {
            mostraAlert("Errore nel caricamento di UtenteView.fxml");
        }
    }


    /**
     * @brief Gestisce la rimozione di un utente.
     * * Verifica che l'utente non abbia prestiti attivi prima di procedere alla rimozione.
     * In caso di violazione del vincolo, mostra un messaggio di errore.
     *
     * @param u L'utente da rimuovere.
     * @throws PrestitoException Gestita internamente (mostra Alert) se l'utente ha prestiti in corso.
     */
    @Override
    public void onRemove(Utente u) {
        if (u != null) {
            try {
                gestore.remove(u);
            } catch (PrestitoException e) {
                mostraAlert(e.getMessage());
            }
        }
    }

    /**
     * @brief Apre la finestra modale per la modifica di un utente esistente.
     * * Recupera l'elemento selezionato dalla tabella, popola il form con i dati attuali
     * e permette la modifica.
     *
     * @param tabella La TableView da cui recuperare l'elemento selezionato.
     * @pre La tabella deve avere un elemento selezionato.
     */
    @Override
    public void onEdit(TableView<Utente> tabella) {
        Utente u = tabella.getSelectionModel().getSelectedItem();

        if (u == null) return;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/librarymanager/UtenteView.fxml"));

            Parent root = fxmlLoader.load();

            FormUtenteController fu = fxmlLoader.getController();

            if(fu == null) return;
            else {
                fu.init(gestore);
            }

            fu.setFormOnEdit(u);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Nuovo Utente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException e) {
            mostraAlert("Errore nel caricamento di UtenteView.fxml");
        }

        tabella.refresh();
    }


    /**
     * @brief Configura la TableView per la visualizzazione degli utenti.
     * * Imposta le colonne per i dati anagrafici e la colonna calcolata per i prestiti attivi.
     * Utilizza Platform.runLater per garantire la coerenza del layout al caricamento della vista.
     *
     * @param[inout] table La TableView da configurare.
     */
    @Override
    public void setTableView(TableView<Utente> table) {
        //puliamo la tabella dalla selezione e dalla lista ordinata precedente
        table.getSelectionModel().clearSelection();
        table.getSortOrder().clear();

        //rimpiazziamo la lista precdente con una vuota
        table.setItems(FXCollections.emptyObservableList());

        //rimuoviamo le colonne e il RowFactory precedente
        table.getColumns().clear();
        table.setRowFactory(null);

        table.refresh();

        /*ricostruzione posticipata della tabella
          per far in modo che carichi tutto correttamente senza sovrapporsi
          alle altre aree */
        Platform.runLater(() -> {
            // Definizione Colonne
            TableColumn<Utente, String> nomeClm = new TableColumn<>("Nome");
            nomeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getNome()));

            TableColumn<Utente, String> cognomeClm = new TableColumn<>("Cognome");
            cognomeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getCognome()));

            TableColumn<Utente, String> matricolaClm = new TableColumn<>("Matricola");
            matricolaClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getMatricola()));

            TableColumn<Utente, String> emailClm = new TableColumn<>("Email");
            emailClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getEmail()));

            TableColumn<Utente, String> prestitiAttiviClm = new TableColumn<>("Prestiti Attivi");
            prestitiAttiviClm.setCellValueFactory(r -> {
                int count = r.getValue().getCountPrestiti();
                return new SimpleStringProperty(count + " / " + Utente.MAX_PRESTITI);
            });

            table.getColumns().addAll(nomeClm, cognomeClm, matricolaClm, emailClm, prestitiAttiviClm);

            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            setRigheTabella(table);

            //sleghiamo la lista precedente dal binding con la tabella
            if (this.listaOrdinata != null && this.listaOrdinata.comparatorProperty().isBound()) {
                this.listaOrdinata.comparatorProperty().unbind();
            }

            //creiamo una nuova lista ordinata e filtrata
            this.listaFiltrata = new FilteredList<>(gestore.getLista(), p -> true);
            this.listaOrdinata = new SortedList<>(listaFiltrata);

            this.listaOrdinata.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(listaOrdinata);

            table.refresh();
        });
    }


    /**
     * @brief Aggiorna il predicato della FilteredList in base al testo di ricerca.
     * @param[in] filtro La stringa da cercare (match parziale su nome, cognome o matricola).
     */
    @Override
    public void filtraTabella(String filtro) {
        listaFiltrata.setPredicate(gestore.getPredicato(filtro));
    }


    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaOrdinamento.keySet());
    }


    /**
     *
     * @param[in] criterio Il nome del criterio di ordinamento da applicare
     * (ordinamento su nome o cognome).
     */
    @Override
    public void ordina(String criterio) {
        if (listaOrdinata != null && mappaOrdinamento.containsKey(criterio)) {
            if (listaOrdinata.comparatorProperty().isBound()) {
                listaOrdinata.comparatorProperty().unbind();
            }
            listaOrdinata.setComparator(mappaOrdinamento.get(criterio));
        }
    }


    /**
     * @brief Imposta il RowFactory della
     * tabella per evidenziare utenti cancellati
     * logicamente.
     *
     * @param[inout] t La tabella di cui impostare il RowFactory.
     */
    private void setRigheTabella(TableView<Utente> t) {
        t.setRowFactory(tv -> new TableRow() { 
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || !(item instanceof Utente)) {
                    setStyle("");
                    return;
                }

                if(isSelected()) {
                    setStyle("");
                }
                else {
                    Utente utente = (Utente) item;
                    if (!utente.isAttivo()) {
                        setStyle("-fx-background-color: orange; -fx-text-fill: darkgray;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    /**
     * @brief Mostra su schermo l'alert con il messaggio
     * passato come parametro
     *
     * @param[in] msg Il messaggio di errore/avviso da mostrare su schermo.
     */
    private void mostraAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}