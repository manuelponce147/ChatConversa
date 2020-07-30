package com.example.proyectoandroid;

import java.util.Objects;

public class Data {
    private int id;
    private String name;
    private String date;
    private String message;

    private String lastname;
    private String username;
    private String run;
    private String email;
    private String image;
    private String thumbnail;
    private User user;

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", run='" + run + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return id == data.id &&
                Objects.equals(name, data.name) &&
                Objects.equals(date, data.date) &&
                Objects.equals(message, data.message) &&
                Objects.equals(lastname, data.lastname) &&
                Objects.equals(username, data.username) &&
                Objects.equals(run, data.run) &&
                Objects.equals(email, data.email) &&
                Objects.equals(image, data.image) &&
                Objects.equals(thumbnail, data.thumbnail) &&
                Objects.equals(user, data.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, message, lastname, username, run, email, image, thumbnail, user);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Data() {
    }

    public Data(int id, String name, String date, String message, String lastname, String username, String run, String email, String image, String thumbnail, User user) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.message = message;
        this.lastname = lastname;
        this.username = username;
        this.run = run;
        this.email = email;
        this.image = image;
        this.thumbnail = thumbnail;
        this.user = user;
    }
}
