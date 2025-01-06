package com.pizzagpt.userSession;

import com.pizzagpt.Main;
import com.pizzagpt.PATHS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User {
    private String SPLITTER = ",";
    private String username, password;
    private int id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = generateId();
    }

    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    //Getter
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }

    //Setter
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Altro
    private int generateId() { //Crea ID tenendo conto di quelli già assegnati
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
