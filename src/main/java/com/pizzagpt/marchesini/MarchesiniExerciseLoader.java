package com.pizzagpt.marchesini;

import com.pizzagpt.Main;
import com.pizzagpt.userSession.User;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import com.pizzagpt.PATHS;
import javafx.scene.shape.Path;

public class MarchesiniExerciseLoader extends MarchesiniLoader {
    private String SPLITTER = ",";
    //Variabili
    private int category, exercise;
    private
    MarchesiniExerciseLoader(User user, int category, int exercise) throws IOException {
        super("cat" + category + "/Es" + exercise + ".fxml", user);
        this.category = category;
        this.exercise = exercise;
        show();
        setTitle();
        loadSave();
        loadNavigation();
        start();
    }

    //Getter
    public int getCategory() {
        return category;
    }
    public int getExercise() {
        return exercise;
    }

    //Setter
    public void setCategory(int category) {
        this.category = category;
    }
    public void setExercise(int exercise) {
        this.exercise = exercise;
    }

    //Imposta il titolo della finestra
    public void setTitle() {
        Main.stg.setTitle("Categoria " + category + ", Esercizio " + exercise);
    }

    //Carica il salvataggio
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

    //Carica i tasti di navigazione
    public void loadNavigation() {
        //Variabili
        String path = PATHS.RESOURCES + PATHS.MARCHESINI_VIEWS + category + "/Es";
        File previousFile = new File(path + (exercise-1) + ".txt");
        File nextFile = new File(path + (exercise+1) + ".txt");
        Button previousBtn = (Button)Main.stg.getScene().lookup("#previous");
        Button nextBtn = (Button)Main.stg.getScene().lookup("#next");

        //Controllo precedente
        if(!previousFile.exists()) { //Se non trova disabilita
            previousBtn.setDisable(true);
        }
        //Controllo successivo
        if(!nextFile.exists()) { //Se non trova manda al completamento
            nextBtn.getStyleClass().remove("navigation");
            nextBtn.getStyleClass().add("option");
            nextBtn.setText("Consegna");
            nextBtn.setOnAction(e -> { //getController().complete;
                //TODO: Loader e meccanismo di completamento degli esercizi
                //MarchesiniCompletedLoader completedLoader = new MarchesiniCompletedLoader();
                //completedLoader.start();
            });
        }
    }
}
