package com.example.proyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ConfiguracionActivity extends AppCompatActivity {
    private String token;
    private int id;
    private String username;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        Bundle params = getIntent().getExtras();
        token= params.getString("token");
        id= params.getInt("id");
        username= params.getString("username");
        imageView=findViewById(R.id.contenedorImagen);
        System.out.println("PICASSO INTENTO");
        if(((Constantes) getApplication()).getImage()!=null){

            System.out.println("Presenta la imagen guardada");
            System.out.println(((Constantes) getApplication()).getImage());
//            Picasso.get().load(""+((Constantes) getApplication()).getImage()).into(imageView);
            Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png").into(imageView);
        } else {
            System.out.println("Presenta un placeholder");
            Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png").into(imageView);
        }

        Picasso.get().load("\""+((Constantes) getApplication()).getImage()+"\"").into(imageView);

    }
    public void back(View view){
        parametrosConfiguracion(token,id,username);
    }
    public void parametrosConfiguracion(String token, int id, String username){
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

        parametrosGuardaFoto(token,id,username);

    }


    public void parametrosGuardaFoto(String token, int id, String username){
        Intent intent = new Intent(this, GuardaFotoActivity.class);
        Bundle parametros = new Bundle();
        parametros.putString("username",username);
        parametros.putInt("id",id);
        parametros.putString("token",token);
        intent.putExtras(parametros);
        startActivity(intent);
        finish();
    }
}