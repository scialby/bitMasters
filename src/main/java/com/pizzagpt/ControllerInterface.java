package com.pizzagpt;

import com.pizzagpt.userSession.User;

public interface ControllerInterface {
    void setUser(User user);
    void logOut();
}
