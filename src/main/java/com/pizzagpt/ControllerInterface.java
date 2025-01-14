package com.pizzagpt;

import com.pizzagpt.userSession.User;

//Se si vuole passare l'utente loggato, bisogna implementare questa interfaccia nel controller

public interface ControllerInterface {
    void setUser(User user); //Dichiara l'utente loggato nel controller
    void logOut(); //Dichiara che l'utente non è
}
