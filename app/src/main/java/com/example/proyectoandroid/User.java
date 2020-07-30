package com.example.proyectoandroid;

import java.util.Objects;

public class User {
    private String user_id;
    private String user_image;
    private String user_thumbnail;
    private String name;
    private String lastname;
    private String run;
    private String username;
    private String email;
    private String password;
    private String token_enterprise;

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_image='" + user_image + '\'' +
                ", user_thumbnail='" + user_thumbnail + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", run='" + run + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token_enterprise='" + token_enterprise + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user_id, user.user_id) &&
                Objects.equals(user_image, user.user_image) &&
                Objects.equals(user_thumbnail, user.user_thumbnail) &&
                Objects.equals(name, user.name) &&
                Objects.equals(lastname, user.lastname) &&
                Objects.equals(run, user.run) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(token_enterprise, user.token_enterprise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, user_image, user_thumbnail, name, lastname, run, username, email, password, token_enterprise);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_thumbnail() {
        return user_thumbnail;
    }

    public void setUser_thumbnail(String user_thumbnail) {
        this.user_thumbnail = user_thumbnail;
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

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken_enterprise() {
        return token_enterprise;
    }

    public void setToken_enterprise(String token_enterprise) {
        this.token_enterprise = token_enterprise;
    }

    public User() {
    }

    public User(String user_id, String user_image, String user_thumbnail, String name, String lastname, String run, String username, String email, String password, String token_enterprise) {
        this.user_id = user_id;
        this.user_image = user_image;
        this.user_thumbnail = user_thumbnail;
        this.name = name;
        this.lastname = lastname;
        this.run = run;
        this.username = username;
        this.email = email;
        this.password = password;
        this.token_enterprise = token_enterprise;
    }
}
