package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.controllers.forms.FormPrestitoController;
import it.unisa.diem.softeng.librarymanager.managers.GestoreLibro;
import it.unisa.diem.softeng.librarymanager.managers.GestorePrestito;
import it.unisa.diem.softeng.librarymanager.managers.GestoreUtente;
import it.unisa.diem.softeng.librarymanager.model.Libro;
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
public class PrestitoHandler implements AreaHandler<Prestito> {
    /**
     * @brief Gestore dell'area prestiti.
     */
    private final GestorePrestito gestorePrestito;

    /**
     * @brief Gestore ausiliario dell'area libri.
     */
    private final GestoreLibro gestoreLibro;

    /**
     * @brief Gestore ausiliario dell'area utenti.
     */
    private final GestoreUtente gestoreUtente;

    /**
     * @brief Lista osservabile filtrata secondo un determinato criterio.
     */
    private FilteredList<Prestito> listaFiltrata;

    /**
     * @brief Lista osservabile ordinata secondo una relazione d'ordine imposta da un
     * Comparator.
     */
    private SortedList<Prestito> listaOrdinata;

    /**
     * @brief Costruttore della classe LibroHandler.
     *
     * @param gestore Il GestoreLibro della sessione attuale
     */


    /**
     * @brief Costruttore della classe PrestitoHandler.
     *
     * @param gestorePrestito Il gestore dell'area prestiti.
     * @param gestoreLibro Gestore ausiliario
     * @param gestoreUtente Gestore ausiliario
     */
    public PrestitoHandler(GestorePrestito gestorePrestito, GestoreLibro gestoreLibro, GestoreUtente gestoreUtente) {
        this.gestorePrestito = gestorePrestito;
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
                fu.init(gestorePrestito, gestoreLibro, gestoreUtente);
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
    }

    @Override
    public void setTableView(TableView<Prestito> table) {

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
