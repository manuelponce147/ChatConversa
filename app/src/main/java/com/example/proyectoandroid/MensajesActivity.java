package com.example.proyectoandroid;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MensajesActivity extends FragmentActivity {
    private String token;
    private int id;
    private String username;
    private EditText mensajeLayoutTxt;
    private ServicioWeb servicio;
    RecyclerView rv;
    Adapter adapter;
    List<Data> mensajes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);
        Bundle params = getIntent().getExtras();
        token= params.getString("token");
        String tokenB1 = "Bearer " + token;
        id= params.getInt("id");
        username= params.getString("username");
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicio = retrofit.create(ServicioWeb.class);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                RequestBody usernameRb =RequestBody.create(MediaType.parse("multipart/form-data"), username);
                RequestBody idRb =RequestBody.create(MediaType.parse("multipart/form-data"), id+"");
                Call<RespuestaWSLastM> call = servicio.mGet(idRb,usernameRb,tokenB1);
                call.enqueue(new Callback<RespuestaWSLastM>() {
                    @Override
                    public void onResponse(Call<RespuestaWSLastM> call, Response<RespuestaWSLastM> response) {
                        if(response.isSuccessful()){
                            Data data = new Data();
//                  mensajes.removeAll(mensajes);
                            rv = findViewById(R.id.containerF2);
                            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            mensajes=response.body().getData();
                            adapter=new Adapter(mensajes);
                            rv.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaWSLastM> call, Throwable t) {

                    }
                });
            }
        }, 0, 1000);//put here time 1000 milliseconds=1 second
        //////////////
        //////////////
        RequestBody usernameRb =RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody idRb =RequestBody.create(MediaType.parse("multipart/form-data"), id+"");
        Call<RespuestaWSLastM> call = servicio.mGet(idRb,usernameRb,tokenB1);
        call.enqueue(new Callback<RespuestaWSLastM>() {
            @Override
            public void onResponse(Call<RespuestaWSLastM> call, Response<RespuestaWSLastM> response) {
                if(response.isSuccessful()){
                    Data data = new Data();
//                  mensajes.removeAll(mensajes);
                    rv = findViewById(R.id.containerF2);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mensajes=response.body().getData();
                    adapter=new Adapter(mensajes);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<RespuestaWSLastM> call, Throwable t) {

            }
        });

    }
    public void back(View view){
        parametrosLogoutBack(token,id,username);
    }
    public void enviarMensaje(View view){
        mensajeLayoutTxt= (EditText) findViewById(R.id.mensajeLayout);
        RequestBody usernameRb =RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody idRb =RequestBody.create(MediaType.parse("multipart/form-data"), id+"");
        RequestBody msnRb =RequestBody.create(MediaType.parse("multipart/form-data"),
                mensajeLayoutTxt.getText().toString());
        String tokenB = "Bearer " + token;
        Call<RespuestaWS> call = servicio.mSend(idRb,usernameRb,msnRb,null,
                null,null,tokenB);
        call.enqueue(new Callback<RespuestaWS>() {
            @Override
            public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                Log.d("Retrofit",response.toString());
                if(response.isSuccessful() && response != null && response.body() != null){
                    Call<RespuestaWSLastM> call2 = servicio.mGet(idRb,usernameRb,tokenB);
                    call2.enqueue(new Callback<RespuestaWSLastM>() {
                        @Override
                        public void onResponse(Call<RespuestaWSLastM> call, Response<RespuestaWSLastM> response) {
                            if(response.isSuccessful()){
                                Data data = new Data();
            //                  mensajes.removeAll(mensajes);
                                rv = findViewById(R.id.containerF2);
                                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mensajes=response.body().getData();
                                adapter=new Adapter(mensajes);
                                rv.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<RespuestaWSLastM> call, Throwable t) {

                        }
                    });
                }else {

                    Gson gson= new Gson();
                    respuestaErronea res = new respuestaErronea();
                    try{
                        res = gson.fromJson(response.errorBody().string(),respuestaErronea.class);
                        System.out.println("Respuesta: "+res);


                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaWS> call, Throwable t) {

            }
        });

    }


    public void parametrosLogoutBack(String token, int id, String username){
        Intent intent = new Intent(this, LogoutActivity.class);
        Bundle parametros = new Bundle();
        parametros.putString("username",username);
        parametros.putInt("id",id);
        parametros.putString("token",token);
        intent.putExtras(parametros);
        startActivity(intent);
        finish();
    }


}