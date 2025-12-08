package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.controllers.forms.FormPrestitoController;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
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
import java.util.List;

/**@brief Gestore della schermata UI Area Prestiti
 *
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del file del form al
 * gestore del form
 *
 */
public class PrestitoHandler implements AreaHandler<Prestito> {
    private final GestorePrestito gestore;
    private final GestoreLibro gestoreLibro;
    private final GestoreUtente gestoreUtente;

    private FilteredList<Prestito> listaFiltrata;
    private SortedList<Prestito> listaOrdinata;

    public PrestitoHandler(GestorePrestito gestore, GestoreLibro gestoreLibro, GestoreUtente gestoreUtente) {
        this.gestore = gestore;
        this.gestoreLibro = gestoreLibro;
        this.gestoreUtente = gestoreUtente;
    }

    @Override
    public void onRemove(Prestito p) {

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

        if(p==null)return;

    }

    @Override
    public void setTableView(TableView<Prestito> table) {
        TableView<Prestito> t=table;
        t.getColumns().clear();
        TableColumn<Prestito,String> utenteClm=new TableColumn<>("Dati utente");
        TableColumn<Prestito,String> libroClm=new TableColumn<>("Dati Libro");

        TableColumn<Prestito,String> nomeClm=new TableColumn<>("Nome");
        nomeClm.setCellValueFactory(r->new SimpleStringProperty(r.getValue().getUtente().getNome()));
        TableColumn<Prestito,String> cognomeClm=new TableColumn<>("cognome");
        cognomeClm.setCellValueFactory(r->new SimpleStringProperty(r.getValue().getUtente().getCognome()));

        TableColumn<Prestito,String> titoloClm =new TableColumn<>("Titolo");
        titoloClm.setCellValueFactory(r->new SimpleStringProperty(r.getValue().getLibro().getTitolo()));
        TableColumn<Prestito,String> autoreClm=new TableColumn<>("Autore");
        autoreClm.setCellValueFactory(r->new SimpleStringProperty(r.getValue().getLibro().getAutore()));
        utenteClm.getColumns().addAll(nomeClm,cognomeClm);
        libroClm.getColumns().addAll(titoloClm,autoreClm);
        t.getColumns().addAll(utenteClm,libroClm);

        TableColumn<Prestito, String> dataClm=new TableColumn<>("Data Prestito");

        TableColumn<Prestito,String> dataInizioClm=new TableColumn<>("Data Inizio");
        dataInizioClm.setCellValueFactory(r->new SimpleStringProperty(r.getValue().getDataInizio().toString()));

        TableColumn<Prestito,String> dataFineClm=new TableColumn<>("Data Fine");
        dataFineClm.setCellValueFactory(r->new SimpleStringProperty(r.getValue().getDataFine().toString()));

        dataClm.getColumns().addAll(dataInizioClm,dataFineClm);
        t.getColumns().addAll(dataClm);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(gestore.getLista());


    }

    @Override
    public void filtraTabella(String filtro) {

    }

    @Override
    public List<String> getCriteriOrdinamento() {
        return List.of();
    }

    @Override
    public void ordina(String criterio) {

    }

}
