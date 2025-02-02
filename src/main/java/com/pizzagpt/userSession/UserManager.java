package com.pizzagpt.userSession;

import com.pizzagpt.PATHS;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {
    private String SPLITTER = ",";
    private ArrayList<User> users;

    // Costruttore
    public UserManager() {
        users = new ArrayList<User>();
    }

    // Getter
    public ArrayList<User> getUsers() {
        return users;
    }

    // Setter
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    // Aggiunge un nuovo utente se non esiste già
    public boolean addUser(User user) {
        if(!exists(user)) {
            users.add(user);
            return true;
        }
        return false;
    }

    // Controlla se l'utente esiste già
    public boolean exists(User user) {
        String username = user.getUsername();
        for(User i : users) {
            if(i.getUsername().equals(username)) return true;
        }
        return false;
    }

    // Salva la lista di utenti in un file
    public boolean toFile() {
        try {
            PrintWriter pw = new PrintWriter(PATHS.ACCOUNTS);
            for(User user : users) {
                pw.println(user.getUsername() + SPLITTER + user.getPassword() + SPLITTER + user.getId());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("[" + e.getMessage() + "] Nessun account ancora registrato.");
            return false;
        }
        return true;
    }

    // Legge la lista di utenti da un file
    public boolean fromFile() {
        try {
            Scanner read = new Scanner(new File(PATHS.ACCOUNTS));
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] tokens = line.split(SPLITTER);
                users.add(new User(tokens[0], tokens[1], Integer.parseInt(tokens[2])));
            }
            if (users.isEmpty()) { return false; }
            read.close();
        } catch (FileNotFoundException ex) {
            return false;
        }
        return true;
    }
}
