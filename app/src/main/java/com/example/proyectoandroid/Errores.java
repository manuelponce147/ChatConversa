package com.example.proyectoandroid;

import java.util.List;
import java.util.Objects;

class Errores {

    private List<String> username;
    private List<String> email;
    private List<String> token_enterprise;

    public Errores() {
    }

    public Errores(List<String> username, List<String> email, List<String> token_enterprise) {
        this.username = username;
        this.email = email;
        this.token_enterprise = token_enterprise;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Errores errores = (Errores) o;
        return Objects.equals(username, errores.username) &&
                Objects.equals(email, errores.email) &&
                Objects.equals(token_enterprise, errores.token_enterprise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, token_enterprise);
    }

    @Override
    public String toString() {
        return "Errores{" +
                "username=" + username +
                ", email=" + email +
                ", token_enterprise=" + token_enterprise +
                '}';
    }
}
