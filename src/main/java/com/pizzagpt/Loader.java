package com.pizzagpt;

import com.pizzagpt.userSession.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Loader {

    //Variabili
    private String path;
    private User user;
    //Oggetti
    private ControllerInterface controller;
    private Pane loadedContent;
    private StackPane root, overlay;
    private ImageView imageView;
    private Scene scene;

    public Loader(User user, String path) throws IOException {
        this.user = user;
        this.path = path;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path)); //Imposta il percorso
        loadedContent = fxmlLoader.load(); //Carica il contenuto del percorso
        controller = fxmlLoader.getController(); //Imposta il controller, basta che abbia la stessa interfaccia
        controller.setUser(user); //Passa l'utente al controller
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
        scene.getStylesheets().add(getClass().getResource(PATHS.CSS).toExternalForm()); //Applica il CSS giusto
    }

    // Getter
    public String getPath() { return path; }
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
    public void load() { //Carica la scena
        Main.stg.setScene(scene);
        Main.stg.show();
    }
    public void show() { //Rimuove la schermata di caricamento
        root.getChildren().remove(overlay);
    } //Mostra in definitiva la scena (toglie il caricamento)
    public void setTitle(String title) { //Imposta il titolo
        Main.stg.setTitle(title);
    }
}
