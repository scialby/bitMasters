module com.pizzagpt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    exports com.pizzagpt.sortino to javafx.fxml;
    opens com.pizzagpt.sortino to javafx.fxml;

    exports com.pizzagpt;     // Esporta il pacchetto che contiene la classe Main
    opens com.pizzagpt.userSession; // Apre il pacchetto che contiene i file FXML

    exports com.pizzagpt.marchesini; // ^ Stessa cosa ma per il pacchetto com.pizzagpt.marchesini
    opens com.pizzagpt.marchesini to javafx.graphics;
}
