package com.example.proyectoandroid;

import java.util.Objects;

public class Constantes {
     private String token;
     private int id;
     private String username;


    public Constantes(){

    }

    public Constantes(String token, int id, String username) {
        this.token = token;
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
