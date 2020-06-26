package com.example.proyectoandroid;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Interceptador implements Interceptor {
    private Constantes constante;
    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = constante.getToken();
        System.out.println("Tokeeeeeeeeeeeeeeeeen   " + token);
        System.out.println("===========================");
        System.out.println("===========================");
        System.out.println("===========================");
        System.out.println("===========================");
        Request request = chain.request().newBuilder().addHeader("Authorization","Bearer" + token).build();
        return chain.proceed(request);
    }
}
