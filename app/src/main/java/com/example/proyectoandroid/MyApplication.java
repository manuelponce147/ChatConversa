package com.example.proyectoandroid;

import android.app.Application;

import java.util.Objects;

public class MyApplication extends Application {
    private String username;
    private String id;
    private String token;
    private String thumbnail;
    private String image;

    public MyApplication() {
    }

    public MyApplication(String username, String id, String token, String thumbnail, String image) {
        this.username = username;
        this.id = id;
        this.token = token;
        this.thumbnail = thumbnail;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyApplication that = (MyApplication) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(id, that.id) &&
                Objects.equals(token, that.token) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, id, token, thumbnail, image);
    }

    @Override
    public String toString() {
        return "Aplicacion{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
