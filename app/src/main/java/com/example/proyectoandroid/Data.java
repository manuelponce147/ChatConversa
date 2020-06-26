package com.example.proyectoandroid;

import java.util.Objects;

public class Data {
    private int id;
    private String name;
    private String lastname;
    private String username;
    private String run;
    private String email;
    private String image;
    private String thumbnail;


    public Data(int id, String name, String lastname, String username, String run, String email, String image, String thumbnail) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.run = run;
        this.email = email;
        this.image = image;
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return id == data.id &&
                Objects.equals(name, data.name) &&
                Objects.equals(lastname, data.lastname) &&
                Objects.equals(username, data.username) &&
                Objects.equals(run, data.run) &&
                Objects.equals(email, data.email) &&
                Objects.equals(image, data.image) &&
                Objects.equals(thumbnail, data.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, username, run, email, image, thumbnail);
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", run='" + run + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
