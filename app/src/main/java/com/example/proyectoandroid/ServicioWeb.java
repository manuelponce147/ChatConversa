package com.example.proyectoandroid;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

import com.example.proyectoandroid.Constantes;

import java.util.Map;

public interface ServicioWeb {

    @POST("user/login")
    Call<RespuestaWS> login(@Body Login login);

    @POST("user/create")
    Call<RespuestaWSRegister> create(@Body User  user);

    @POST("user/logout")
    Call<RespuestaWS> logout(@Body Logout  logout,@Header("Authorization")String token);

    @Multipart
    @POST("user/load/image")
    Call<RespuestaWSFoto> subirImage(@Part MultipartBody.Part file, @Part("username")RequestBody username,@Part("user_id")RequestBody user_id,@Header("Authorization")String token);

}


