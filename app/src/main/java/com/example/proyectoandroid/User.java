package com.example.proyectoandroid;

import java.util.Objects;

public class User {
    private String name;
    private String lastname;
    private String run;
    private String username;
    private String email;
    private String password;
    private String token_enterprise;

    public User(){

    }

    public User(String name, String lastname, String run, String username, String email, String password, String token_enterprise) {
        this.name = name;
        this.lastname = lastname;
        this.run = run;
        this.username = username;
        this.email = email;
        this.password = password;
        this.token_enterprise = token_enterprise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(lastname, user.lastname) &&
                Objects.equals(run, user.run) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(token_enterprise, user.token_enterprise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastname, run, username, email, password, token_enterprise);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", run='" + run + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token_enterprise='" + token_enterprise + '\'' +
                '}';
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
}
