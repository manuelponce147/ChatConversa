package com.example.proyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity {
    private ServicioWeb servicio;
    TextInputLayout name;
    TextInputLayout lastname;
    TextInputLayout run;
    TextInputLayout username;
    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout token;
    private static final Pattern PASSWORD_PATTERN =Pattern.compile("^" +"(?=.*[a-z])" +"(?=.*[A-Z])" +"(?=.*\\d)" +"[a-zA-Z\\d]"+".{6,12}" +"$");
    private static final Pattern TOKEN_PATTERN =Pattern.compile("^" +"[A - Z\\d]"+"{6,6}" +"$");
    int flag2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicio=retrofit.create(ServicioWeb.class);


    }


    public void onClick(View view){

        name = findViewById(R.id.nameLabel);
        lastname = findViewById(R.id.lastnameLabel);
        run = findViewById(R.id.runLabel);
        username = findViewById(R.id.usernameLabel);
        email = findViewById(R.id.emailLabel);
        password = findViewById(R.id.passwordLabel);
        token=findViewById(R.id.tokenLabel);
        User usuario=new User("",
                "" ,
                "" ,
                name.getEditText().getText().toString(),
                lastname.getEditText().getText().toString(),
                run.getEditText().getText().toString(),
                username.getEditText().getText().toString()
                , email.getEditText().getText().toString(),
                password.getEditText().getText().toString(),
                token.getEditText().getText().toString());


       if (!validateName() |!validateLastName() |!validateRun() | !validateUsername() |!validateEmail() | !validatePassword()) {

        } else {
           Call<RespuestaWSRegister> call = servicio.create(usuario);
           call.enqueue(new Callback<RespuestaWSRegister>() {
               @Override
               public void onResponse(Call<RespuestaWSRegister> call, Response<RespuestaWSRegister> response) {

                   if(response.isSuccessful() && response!= null && response.body() != null){
                       ((Constantes) getApplication()).setId(""+response.body().getData().getId());
                       ((Constantes) getApplication()).setName(name.getEditText().getText().toString());
                       ((Constantes) getApplication()).setLastname(lastname.getEditText().getText().toString());
                       ((Constantes) getApplication()).setUsername(username.getEditText().getText().toString());
                       ((Constantes) getApplication()).setRun(run.getEditText().getText().toString());
                       ((Constantes) getApplication()).setEmail(email.getEditText().getText().toString());
                       ((Constantes) getApplication()).setToken(token.getEditText().getText().toString());



                       flag2=1;
                       Log.d("Retrofit","Exito: "+ response.body().toString());
                       Toast toast =
                               Toast.makeText(getApplicationContext(),
                                       "Registro exitoso", Toast.LENGTH_SHORT);


                       toast.show();
                       Intent intent = new Intent(view.getContext(),MainActivity.class);
                       startActivity(intent);
                       finish();


                   }else{

                       Gson gson= new Gson();
                       respuestaErronea res = new respuestaErronea();
                       try{
                           res = gson.fromJson(response.errorBody().string(),respuestaErronea.class);
                           System.out.println("Respuesta: "+response);
                           if (res.getStatus_code()==400){
                               if (res.getErrors().getUsername()!=null){
                                   System.out.println(res.getErrors().getUsername());
                                   Log.d("Retrofit","Error: "+ res.getErrors().getUsername().toString());
                                   username.setError("El valor del campo username ya está en uso.");

                               }
                               if (res.getErrors().getEmail()!=null){
                                   System.out.println(res.getErrors().getEmail());
                                   Log.d("Retrofit","Error: "+ res.getErrors().getEmail().toString());
                                   email.setError("El valor del campo email ya está en uso.");
                               }
                               if (res.getErrors().getToken_enterprise()!=null){
                                   System.out.println(res.getErrors().getToken_enterprise());
                                   Log.d("Retrofit","Error: "+ res.getErrors().getToken_enterprise().toString());
                                   token.setError("EL valor del token no es valido.");
                               }

                           }

                       }catch (IOException e){
                           e.printStackTrace();
                       }
                   }

               }

               @Override
               public void onFailure(Call<RespuestaWSRegister> call, Throwable t) {

               }
           });

       }



    if(flag2==1){

    }

    }


    //Validaciones


    private boolean validateName() {
        String nameInput = name.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()) {
            name.setError("El campo no puede ser vacio");
            return false;
        } else if (nameInput.length() > 60) {
            name.setError("El campo no puede exceder los 60 caracteres");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }


    private boolean validateLastName() {
        String lastNameInput = lastname.getEditText().getText().toString().trim();
        if (lastNameInput.isEmpty()) {
            lastname.setError("El campo no puede ser vacio");
            return false;
        } else if (lastNameInput.length() > 60) {
            lastname.setError("El campo no puede exceder los 60 caracteres");
            return false;
        } else {
            lastname.setError(null);
            return true;
        }
    }


    private boolean validateRun() {
        String runInput = run.getEditText().getText().toString().trim();
        if (runInput.isEmpty()) {
            run.setError("El campo no puede ser vacio");
            return false;
        } else if (runInput.length() > 8|| runInput.length() < 7) {
            run.setError("El campo no puede tener mas de 8 ni menos de 7 caracteres");
            return false;
        } else {
            run.setError(null);
            return true;
        }
    }


    private boolean validateUsername() {
        String usernameInput = username.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            username.setError("El campo no puede ser vacio");
            return false;
        } else if (usernameInput.length() > 8||usernameInput.length() < 4) {
            username.setError("El campo no puede tener mas de 8 ni menos de 4 caracteres");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }


    private boolean validateEmail() {
        String emailInput = email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("El campo no puede ser vacio");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Por favor inserta un correo valido");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }


    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            password.setError("El campo no puede ser vacio");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password muy debil");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }


    
    public void cancel(View view){
        Intent cancel = new Intent(this, MainActivity.class);
        startActivity(cancel);
        finish();
    }

}
