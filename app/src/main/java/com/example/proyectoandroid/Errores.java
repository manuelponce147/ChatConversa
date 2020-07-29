package com.example.proyectoandroid;

import java.util.List;
import java.util.Objects;

class Errores {

    private List<String> username;
    private List<String> email;
    private List<String> token_enterprise;
    private List<String> user_id;
    private List<String> user_image;
    private List<String> image;
    private List<String> thumbnail;

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getToken_enterprise() {
        return token_enterprise;
    }

    public void setToken_enterprise(List<String> token_enterprise) {
        this.token_enterprise = token_enterprise;
    }

    public List<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<String> user_id) {
        this.user_id = user_id;
    }

    public List<String> getUser_image() {
        return user_image;
    }

    public void setUser_image(List<String> user_image) {
        this.user_image = user_image;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<String> getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(List<String> thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Errores{" +
                "username=" + username +
                ", email=" + email +
                ", token_enterprise=" + token_enterprise +
                ", user_id=" + user_id +
                ", user_image=" + user_image +
                ", image=" + image +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
