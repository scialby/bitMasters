package com.bitmasters.marchesini.loader;

import com.bitmasters.marchesini.controller.MarchesiniController;
import com.bitmasters.userSession.User;

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