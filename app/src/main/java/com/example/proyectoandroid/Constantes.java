package com.example.proyectoandroid;

import android.app.Application;

import java.util.Objects;

public class Constantes extends Application {
    private String id;
    private String name;
    private String lastname;
    private String username;
    private String run;
    private String email;
    private String thumbnail;
    private String image;
    private String token;


    @Override
    public String toString() {
        return "Constantes{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", run='" + run + '\'' +
                ", email='" + email + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", image='" + image + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constantes that = (Constantes) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(username, that.username) &&
                Objects.equals(run, that.run) &&
                Objects.equals(email, that.email) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(image, that.image) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, username, run, email, thumbnail, image, token);
    }

    public Constantes() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Constantes(String id, String name, String lastname, String username, String run, String email, String thumbnail, String image, String token) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.run = run;
        this.email = email;
        this.thumbnail = thumbnail;
        this.image = image;
        this.token = token;
    }
}
