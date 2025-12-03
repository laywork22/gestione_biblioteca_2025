package it.unisa.diem.softeng.librarymanager.controllers;

import it.unisa.diem.softeng.librarymanager.model.Prestito;
import javafx.scene.control.TableView;

public interface AreaController {
    void onRemove();
    void onAdd();
    void onEdit(TableView<?> tabella);

    void setTableView(TableView<?> tabella);
    void filtraTabella(TableView<?> tabella);
}
