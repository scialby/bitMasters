package com.pizzagpt.marchesini;

import com.pizzagpt.Main;
import com.pizzagpt.PATHS;
import com.pizzagpt.userSession.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MarchesiniLoader {

    //Variabili
    private String path;
    private User user;
    //Oggetti
    private Controller controller;
    private AnchorPane loadedContent;
    private StackPane root, overlay;
    private ImageView imageView;
    private Scene scene;

    public MarchesiniLoader(User user, String path) throws IOException {
        this.path = path;
        this.user = user;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(PATHS.MARCHESINI_VIEWS + path)); //Imposta il percorso
        loadedContent = fxmlLoader.load(); //Carica il contenuto del percorso
        controller = fxmlLoader.getController();
        controller.setUser(user); //Passa l'utente della sessione
        root = new StackPane(); //Crea il parente per contenuto e caricamento
        root.getChildren().add(loadedContent); //Gli aggiunge il contenuto
        overlay = new StackPane(); //Crea caricamento
        overlay.getStyleClass().add("loading");
        imageView = new ImageView(); //Crea loading-gif
        imageView.setImage(new Image("file:" + PATHS.MARCHESINI_IMAGES + "loading-gif.gif")); //Carica l'immagine e poi la imposta
        imageView.setFitWidth(70);
        imageView.setPreserveRatio(true);
        overlay.getChildren().add(imageView); //Aggiunge gif al caricamento
        root.getChildren().add(overlay); //Aggiunge il caricamento
        scene = new Scene(root); //Crea la scena
        scene.getStylesheets().clear(); //Toglie eventuali altri file CSS
        scene.getStylesheets().add(getClass().getResource(PATHS.MARCHESINI_CSS).toExternalForm()); //Applica il CSS giusto
    }

    //Getter
    public String getPath() {
        return path;
    }
    public User getUser() {
        return user;
    }

    //Funzioni complementari
    public void show() { //Mostrare in definitiva la scena
        Main.stg.setScene(scene);
        Main.stg.show();
    }
    public void start() { //Funzione per rimuovere la schermata di caricamento
        root.getChildren().remove(overlay);
    }
    //public abstract void setTitle();
}
