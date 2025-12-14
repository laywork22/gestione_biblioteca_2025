package it.unisa.diem.softeng.librarymanager.handlers;

import it.unisa.diem.softeng.librarymanager.controllers.forms.FormPrestitoController;
import it.unisa.diem.softeng.librarymanager.exceptions.PrestitoException;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.StatoPrestitoEnum;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**@brief Gestore della schermata UI Area Prestiti
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del form grafico di inserimento o
 * modifica di un prestito.
 *
 * @invariant La TableView visualizza sempre una SortedList che avvolge una FilteredList.
 * Questo garantisce che le operazioni di filtraggio e ordinamento non modifichino
 * l'ordine o il contenuto della lista dati originale nel Manager.
 * @invariant La colonna "Prestiti Attivi" riflette sempre il rapporto:
 * (Prestiti in corso / MAX_PRESTITI).
 *
 * @note L'aggiornamento delle colonne è differito tramite Platform.runLater per
 * garantire la corretta inizializzazione del layout.
 *
 * @see GestorePrestito
 * @see FormPrestitoController
 */
public class PrestitoHandler implements AreaHandler<Prestito> {
    private final GestorePrestito gestore;
    private final GestoreLibro gestoreLibro;
    private final GestoreUtente gestoreUtente;

    private final Map<String, Comparator<Prestito>> mappaComparatori;

    private FilteredList<Prestito> listaFiltrata;
    private SortedList<Prestito> listaOrdinata;

    public PrestitoHandler(GestorePrestito gestore, GestoreLibro gestoreLibro, GestoreUtente gestoreUtente) {
        this.gestore = gestore;
        this.gestoreLibro = gestoreLibro;
        this.gestoreUtente = gestoreUtente;

        mappaComparatori = new HashMap<>();

        mappaComparatori.put("Utente (A-Z)", Comparator.comparing(p -> p.getUtente().getCognome(), String.CASE_INSENSITIVE_ORDER));
        mappaComparatori.put("Libro (A-Z)", Comparator.comparing(p -> p.getLibro().getTitolo(), String.CASE_INSENSITIVE_ORDER));
        mappaComparatori.put("Data Inizio (Recenti)", Comparator.comparing(Prestito::getDataInizio));
        mappaComparatori.put("Stato", Comparator.comparing(p -> p.getStato().toString()));
    }


    /**
     * @brief Rimuove un prestito (Restituzione/Cancellazione).
     * * @param p Il prestito da rimuovere.
     * @note La rimozione comporta il ripristino della disponibilità del libro
     * e il decremento del contatore prestiti dell'utente (gestito dal Manager).
     */
    @Override
    public void onRemove(Prestito p) {
        if(p != null) {
            try {
                this.gestore.remove(p);
            } catch (PrestitoException e) {
                mostraAlert(e.getMessage());
            }
        }
    }


    /**
     * @brief Apre la finestra modale per l'inserimento di un nuovo prestito.
     * Carica il file FXML 'PrestitoView.fxml', inizializza il form e attende la chiusura
     * della finestra (modalità WINDOW_MODAL)
    */
    @Override
    public void onAdd() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/librarymanager/PrestitoView.fxml"));

            Parent root = fxmlLoader.load();

            FormPrestitoController fu = fxmlLoader.getController();

