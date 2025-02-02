module com.pizzagpt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;

    exports com.pizzagpt.sortino to javafx.fxml;

    exports com.pizzagpt;     // Esporta il pacchetto che contiene la classe Main
    opens com.pizzagpt.userSession; // Apre il pacchetto che contiene i file FXML

//    exports com.pizzagpt.marchesini.controller; // ^ Stessa cosa ma per il pacchetto com.pizzagpt.marchesini
//    opens com.pizzagpt.marchesini to javafx.graphics, javafx.fxml, com.fasterxml.jackson.databind;
    exports com.pizzagpt.marchesini.json;
    opens com.pizzagpt.marchesini.json to com.fasterxml.jackson.databind, javafx.fxml, javafx.graphics;
    exports com.pizzagpt.marchesini.controller;
    opens com.pizzagpt.marchesini.controller to com.fasterxml.jackson.databind, javafx.fxml, javafx.graphics;
    exports com.pizzagpt.marchesini.loader;
    opens com.pizzagpt.sortino;
}
