package com.example.proyectoandroid;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import com.example.proyectoandroid.Constantes;

public interface ServicioWeb {

    @POST("user/login")
    Call<RespuestaWS> login(@Body Login login);

    @POST("user/create")
    Call<RespuestaWSRegister> create(@Body User  user);

    @POST("user/logout")
    Call<RespuestaWS> logout(@Body Logout  logout,@Header("Authorization")String token);

}
