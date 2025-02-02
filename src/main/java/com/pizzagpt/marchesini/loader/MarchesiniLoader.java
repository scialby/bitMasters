package com.pizzagpt.marchesini.loader;

import com.pizzagpt.marchesini.controller.MarchesiniController;
import com.pizzagpt.userSession.User;

import java.io.IOException;

// Loader generico, base per gli altri loader di Marchesini

public abstract class MarchesiniLoader<T extends MarchesiniController> extends Loader {

    protected T controller;

    public MarchesiniLoader(User user, String path, int category, int exercise) throws IOException {
        super(user, path);
        this.controller = (T) super.getController();
        this.controller.setCategory(category);
        this.controller.setExercise(exercise);
    }
}