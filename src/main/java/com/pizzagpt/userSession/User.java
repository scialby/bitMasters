package com.pizzagpt.userSession;

import com.pizzagpt.Main;
import com.pizzagpt.PATHS;
import com.pizzagpt.sortino.UserProgress;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User {
    private String SPLITTER = ",";
    private String username, password;
    private int id;
    private UserProgress progress;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = generateId();
        this.progress = new UserProgress(username);

    }

    public void setProgress(UserProgress progress) {
        this.progress = progress;
    }

    public UserProgress getProgress() {
        return progress;
    }

    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    // Getter
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }

    // Setter
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Crea ID tenendo conto di quelli già assegnati
    private int generateId() {
        int id = 0;
        try {
            Scanner read = new Scanner(new File(PATHS.ACCOUNTS));
            while(read.hasNextLine()) {
                String line = read.nextLine();
                if(!line.isBlank()) {
                    String[] tokens = line.split(SPLITTER);
                    id = Integer.parseInt(tokens[2]);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File non trovato. [Eccezione " + ex + "]");
        }
        return id+1;
    }
}
