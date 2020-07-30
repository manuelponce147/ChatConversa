package com.example.proyectoandroid;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RespuestaWSLastM {
    private String status_code;
    private String message;
    private String token;
    private List<Data> data;

    @Override
    public String toString() {
        return "RespuestaWSLastM{" +
                "status_code='" + status_code + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaWSLastM that = (RespuestaWSLastM) o;
        return Objects.equals(status_code, that.status_code) &&
                Objects.equals(message, that.message) &&
                Objects.equals(token, that.token) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_code, message, token, data);
    }

    public RespuestaWSLastM() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public RespuestaWSLastM(String status_code, String message, String token, List<Data> data) {
        this.status_code = status_code;
        this.message = message;
        this.token = token;
        this.data = data;
    }
}
