package com.example.proyectoandroid;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MensajesActivity extends FragmentActivity {
    private String token;
    private int id;
    private String username;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private String user_id;
    private String username2;
    private String pathPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);
        Bundle params = getIntent().getExtras();
        token= params.getString("token");
        id= params.getInt("id");
        username= params.getString("username");
        pathPhoto = params.getString("imagen");
        oneFragment = OneFragment.newInstance(id+"",username,token,pathPhoto);
        twoFragment = TwoFragment.newInstance(id+"",username,token);


        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout1, oneFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout2, twoFragment).commit();
    }
    public void back(View view){
        parametrosLogoutBack(token,id,username);
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