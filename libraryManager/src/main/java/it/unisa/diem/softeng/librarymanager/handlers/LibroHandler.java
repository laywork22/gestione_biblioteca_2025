package it.unisa.diem.softeng.librarymanager.handlers;

import it.unisa.diem.softeng.librarymanager.controllers.forms.FormLibroController;
import it.unisa.diem.softeng.librarymanager.exceptions.LibroException;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
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

/**@brief Gestore della schermata UI Area Libri
 *
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del file del di inserimento o
 * modifica di un Libro.
 *
 *
 * @see GestoreLibro
 * @see FormLibroController
 *
 * @note L'aggiornamento delle colonne è differito tramite Platform.runLater per
 * garantire la corretta inizializzazione del layout.
 */
public class LibroHandler implements AreaHandler<Libro> {
    private final GestoreLibro gestore;
    private final Map<String, Comparator<Libro>> mappaComparatori;
    private FilteredList<Libro> listaFiltrata;
    private SortedList<Libro> listaOrdinata;

    public LibroHandler(GestoreLibro gestore) {
        this.gestore = gestore;

        mappaComparatori = new HashMap<>();

        mappaComparatori.put("Titolo (A-Z)",  Comparator.comparing(p -> p.getTitolo(), String.CASE_INSENSITIVE_ORDER));;
        mappaComparatori.put("Autore (A-Z)", Comparator.comparing(p->p.getAutore(), String.CASE_INSENSITIVE_ORDER));;
        mappaComparatori.put("Anno (Recenti)", Comparator.comparing(Libro::getAnno).reversed());;
    }

    /**
     * @brief Gestisce la rimozione di un libro.
     * Tenta di rimuovere il libro selezionato tramite il gestore.
     * Se il libro non può essere rimosso (es. copie in prestito), l'eccezione
     * viene catturata e mostrata come Alert all'utente.
     *
     * @param[inout] l Il libro da rimuovere. Se null, l'operazione viene ignorata.
     */
    @Override
    public void onRemove(Libro l) {
        if (l != null) {
            try {
                gestore.remove(l);
            } catch (LibroException e) {
                mostraAlert(e.getMessage());
            }
        }
    }

    /**
     * @brief Apre la finestra modale per l'inserimento di un nuovo libro.
     * * Carica il file FXML 'LibroView.fxml', inizializza il form e attende la chiusura
     * della finestra (modalità WINDOW_MODAL).
     */
    @Override
    public void onAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/librarymanager/LibroView.fxml"));

            Parent root = fxmlLoader.load();

            FormLibroController fu = fxmlLoader.getController();

            if(fu != null) {
                fu.init(gestore);
            }

            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Nuovo Libro");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);

            stage.show();

        } catch (IOException e) {
            mostraAlert("Errore nel caricamento di LibroView.fxml");
        }
    }


    /**
     * @brief Apre la finestra modale per la modifica di un libro esistente.
     * * Recupera l'elemento selezionato dalla tabella, popola il form con i dati attuali
     * e permette la modifica.
     *
     * @param tabella La TableView da cui recuperare l'elemento selezionato.
     * @pre La tabella deve avere un elemento selezionato.
     */
    @Override
    public void onEdit(TableView<Libro> tabella) {
        //prendi il libro dalla tabella
        Libro l = tabella.getSelectionModel().getSelectedItem();

        if (l == null) return;

        //usa il controller per settare il form con setFormOnEdit
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/librarymanager/LibroView.fxml"));

            Parent root = fxmlLoader.load();

            FormLibroController fu = fxmlLoader.getController();

            if(fu != null) {
                fu.init(gestore);
            } else return;

            fu.setFormOnEdit(l);

            Stage stage = new Stage();

            stage.setResizable(false);

            stage.setTitle("Nuovo Libro");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("Errore nel caricamento di LibroView.fxml");
        }

        tabella.refresh();
    }


    /**
     * @brief Configura la TableView per la visualizzazione del catalogo libri.
     * Imposta le colonne per i dati anagrafici e la colonna calcolata per i prestiti attivi.
     * Utilizza Platform.runLater per garantire la coerenza del layout al caricamento della vista.
     *
     * @param table La TableView da configurare.
     */
    @Override
    public void setTableView(TableView<Libro> table) {
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
        Platform.runLater(() -> {
            TableColumn<Libro, String> autoreClm = new TableColumn<>("Autore");
            TableColumn<Libro, String> titoloClm = new TableColumn<>("Titolo");
            TableColumn<Libro, String> isbnClm = new TableColumn<>("ISBN");
            TableColumn<Libro, Integer> annoClm = new TableColumn<>("Anno");
            TableColumn<Libro, Integer> copieDisponibiliClm = new TableColumn<>("Copie Disponibili");
            TableColumn<Libro, Integer> copieTotaliClm = new TableColumn<>("Copie Totali");


            titoloClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getTitolo()));
            autoreClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAutore()));
            isbnClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getIsbn()));
            isbnClm.setSortable(false);
            annoClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getAnno()));
            copieTotaliClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getCopieTotali()));
            copieTotaliClm.setSortable(false);
            copieDisponibiliClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getCopieDisponibili()));
            copieDisponibiliClm.setSortable(false);

            table.getColumns().addAll(titoloClm, autoreClm, annoClm, isbnClm, copieDisponibiliClm, copieTotaliClm);


            setRigheTabella(table);

            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

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
     * @brief Imposta il RowFactory della
     * tabella per evidenziare libri cancellati
     * logicamente.
     * -Arancione: non attivo
     * -Default: attivo
     *
     * @param table La tabella di cui impostare il RowFactory.
     */
    private void setRigheTabella(TableView<Libro> table) {
        table.setRowFactory(tv -> new TableRow() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || !(item instanceof Libro)) {
                    setStyle("");
                    return;
                }

                if (isSelected()) {
                    setStyle("");
                }
                else {
                    Libro libro = (Libro) item;
                    if(!libro.isAttivo()) {
                        setStyle("-fx-background-color: orange; -fx-text-fill: darkgray;");
                    }
                    else {
                        setStyle("");
                    }
                }
                
            }
        });
    }

    /**
     * @brief Aggiorna il predicato della FilteredList in base al testo di ricerca.
     * @param filtro La stringa da cercare (match parziale su titolo o autore).
     */
    @Override
    public void filtraTabella(String filtro) {
        listaFiltrata.setPredicate(gestore.getPredicato(filtro));
    }

    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaComparatori.keySet());
    }

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
     * @brief Mostra su schermo l'alert con il messaggio
     * passato come parametro
     *
     * @param msg Il messaggio di errore/avviso da mostrare su schermo.
     */
    private void mostraAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}

