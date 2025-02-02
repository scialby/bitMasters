package com.bitmasters.marchesini.controller;

import com.bitmasters.ControllerInterface;
import com.bitmasters.marchesini.loader.Loader;
import com.bitmasters.PATHS;
import com.bitmasters.Utils;
import com.bitmasters.marchesini.json.JsonHandler;
import com.bitmasters.marchesini.json.MarchesiniInfo;
import com.bitmasters.marchesini.loader.SelectionLoader;
import com.bitmasters.userSession.User;

import java.io.IOException;

public abstract class MarchesiniController implements ControllerInterface {

    // VARIABILI
    private User loggedUser;
    private int category, exercise;
    private MarchesiniInfo.Category category_info;
    private MarchesiniInfo.Exercise exercise_info;

    // Funzioni implementate dall'interfaccia
    @Override
    public void setUser(User loggedUser) { this.loggedUser = loggedUser; }
    @Override
    public User getUser() { return loggedUser; }
    @Override
    public void logOut() {
        try {
            Utils.setSceneCSS(PATHS.LOGIN_SCENE);

        } catch (IOException ex) {
            System.out.println("[Eccezione: " + ex + "] E' stato riscontrato un problema.");
        }
    }

    // Getter
    public int getCategory() { return category; }
    public int getExercise() { return exercise; }
    public MarchesiniInfo.Exercise getExercise_info() { return exercise_info; }
    public MarchesiniInfo.Category getCategory_info() { return category_info; }

    // Setter
    public void setCategory(int category) { this.category = category; }
    public void setExercise(int exercise) { this.exercise = exercise; }




    // ALTRE FUNZIONI

    //Controlla se può caricare da file .json
    public boolean canLoadFromJson() {
        MarchesiniInfo.Root root = JsonHandler.getJsonRoot(MarchesiniInfo.Root.class);
        // Cicla ogni categoria presente fino a quando non corrisponde con quella cercata
        for(MarchesiniInfo.Category category : root.getCategories()) {
            if(category.getId() == getCategory()) {
                // Cicla ogni esercizio presente fino a quando non corrisponde con quello cercato
                for(MarchesiniInfo.Exercise exercise : category.getExercises()) {
                    if(exercise.getId() == getExercise()) {
                        category_info = category;
                        exercise_info = exercise;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Ritorna alla selezione degli esercizi di Marchesini
    public void toSelection() {
        try {
            new SelectionLoader(loggedUser);
        } catch (IOException e) {
            throw new RuntimeException("[" + e.getMessage() + "] Impossibile tornare alla selezione di Marchesini.");
        }
    }

    //Ritorna al menù principale
    public void toMainMenu() {
        Loader loader = null;
        try {
            loader = new Loader(loggedUser, PATHS.MAIN_MENU);
            loader.setTitle("Menù principale");
            loader.load();
            loader.show();
        } catch (IOException e) {
            throw new RuntimeException("[" + e.getMessage() + "] Impossibile tornare al menù principale.");
        }
    }
}
