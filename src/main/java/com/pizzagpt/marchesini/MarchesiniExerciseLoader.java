package com.pizzagpt.marchesini;

import com.pizzagpt.Globals;
import com.pizzagpt.Loader;
import com.pizzagpt.Main;
import com.pizzagpt.userSession.User;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import com.pizzagpt.PATHS;
import javafx.scene.shape.Path;

// Classe apposita per cambiare la scena all'esercizio specificato nella cartella Marchesini
public class MarchesiniExerciseLoader extends Loader {

    // Variabili
    private int category, exercise;
    private Controller controller;

    // Costruttore
    public MarchesiniExerciseLoader(User user, int category, int exercise) throws IOException {
        super(user, Globals.marchesini_views + "cat" + category + "/Es" + exercise + ".fxml");
        this.category = category;
        this.exercise = exercise;
        show();
        setTitle();
        controller = (Controller)super.getController();
        controller.setUser(user);
        controller.setInfo(category, exercise);
        setCss(Globals.marchesini_css);
        loadSave();
        loadNavigation();
        start();
    }

    // Getter
    public int getCategory() {
        return category;
    }
    public int getExercise() {
        return exercise;
    }
    @Override
    public Controller getController() {
        return controller;
    }

    // Setter
    public void setCategory(int category) {
        this.category = category;
    }
    public void setExercise(int exercise) {
        this.exercise = exercise;
    }

    // Imposta il titolo della finestra
    public void setTitle() {
        Main.stg.setTitle("Categoria " + category + ", Esercizio " + exercise);
    }

    //Carica il salvataggio
    //TODO: Cambiare e provare ad utilizzare lo UserManager per comparare anziché ogni volta riprendere dal file
    public void loadSave() {
        try {
            Scanner read = new Scanner(new File(PATHS.MARCHESINI_SAVES + "user" + getUser().getId() + "/cat" + category + ".txt"));
            while(read.hasNextLine()) {
                String line = read.nextLine();
                String[] tokens = line.split(SPLITTER);
                if(exercise == Integer.parseInt(tokens[0])) {
                    Button btn = (Button)Main.stg.getScene().lookup("#" + tokens[1]);
                    btn.fire();
                    break;
                }
            }
        } catch(FileNotFoundException ex) {
            System.out.println("[Eccezione: " + ex + "] Nessun salvataggio trovato per questo esercizio.");
        }
    }

    // Carica i tasti di navigazione
    //TODO: Riscrivere e controllare
    public void loadNavigation() {
        //Variabili
        String path = PATHS.RESOURCES + PATHS.MARCHESINI_VIEWS + category + "/Es";
        File previousFile = new File(path + (exercise-1) + ".txt");
        File nextFile = new File(path + (exercise+1) + ".txt");
        Button previousBtn = (Button)Main.stg.getScene().lookup("#previous");
        Button nextBtn = (Button)Main.stg.getScene().lookup("#next");

        System.out.println(previousFile.getPath());
        //Controllo precedente
        if(!previousFile.exists()) { //Se non trova disabilita
            System.out.println("Non esiste il precedente");
            previousBtn.setDisable(true);
        } else {
            System.out.println("Esiste il precedente");
        }
        System.out.println(nextFile.getPath());
        //Controllo successivo
        if(!nextFile.exists()) { //Se non trova manda al completamento
            System.out.println("Non esiste il successivo");
            nextBtn.getStyleClass().remove("navigation");
            nextBtn.getStyleClass().add("option");
            nextBtn.setText("Consegna");
            nextBtn.setOnAction(e -> { //getController().complete;
                //TODO: Loader e meccanismo di completamento degli esercizi
                //MarchesiniCompletedLoader completedLoader = new MarchesiniCompletedLoader();
                //completedLoader.start();
            });
        } else {
            System.out.println("Esiste il successivo");
        }
    }
}
