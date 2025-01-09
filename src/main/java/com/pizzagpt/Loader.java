package com.pizzagpt;

import com.pizzagpt.marchesini.Controller;
import com.pizzagpt.userSession.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public abstract class Loader {

    //Variabili
    private String path;
    private User user;
    //Oggetti
    private Object controller;
    private AnchorPane loadedContent;
    private StackPane root, overlay;
    private ImageView imageView;
    private Scene scene;

    public Loader(User user, String path) throws IOException {
        this.user = user;
        this.path = path;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path)); //Imposta il percorso
        loadedContent = fxmlLoader.load(); //Carica il contenuto del percorso
        controller = fxmlLoader.getController();
        root = new StackPane(); //Crea il parente per contenuto e caricamento
        root.getChildren().add(loadedContent); //Gli aggiunge il contenuto
        overlay = new StackPane(); //Crea caricamento
        overlay.getStyleClass().add("loading");
        imageView = new ImageView(); //Crea loading-gif
        imageView.setImage(new Image("file:" + Globals.marchesini_images + "loading-gif.gif")); //Carica l'immagine e poi la imposta
        imageView.setFitWidth(70);
        imageView.setPreserveRatio(true);
        overlay.getChildren().add(imageView); //Aggiunge gif al caricamento
        root.getChildren().add(overlay); //Aggiunge il caricamento
        scene = new Scene(root); //Crea la scena
        scene.getStylesheets().clear(); //Toglie eventuali altri file CSS
        scene.getStylesheets().add(getClass().getResource(Globals.css).toExternalForm()); //Applica il CSS giusto
    }

    // Getter
    public String getPath() {
        return path;
    }
    public User getUser() {
        return user;
    }
    public Object getController() {
        return controller;
    }

    // Setter
    public void setCss(String css) {
        scene.getStylesheets().clear(); //Toglie eventuali altri file CSS
        scene.getStylesheets().add(getClass().getResource(css).toExternalForm()); //Applica il CSS giusto
    }

    // Funzioni
    public void show() { //Mostra la scena
        Main.stg.setScene(scene);
        Main.stg.show();
    }
    public void start() { //Rimuove la schermata di caricamento
        root.getChildren().remove(overlay);
    }
    public void setTitle(String title) { //Imposta il titolo
        Main.stg.setTitle(title);
    }
}
