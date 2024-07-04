module com.example.pizzagpt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pizzagpt to javafx.fxml;
    exports com.example.pizzagpt;
}