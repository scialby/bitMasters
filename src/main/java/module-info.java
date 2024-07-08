module com.pizzagpt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pizzagpt to javafx.fxml;
    exports com.pizzagpt;
}