package com.example.proyectoandroid;

import java.util.Objects;

public class respuestaErronea {
    private int status_code;
    private String message;
    private Errores errors;

    @Override
    public String toString() {
        return "respuestaErronea{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                ", errores=" + errors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        respuestaErronea that = (respuestaErronea) o;
        return status_code == that.status_code &&
                Objects.equals(message, that.message) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_code, message, errors);
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Errores getErrors() {
        return errors;
    }

    public void setErrors(Errores errors) {
        this.errors = errors;
    }

    public respuestaErronea() {
    }

    public respuestaErronea(int status_code, String message, Errores errores) {
        this.status_code = status_code;
        this.message = message;
        this.errors = errores;
    }
}
