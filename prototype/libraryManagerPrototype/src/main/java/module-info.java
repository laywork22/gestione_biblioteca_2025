module it.nocera.laborgata {
    requires javafx.controls;
    requires javafx.fxml;

    opens it.nocera.laborgata to javafx.fxml;
    exports it.nocera.laborgata;
}
