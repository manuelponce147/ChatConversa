package com.example.proyectoandroid;

import android.graphics.Bitmap;

import java.io.File;
import java.util.Objects;

public class Imagen {
    private int user_id;
    private String username;
    private File user_image;

    public Imagen() {
    }

    public Imagen(int user_id, String username, File user_image) {
        this.user_id = user_id;
        this.username = username;
        this.user_image = user_image;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public File getUser_image() {
        return user_image;
    }

    public void setUser_image(File user_image) {
        this.user_image = user_image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Imagen imagen = (Imagen) o;
        return user_id == imagen.user_id &&
                Objects.equals(username, imagen.username) &&
                Objects.equals(user_image, imagen.user_image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, user_image);
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", user_image=" + user_image +
                '}';
    }
}