            if(fu != null) {
                fu.init(gestore, gestoreLibro, gestoreUtente);
            }

            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Nuovo Prestito");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            mostraAlert("Errore caricamento PrestitoView.fxml");
        }

    }

     /**
      * @brief Apre la finestra modale per la modifica di un libro esistente.
      *Recupera l'elemento selezionato dalla tabella, popola il form con i dati attuali
      * e permette la modifica.
      *
      * @param tabella La TableView da cui recuperare l'elemento selezionato.
      * @pre La tabella deve avere un elemento selezionato.
     */
    @Override
    public void onEdit(TableView<Prestito> tabella) {
        Prestito p = tabella.getSelectionModel().getSelectedItem();

        if (p == null) return;

        //usa il controller per settare il form con setFormOnEdit
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/librarymanager/PrestitoView.fxml"));

            Parent root = fxmlLoader.load();

            FormPrestitoController fu = fxmlLoader.getController();

            if(fu != null) {
                fu.init(gestore, gestoreLibro, gestoreUtente);
            } else return;

            fu.setFormOnEdit(p);

            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Modifica Prestito");

            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            mostraAlert("Errore nel caricamento di LibroView.fxml");
        }

        tabella.refresh();
    }


    /**
     * @brief Configura la TableView dei prestiti.
     * * Crea colonne composte (Nested Columns) per raggruppare visivamente i dati
     * dell'Utente, del Libro e le Date, migliorando la leggibilità.
     *
     * @param[inout] table La TableView da configurare.
     */
    @Override
    public void setTableView(TableView<Prestito> table) {
        //puliamo la tabella dalla selezione e dalla lista ordinata precedente
        table.getSelectionModel().clearSelection();
        table.getSortOrder().clear();

        //rimpiazziamo la lista precdente con una vuota
        table.setItems(FXCollections.emptyObservableList());

        //rimuoviamo le colonne e il RowFactory precedente
        table.getColumns().clear();
        table.setRowFactory(null);


        table.refresh();

        /*
          ricostruzione posticipata della tabella
          per far in modo che carichi tutto correttamente senza sovrapporsi
          alle altre aree
         */
        Platform.runLater( () -> {
            TableColumn<Prestito, Integer> idClm = new TableColumn<>("ID");
            idClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getId()));
            idClm.setMaxWidth(100);


            TableColumn<Prestito, String> utenteClm = new TableColumn<>("Dati Utente");
            TableColumn<Prestito, String> nomeClm = new TableColumn<>("Nome");
            nomeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtente().getNome()));
            TableColumn<Prestito, String> cognomeClm = new TableColumn<>("Cognome");
            cognomeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtente().getCognome()));
            utenteClm.getColumns().addAll(nomeClm, cognomeClm);

            TableColumn<Prestito, String> libroClm = new TableColumn<>("Dati Libro");
            TableColumn<Prestito, String> titoloClm = new TableColumn<>("Titolo");
            titoloClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getLibro().getTitolo()));
            TableColumn<Prestito, String> autoreClm = new TableColumn<>("Autore");
            autoreClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getLibro().getAutore()));
            libroClm.getColumns().addAll(titoloClm, autoreClm);

            TableColumn<Prestito, String> dataClm = new TableColumn<>("Data Prestito");
            TableColumn<Prestito, String> dataInizioClm = new TableColumn<>("Data Inizio");
            dataInizioClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getDataInizio().toString()));
            TableColumn<Prestito, String> dataFineClm = new TableColumn<>("Data Fine");
            dataFineClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getDataFine().toString()));
            dataClm.getColumns().addAll(dataInizioClm, dataFineClm);

            TableColumn<Prestito, String> statoClm = new TableColumn<>("Stato");
            statoClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getStato().toString()));

            // Aggiunta colonne alla tabella
            table.getColumns().add(idClm);
            table.getColumns().addAll(utenteClm, libroClm);
            table.getColumns().add(dataClm);
            table.getColumns().add(statoClm);

            //settiamo il row factory (colore delle righe)
            setRigheTabella(table);

            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            //sleghiamo la lista precedente dal binding con la tabella
            if (this.listaOrdinata != null && this.listaOrdinata.comparatorProperty().isBound()) {
                this.listaOrdinata.comparatorProperty().unbind();
            }

            //creiamo una nuova lista ordinata e filtrata
            this.listaFiltrata = new FilteredList<>(gestore.getLista(), p -> true);
            this.listaOrdinata = new SortedList<>(listaFiltrata);

            //binding della lista alla tabella
            this.listaOrdinata.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(listaOrdinata);

            table.refresh();
        });
    }


    /**
     * @brief Aggiorna il predicato della FilteredList in base al testo di ricerca.
     * @param filtro La stringa da cercare (match parziale per utente, libro o stato).
     *
     * @see LibroHandler#ordina(String)
     * @see UtenteHandler#ordina(String)
     */
    @Override
    public void filtraTabella(String filtro) {
        listaFiltrata.setPredicate(gestore.getPredicato(filtro));
    }

    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaComparatori.keySet());
    }

    /**
     * @brief Ordina la tabella in base al criterio selezionato.
     * * Supporta ordinamenti complessi:
     * - Per Utente (alfabetico sul cognome).
     * - Per Libro (alfabetico sul titolo).
     * - Per Data Inizio (cronologico).
     * - Per Stato.
     *
     * @param[in] criterio La stringa che identifica il comparatore da usare.
     */
    @Override
    public void ordina(String criterio) {
        if (listaOrdinata != null && mappaComparatori.containsKey(criterio)) {
            if (listaOrdinata.comparatorProperty().isBound()) {
                listaOrdinata.comparatorProperty().unbind();
            }
            listaOrdinata.setComparator(mappaComparatori.get(criterio));
        }
    }


    /**
     * @brief Imposta il RowFactory della
     * tabella per evidenziare prestiti in scandenza o scaduti
     * e chiusi.
     *
     * @param[inout] t La tabella di cui impostare il RowFactory.
     */
    private void setRigheTabella(TableView<Prestito> t) {
        t.setRowFactory(tv -> new TableRow() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || !(item instanceof Prestito)) {
                    setStyle("");
                    return;
                }

                if (isSelected()) {
                    setStyle("");
                }
                else {
                    Prestito prestito = (Prestito) item;
                    try {
                        LocalDate now = LocalDate.now();
                        LocalDate scadenza = prestito.getDataFine();

                        if (prestito.getStato() == StatoPrestitoEnum.CHIUSO) {
                            setStyle("-fx-background-color: lightgreen;");
                        } else if (scadenza.isBefore(now)) {
                            setStyle("-fx-background-color: orange;");
                        } else if (!scadenza.isAfter(now.plusDays(5))) {
                            setStyle("-fx-background-color: yellow;");
                        } else {
                            setStyle("");
                        }
                    } catch (Exception e) { setStyle(""); }
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
