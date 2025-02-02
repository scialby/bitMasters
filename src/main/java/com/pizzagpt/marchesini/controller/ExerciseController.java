package com.pizzagpt.marchesini.controller;

import com.pizzagpt.PATHS;
import com.pizzagpt.marchesini.loader.ExerciseLoader;
import com.pizzagpt.marchesini.loader.ResultsLoader;
import com.pizzagpt.marchesini.json.JsonHandler;
import com.pizzagpt.marchesini.json.MarchesiniInfo;
import com.pizzagpt.marchesini.json.MarchesiniUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

// Controller per il controllo della schermata degli Esercizi

public class ExerciseController extends MarchesiniController implements Initializable {

    // VARIABILI
    @FXML
    private Label title, progress, question;
    @FXML
    private HBox choice_container;
    @FXML
    private ImageView image;
    @FXML
    private Button previous_btn, next_btn;

    // ALTRE FUNZIONI

    // Carica esercizio da .json
    public void loadExercise() {
        if(canLoadFromJson()) {
            System.out.println();
            MarchesiniInfo.Exercise exercise = getExercise_info();
            MarchesiniInfo.Category category = getCategory_info();
            int num_category = category.getId();
            int num_exercise = exercise.getId();
            int tot_exercises = category.getExercises().size();

            //Imposta le varie scritte della schermata
            title.setText(getExercise_info().getTitle());
            progress.setText(num_exercise + "/" + tot_exercises);
            question.setText(exercise.getQuestion());
            loadChoices(exercise);

            //Imposta l'immagine dell'esercizio
            String image_path = "file:" + PATHS.MARCHESINI_IMAGES + "/cat" + num_category + "/es" + num_exercise + ".png";
            image.setImage(new Image(image_path));
        }
    }

    // Carica le scelte possibili per l'esercizio
    private void loadChoices(MarchesiniInfo.Exercise exercise) {
        for(MarchesiniInfo.Choice choice : exercise.getChoices()) {
            String info_choice = choice.getId();

            Button choice_btn = (Button) choice_container.lookup("#" + info_choice);
            if(choice_btn == null) {
                throw new NoSuchElementException("Nessuna Choice con ID \"" + choice.getId() + "\" trovata.");
            }
            choice_btn.setText(choice.getText());

            //TODO: Possibili migliorie all'efficienza, però funziona
            MarchesiniUser.Root user_root = JsonHandler.getJsonRoot(PATHS.MARCHESINI_SAVES + "user" + getUser().getId() + ".json", MarchesiniUser.Root.class);
            for(MarchesiniUser.Category cat : user_root.getCategories()) {
                if (cat.getId() == getCategory()) {
                    for (MarchesiniUser.Exercise ex : cat.getExercises()) {
                        if(ex.getId() == exercise.getId() && ex.getChoice().equals(choice.getId())) {
                            updateGUI(choice_container, choice_btn);
                        }
                    }
                }
            }
        }
    }

    // Gestisce le risposte date
    public void choiceHandler(ActionEvent event) {
        Button choice_btn = (Button)event.getSource(); //Determina qual è il tasto cliccato
        String choice_id = choice_btn.getId();

        JsonHandler.saveExercise(getUser().getId(), getCategory(), getExercise(), choice_id);
        updateGUI(choice_container, choice_btn);
    }

    // Carica la barra di navigazione
    // (calcola se ci sono esercizi precedenti o successivi)
    public void loadNavigation() {
        //Se non esiste l'esercizio precedente
        if(!JsonHandler.existsExercise(getCategory(), getExercise()-1)) {
            previous_btn.setDisable(true);
        }
        //Se non esiste l'esercizio successivo
        if(!JsonHandler.existsExercise(getCategory(), getExercise()+1)) {
            next_btn.setText("Consegna");
            next_btn.getStyleClass().add("btn-blue");
            next_btn.setOnAction(e -> { //Crea dinamicamente il tasto che porta alla scena di completamento
                try {
                    new ResultsLoader(getUser(), getCategory());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    // Tasti per il precedente e prossimo esercizio
    public void controlNavigation(ActionEvent event) throws IOException {
        Button clicked_btn = (Button)event.getSource(); //Determina qual è il tasto cliccato
        String id = clicked_btn.getId(); //Estrapola l'ID del button

        if(id.equals("previous_btn")) {
            new ExerciseLoader(getUser(), getCategory(), (getExercise()-1));
        } else {
            new ExerciseLoader(getUser(), getCategory(), (getExercise()+1));
        }
    }

    // Gestisce la GUI dei tasti
    private void updateGUI(HBox choice_container, Button choice_btn) {
        for(Node node : choice_container.getChildren()) {
            node.getStyleClass().remove("btn-blue");
            if(node == choice_btn) {
                node.getStyleClass().add("btn-blue");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
