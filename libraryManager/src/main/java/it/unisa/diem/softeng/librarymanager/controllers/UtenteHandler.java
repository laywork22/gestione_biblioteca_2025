package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.comparators.CognomeUtenteComparator;
import it.unisa.diem.softeng.librarymanager.controllers.forms.FormUtenteController;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
/**@brief Gestore della schermata UI Area utenti
 *
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del file del form al
 * gestore del form
 *
 */
public class UtenteHandler implements AreaHandler<Utente> {
    private final GestoreUtente gestore;
    private final Map<String, Comparator<Utente>> mappaOrdinamento;
    private FilteredList<Utente> listaFiltrata;
    private SortedList<Utente> listaOrdinata;

    public UtenteHandler(GestoreUtente gestore) {
        this.gestore = gestore;

        mappaOrdinamento = new HashMap<>();
        mappaOrdinamento.put("Cognome (A-Z)", new CognomeUtenteComparator());
    }

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
            System.out.println("Errore nel caricamento di UtenteView.fxml");
        }
    }

    @Override
    public void onRemove(Utente u) {
        if (u == null) throw new RuntimeException();

        u.setAttivo(false);
    }

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
            e.printStackTrace(); // Meglio stampare lo stack trace completo per debug
            System.out.println("Errore nel caricamento di UtenteView.fxml");
        }

        tabella.refresh();
    }


    @Override
    public void setTableView(TableView<Utente> table) {
        // --- FASE 1: PULIZIA PROFONDA (Immediata) ---

        // 1. Pulisce selezione e ordinamento
        table.getSelectionModel().clearSelection();
        table.getSortOrder().clear();

        // 2. Svuota gli elementi
        table.setItems(FXCollections.emptyObservableList());

        // 3. Rimuove colonne e factory
        table.getColumns().clear();
        table.setRowFactory(null);

        // 4. Forza refresh grafico
        table.refresh();

        // --- FASE 2: RICOSTRUZIONE (Posticipata) ---
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

            // Aggiunta colonne
            table.getColumns().addAll(nomeClm, cognomeClm, matricolaClm, emailClm, prestitiAttiviClm);

            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // RowFactory (Evidenzia limite raggiunto)
            setRigheTabella(table);

            // Binding e Popolamento
            // CORREZIONE: Slegare la LISTA, non la TABELLA
            if (this.listaOrdinata != null && this.listaOrdinata.comparatorProperty().isBound()) {
                this.listaOrdinata.comparatorProperty().unbind();
            }

            this.listaFiltrata = new FilteredList<>(gestore.getLista(), p -> true);
            this.listaOrdinata = new SortedList<>(listaFiltrata);

            // Qui avviene il legame corretto: la lista ascolta la tabella
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
        return new ArrayList<>(mappaOrdinamento.keySet());
    }


    @Override
    public void ordina(String criterio) {
        if (listaOrdinata != null && mappaOrdinamento.containsKey(criterio)) {
            if (listaOrdinata.comparatorProperty().isBound()) {
                listaOrdinata.comparatorProperty().unbind();
            }
            listaOrdinata.setComparator(mappaOrdinamento.get(criterio));
        }
    }

    private void setRigheTabella(TableView<Utente> t) {
        t.setRowFactory(tv -> new TableRow() { // RAW TYPE
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || !(item instanceof Utente)) {
                    setStyle("");
                    return;
                }

                Utente libro = (Utente) item;
                if (!libro.isAttivo()) {
                    setStyle("-fx-background-color: lightgray; -fx-text-fill: darkgray;");
                } else {
                    setStyle("");
                }
            }
        });
    }
}