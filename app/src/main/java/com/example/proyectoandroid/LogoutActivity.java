package com.example.proyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.proyectoandroid.Interceptador;

public class LogoutActivity extends AppCompatActivity {
    private ServicioWeb servicio;
    private Button logoutButton;
    private String token;
    private int id;
    private String username;
    int flag=0;
    private String pathPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        logoutButton = findViewById(R.id.logout);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                //.client(cliente)
                .build();
        servicio = retrofit.create(ServicioWeb.class);

        Bundle params = getIntent().getExtras();
        token= params.getString("token");
        id= params.getInt("id");
        username= params.getString("username");
        pathPhoto= params.getString("imagen");
    }
    public void onClick(View view){
        Logout logout = new Logout(id,username);
        String tokenBearer = "Bearer " + token;
        Call<RespuestaWS> call = servicio.logout(logout,tokenBearer);
        call.enqueue(new Callback<RespuestaWS>() {
            @Override
            public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {

               if(response.isSuccessful() && response != null && response.body() != null){
                   Log.d("Retrofit",response.body().getMessage());
                   Log.d("Retrofit",response.body().getStatus_code());
                   Login();
               }
            }

            @Override
            public void onFailure(Call<RespuestaWS> call, Throwable t) {

            }
        });

    }
    public void Login(){
        if(flag==0){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        }
    }

    public void SalirApp (View view){
        flag=1;
        onClick(view);
        finish();

    }

    public void configuracion(View view){

        parametrosConfiguracion(token,id,username);

    }

    public void verMensajes(View view){
        parametrosMensaje(token,id,username,pathPhoto);
    }
    public void parametrosConfiguracion(String token, int id, String username){
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        Bundle parametros = new Bundle();
        parametros.putString("username",username);
        parametros.putInt("id",id);
        parametros.putString("token",token);
        intent.putExtras(parametros);
        startActivity(intent);
        finish();
    }
    public void parametrosMensaje(String token, int id, String username, String imagen){
        Intent intent = new Intent(this, MensajesActivity.class);
        Bundle parametros = new Bundle();
        parametros.putString("username",username);
        parametros.putInt("id",id);
        parametros.putString("token",token);
        parametros.putString("imagen",pathPhoto);
        intent.putExtras(parametros);
        startActivity(intent);
        finish();
    }

}
