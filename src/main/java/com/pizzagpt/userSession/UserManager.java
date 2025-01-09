package com.pizzagpt.userSession;

import com.pizzagpt.Globals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {

    private ArrayList<User> users;

    public UserManager() {
        users = new ArrayList<User>();
    }

    //Getter
    public ArrayList<User> getUsers() {
        return users;
    }

    //Setter
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    //Altro
    public boolean addUser(User user) {
        if(!exists(user)) {
            users.add(user);
            new File(Globals.marchesini_saves + "user" + user.getId()).mkdir();
            return true;
        }
        return false;
    }
    public boolean exists(User user) { //Controlla se l'username esiste già
        String username = user.getUsername();
        for(User i : users) {
            if(i.getUsername().equals(username)) return true;
        }
        return false;
    }

    public boolean toFile() { //ArrayList to File
        try {
            PrintWriter pw = new PrintWriter(Globals.accounts);
            for(User user : users) {
                pw.println(user.getUsername() + Globals.splitter + user.getPassword() + Globals.splitter + user.getId());
            }
            pw.close();
        } catch(FileNotFoundException ex) {
            System.out.println("File non trovato. [Eccezione " + ex + "]");
            return false;
        }
        return true;
    }

    public boolean fromFile() { //File to ArrayList
        try {
            Scanner read = new Scanner(new File(Globals.accounts));
            while(read.hasNextLine()) {
                String line = read.nextLine();
                String[] tokens = line.split(Globals.splitter);
                users.add(new User(tokens[0], tokens[1], Integer.parseInt(tokens[2])));
            }
            read.close();
        } catch(FileNotFoundException ex) {
            System.out.println("File non trovato. [Eccezione " + ex + "]");
            return false;
        }
        return true;
    }
}
