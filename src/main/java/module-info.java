module com.bitmasters {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;

    exports com.bitmasters.sortino to javafx.fxml;

    exports com.bitmasters;     // Esporta il pacchetto che contiene la classe Main
    opens com.bitmasters.userSession; // Apre il pacchetto che contiene i file FXML

    exports com.bitmasters.marchesini.json;
    opens com.bitmasters.marchesini.json to com.fasterxml.jackson.databind, javafx.fxml, javafx.graphics;
    exports com.bitmasters.marchesini.controller;
    opens com.bitmasters.marchesini.controller to com.fasterxml.jackson.databind, javafx.fxml, javafx.graphics;
    exports com.bitmasters.marchesini.loader;
    opens com.bitmasters.sortino;
}