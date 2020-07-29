package com.example.proyectoandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Context;
import android.provider.Settings.Secure;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private EditText user, pass;
    private ServicioWeb servicio;
    private String token;
    private int id;
    private String username;
    private String device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user= findViewById(R.id.editText);
        pass= findViewById(R.id.editText2);
        button = findViewById(R.id.button);

        device_id = Secure.getString(getContentResolver(),Secure.ANDROID_ID);
        //System.out.println("este es el device id "+device_id);
        button.setOnClickListener(this);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicio = retrofit.create(ServicioWeb.class);

    }
    public void register(View view){
        Intent registro = new Intent(this, RegisterActivity.class);
        startActivity(registro);
        finish();
    }



    @Override
    public void onClick(View v) {

        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            Login login = new Login(user.getText().toString(), pass.getText().toString(), device_id.toString());
            final Call<RespuestaWS> resp = servicio.login(login);
            resp.enqueue(new Callback<RespuestaWS>() {
                @Override
                public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                    if (response.isSuccessful() && response != null && response.body() != null) {
                        Log.d("Retrofit", response.body().getStatus_code());
                        Log.d("Retrofit", response.body().getData().getId() + "");
                        Log.d("Retrofit", response.body().getToken());
                        token = response.body().getToken();
                        int id = response.body().getData().getId();
                        String username = response.body().getData().getUsername();
                        parametroLogout(token, id, username);
                    } else {
                        System.out.println("texto error");
                        Gson gson = new Gson();
                        respuestaErronea res = new respuestaErronea();
                        System.out.println("paso por aqui");
                        try {
                            res = gson.fromJson(response.errorBody().string(), respuestaErronea.class);
                            System.out.println("Codigo error:: " + res.getStatus_code());
                            System.out.println(res);

                            if (res.getStatus_code() == 400) {
                                user.setError("La sesión se encuentra iniciada en otro dispositivo, cierre esa sesión antes de iniciar otra");
                            }
                            if (res.getStatus_code() == 401) {
                                Toast toast =
                                        Toast.makeText(getApplicationContext(),
                                                "Las credenciales son incorrectas o no existen", Toast.LENGTH_SHORT);

                                toast.show();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Log.d("Retrofit","Error body convert: "+ response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<RespuestaWS> call, Throwable t) {

                }
            });
        }
    }


    private boolean validateUsername() {
        String usernameInput = user.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            user.setError("El campo no puede ser vacio");
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        String usernameInput = pass.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            pass.setError("El campo no puede ser vacio");
            return false;
        } else {
            return true;
        }
    }

    public void parametroLogout(String token, int id, String username){
        Intent intent = new Intent(this, LogoutActivity.class);
        Bundle parametros = new Bundle();
        parametros.putString("username",username);
        parametros.putInt("id",id);
        parametros.putString("token",token);
        intent.putExtras(parametros);
        startActivity(intent);
        finish();
    }

    public void guardaFoto(View view){

        parametrosGuardaFoto("SARU12",123,"username");

    }


    public void parametrosGuardaFoto(String token, int id, String username){
        Intent intent = new Intent(this, GuardaFotoActivity.class);
        Bundle parametros = new Bundle();
        parametros.putString("username","testo22");
        parametros.putInt("id",155);
        parametros.putString("token",token);
        intent.putExtras(parametros);
        startActivity(intent);
        finish();
    }

}
