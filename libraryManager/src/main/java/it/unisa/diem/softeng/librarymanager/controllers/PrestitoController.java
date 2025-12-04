package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.controllers.forms.FormPrestitoController;
import it.unisa.diem.softeng.librarymanager.controllers.forms.FormUtenteController;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Prestito;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PrestitoController implements AreaController {
    private GestorePrestito gestore;
    private FilteredList<Prestito> listaFiltrata;
    private SortedList<Prestito> listaOrdinata;
    public PrestitoController(GestorePrestito gestore) {
        this.gestore = gestore;
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/libraryManager/PrestitoView.fxml"));

            Parent root = fxmlLoader.load();

            FormPrestitoController fu = fxmlLoader.getController();

            if(fu != null) {
                fu.setGestore(gestore);
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
