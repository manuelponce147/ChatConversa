package com.example.proyectoandroid;

import java.util.Objects;

public class Logout {
    private int user_id;
    private String username;

    public Logout(){

    }
    public Logout(int user_id, String username) {
        this.user_id = user_id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "logout{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logout logout = (Logout) o;
        return Objects.equals(user_id, logout.user_id) &&
                Objects.equals(username, logout.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username);
    }

    public int getUser_Id() {
        return user_id;
    }

    public void setUser_Id(int user_Id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
