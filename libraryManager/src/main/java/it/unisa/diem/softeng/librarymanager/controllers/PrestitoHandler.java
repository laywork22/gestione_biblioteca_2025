package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.controllers.forms.FormPrestitoController;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.StatoPrestitoEnum;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
 * e di caricamento FXML del file del form al
 * gestore del form
 *
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

    @Override
    public void onRemove(Prestito p) {

        p.setStato(StatoPrestitoEnum.CHIUSO);

    }

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
            e.printStackTrace();
            System.out.println("Errore caricamento PrestitoView.fxml");
        }

    }

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
            System.out.println("Errore nel caricamento di LibroView.fxml");
        }

        tabella.refresh();
    }

    @Override
    public void setTableView(TableView<Prestito> table) {
        // --- FASE 1: PULIZIA PROFONDA (Immediata) ---

        // 1. Pulisce selezione e ordinamento
        // (Nota: Non chiamiamo unbind sulla tabella perché è ReadOnly)
        table.getSelectionModel().clearSelection();
        table.getSortOrder().clear();

        // 2. Svuota gli elementi
        table.setItems(FXCollections.emptyObservableList());

        // 3. Rimuove colonne e factory
        table.getColumns().clear();
        table.setRowFactory(null);

        // 4. Forza il refresh grafico
        table.refresh();

        // --- FASE 2: RICOSTRUZIONE (Posticipata) ---
        Platform.runLater(() -> {

            // --- Definizione Colonne ---
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

            // --- Row Factory (Colori) ---
            setRigheTabella(table);

            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // --- Binding e Popolamento ---
            // Se c'era una vecchia listaOrdinata legata, la sleghiamo (pulizia opzionale ma consigliata)
            if (this.listaOrdinata != null && this.listaOrdinata.comparatorProperty().isBound()) {
                this.listaOrdinata.comparatorProperty().unbind();
            }

            this.listaFiltrata = new FilteredList<>(gestore.getLista(), p -> true);
            this.listaOrdinata = new SortedList<>(listaFiltrata);

            // Qui avviene il legame: la lista ascolta la tabella
            this.listaOrdinata.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(listaOrdinata);
        });
    }


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


    private void setRigheTabella(TableView<Prestito> t) {
        t.setRowFactory(tv -> new TableRow() { // RAW TYPE
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || !(item instanceof Prestito)) {
                    setStyle("");
                    return;
                }

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
        });
    }

}
