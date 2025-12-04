package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.comparators.CognomeUtenteComparator;
import it.unisa.diem.softeng.librarymanager.controllers.forms.FormUtenteController;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Utente;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class UtenteController implements AreaController {
    private GestoreUtente gestore;
    private final Map<String, Comparator<Utente>> mappaOrdinamento;
    private FilteredList<Utente> listaFiltrata;
    private SortedList<Utente> listaOrdinata;

    public UtenteController(GestoreUtente gestore) {
        this.gestore = gestore;

        mappaOrdinamento = new HashMap<>();
        mappaOrdinamento.put("Cognome (A-Z)", new CognomeUtenteComparator());
    }

    @Override
    public void onAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unisa/diem/softeng/libraryManager/UtenteView.fxml"));

            fxmlLoader.setControllerFactory(param -> {
                if (param == FormUtenteController.class) {
                    return new FormUtenteController(this.gestore);
                }
                try {
                    return param.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent root = fxmlLoader.load();
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
    }

    @Override
    public void onRemove() {
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
        return new ArrayList<>(mappaOrdinamento.keySet());
    }

    @Override
    public void ordina(String testo) {
    }
}