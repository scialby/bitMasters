module com.pizzagpt {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.pizzagpt;     // Esporta il pacchetto che contiene la classe Main
    opens com.pizzagpt.userSession; // Apre il pacchetto che contiene i file FXML

}