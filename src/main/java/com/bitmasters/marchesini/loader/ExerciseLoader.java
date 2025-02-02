package com.bitmasters.marchesini.loader;

import com.bitmasters.PATHS;
import com.bitmasters.marchesini.controller.ExerciseController;
import com.bitmasters.userSession.User;

import java.io.IOException;

// Loader per la schermata di Esercizi

public class ExerciseLoader extends MarchesiniLoader<ExerciseController> {

    public ExerciseLoader(User user, int category, int exercise) throws IOException {
        super(user, PATHS.MARCHESINI_EXERCISE, category, exercise);
        controller.loadExercise();
        controller.loadNavigation();
        load();
        setTitle("Categoria " + category + " - Esercizio " + exercise);
        show();
    }
}