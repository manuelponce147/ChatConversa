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

import com.example.proyectoandroid.Constantes;

public interface ServicioWeb {

    @POST("user/login")
    Call<RespuestaWS> login(@Body Login login);

    @POST("user/create")
    Call<RespuestaWSRegister> create(@Body User  user);

    @POST("user/logout")
    Call<RespuestaWS> logout(@Body Logout  logout,@Header("Authorization")String token);

//    @Multipart
    @POST("user/load/image")
    Call<RespuestaWSFoto> subirImagen(@Body Imagen  imagen,@Header("Authorization")String token);
//    Call<RespuestaWSFoto> subirImagen(@Body @Part("body") RequestBody body, @Part MultipartBody.Part file,@Header("Authorization")String token);
//
//    @Multipart
//    @POST("/index.php/WS_SAVE_IMG")
//    Call<RespuestaWSFoto> subirImage(@Part MultipartBody.Part file, @Part("nombre") RequestBody nombre);
}


