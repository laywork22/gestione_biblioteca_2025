package it.unisa.diem.softeng.librarymanager.controllers;

import javafx.scene.control.TableView;

public interface AreaController {
    void onRemove();
    void onAdd();
    void onEdit();
    void setTableView(TableView<?> tabella);
    void filtraTabella(TableView<?> tabella);
}
