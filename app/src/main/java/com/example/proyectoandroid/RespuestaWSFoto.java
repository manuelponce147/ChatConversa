package com.example.proyectoandroid;

import java.util.Objects;

public class RespuestaWSFoto {

    private String status_code;
    private String message;
    private Data data;

    public RespuestaWSFoto(String status_code, String message, Data data) {
        this.status_code = status_code;
        this.message = message;
        this.data = data;
    }

    public RespuestaWSFoto() {
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaWSFoto that = (RespuestaWSFoto) o;
        return Objects.equals(status_code, that.status_code) &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_code, message, data);
    }

    @Override
    public String toString() {
        return "RespuestaWSFoto{" +
                "status_code='" + status_code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}