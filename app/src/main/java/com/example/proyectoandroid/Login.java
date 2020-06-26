package com.example.proyectoandroid;

import java.util.Objects;

public class Login {
    private String username;
    private String password;
    private String device_id;

    public Login(){

    }

    public Login(String username, String password, String device_id) {
        this.username = username;
        this.password = password;
        this.device_id = device_id;
    }

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", device_id='" + device_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(username, login.username) &&
                Objects.equals(password, login.password) &&
                Objects.equals(device_id, login.device_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, device_id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
