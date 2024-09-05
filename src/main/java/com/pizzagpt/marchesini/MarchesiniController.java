package com.pizzagpt.marchesini;

import com.pizzagpt.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class MarchesiniController implements Initializable {

    @FXML
    public void wrongChoice(ActionEvent event) {
        System.out.println("Scelta sbagliata");
        Button btn = (Button)event.getSource();
        System.out.println(btn);
        btn.setText("ciao");
        Timeline flash = new Timeline( //Crea un oggetto "Timeline" per far flashare il colore ed indicare che è sbagliato
            new KeyFrame(Duration.seconds(0.5), e -> {
                btn.setText("-fx-background-color: red");
            }),
            new KeyFrame(Duration.seconds(1.0), e -> {
                btn.setText("-fx-background-color: rgb(230, 230, 230)");
            })
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.stg.setTitle("Esercizio 1");
        //Main.stg.getScene().getStylesheets().clear(); //Toglie eventuali altri file CSS
        Main.stg.getScene().getStylesheets().add(getClass().getResource("/com.pizzagpt/scenes/marchesini/style.css").toExternalForm()); //Imposta lo stylesheet apposito
    }
}
