package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.controllers.forms.FormPrestitoController;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**@brief Gestore della schermata UI Area Prestiti
 *
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del file del form al
 * gestore del form
 *
 */
public class PrestitoHandler implements AreaHandler {
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
    public void onRemove() {

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
    public void onEdit(TableView<?> tabella) {

    }

    @Override
    public void setTableView(TableView<?> table) {

    }

    @Override
    public void filtraTabella(String filtro) {

    }

    @Override
    public List<String> getCriteriOrdinamento() {
        return List.of();
    }

    @Override
    public void ordina(String testo) {

    }



}
