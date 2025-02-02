package com.pizzagpt;

import com.pizzagpt.userSession.User;

//Interfaccia che permette il passaggio dell'utente loggato

public interface ControllerInterface {
    void setUser(User user); //Dichiara l'utente loggato nel controller
    User getUser(); //Restituisce l'utente
    void logOut(); //Riporta l'utente alla schermata di accesso (disconnessione)
}
