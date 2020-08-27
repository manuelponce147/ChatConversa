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
    private double latitude;
    private double longitude;
    private String image;
    private String thumbnail;
    private User user;

    public Data() {
    }

    public Data(int id, String name, String date, String message, String lastname, String username, String run, String email, double latitude, double longitude, String image, String thumbnail, User user) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.message = message;
        this.lastname = lastname;
        this.username = username;
        this.run = run;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.thumbnail = thumbnail;
        this.user = user;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", user=" + user +
                '}';
    }
}
