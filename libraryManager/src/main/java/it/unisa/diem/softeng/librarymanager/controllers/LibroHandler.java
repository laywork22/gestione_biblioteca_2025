package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.comparators.AutoreLibroComparator;
import it.unisa.diem.softeng.librarymanager.controllers.forms.FormLibroController;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.model.Libro;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**@brief Gestore della schermata UI Area Libri
 *
 * Implementa la logica di visualizzazione della tabella
 * e di caricamento FXML del file del form al
 * gestore del form
 *
 */
public class LibroHandler implements AreaHandler {
    private final GestoreLibro gestore;
    private final Map<String, Comparator<Libro>> mappaComparatori;
    private FilteredList<Utente> listaFiltrata;
    private SortedList<Utente> listaOrdinata;



    public LibroHandler(GestoreLibro gestore) {
        this.gestore = gestore;

        mappaComparatori = new HashMap<>();

        mappaComparatori.put("Titolo (A-Z)", new AutoreLibroComparator());
        mappaComparatori.put("Autore (A-Z)", new AutoreLibroComparator());
    }

    @Override
    public void onRemove() {

    }

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
            System.out.println("Errore nel caricamento di LibroView.fxml");
        }
    }

    @Override
    public void onEdit(TableView<?> tabella) {
        //prendi il libro dalla tabella

        //usa il controller per settare il form con setFormOnEdit

        //apri il form con i campi riempiti
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
    public void ordina(String criterio) {

    }
}

