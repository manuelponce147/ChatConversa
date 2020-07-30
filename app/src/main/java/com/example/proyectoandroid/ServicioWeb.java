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
    Call<RespuestaWS> create(@Body User  user);

    @POST("user/logout")
    Call<RespuestaWS> logout(@Body Logout  logout,
                             @Header("Authorization")String token);

    @POST("message/send")
    Call<RespuestaWS> mSend(@Part("user_id")RequestBody user_id,
                            @Part("username") RequestBody username,
                            @Part("message") RequestBody message,
                            @Part MultipartBody.Part file,
                            @Part("latitude") RequestBody latitude,
                            @Part("longitude") RequestBody longitude,
                            @Header("Authorization")String token);

    @Multipart
    @POST("user/load/image")
    Call<RespuestaWS> subirImage(@Part MultipartBody.Part file, @Part("username") RequestBody username, @Part("user_id")RequestBody user_id, @Header("Authorization")String token);

    @POST("message/get")
    Call<RespuestaWS> mGet(@Part("user_id")RequestBody user_id,
                           @Part("username")RequestBody username,
                           @Header("Authorization")String token);

}
