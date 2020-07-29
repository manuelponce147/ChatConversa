package com.example.proyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuardaFotoActivity extends AppCompatActivity {

    private Button tomarFoto;
    private Button subirFoto;
    private ImageView contenedorFoto;

    private String token;
    private int id;
    private String username;

    private final static int REQUEST_PERMISSION = 1001;
    private final static int REQUEST_CAMERA = 1002;
    private final static String[] PERMISSION_REQUIRED =
            new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE" };

    private String pathPhoto;

    private ServicioWeb servicioWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardafoto);
        Bundle params = getIntent().getExtras();
        token= params.getString("token");
        id= params.getInt("id");
        username= params.getString("username");

        tomarFoto = findViewById(R.id.tomarFoto);
        contenedorFoto = findViewById(R.id.contenedorImagen);

        subirFoto = findViewById(R.id.subirFoto);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicioWeb = retrofit.create(ServicioWeb.class);

        subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pathPhoto != null){

                    subirImagen();
                }else{
                    Toast.makeText(GuardaFotoActivity.this, "No hay foto, debe sacar una", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(verifyPermission()){
            startCameraInit();
        }else{
            ActivityCompat.requestPermissions(this, PERMISSION_REQUIRED, REQUEST_PERMISSION);
        }

    }

    private void subirImagen(){
        File archivoImagen = new File(pathPhoto);

//        RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImagen);
//
//        MultipartBody.Part file = MultipartBody.Part.createFormData("file", archivoImagen.getName(), imagen);
//
//        RequestBody nombre = RequestBody.create(MediaType.parse("text"), "Móviles 2020");

        Imagen imagen= new Imagen(123,username,archivoImagen);
        String tokenBearer = "Bearer " + token;


        Call<RespuestaWSFoto> call = servicioWeb.subirImagen(imagen,tokenBearer);
        Log.d("Retrofit",servicioWeb.subirImagen(imagen,tokenBearer).toString());
        call.enqueue(new Callback<RespuestaWSFoto>() {
            @Override
            public void onResponse(Call<RespuestaWSFoto> call, Response<RespuestaWSFoto> response) {
                Log.d("Retrofit",response.toString());
                if(response.isSuccessful() && response != null && response.body() != null){
                    Log.d("Retrofit",response.body().getMessage());
                    Log.d("Retrofit",response.body().getStatus_code());
                }
            }

            @Override
            public void onFailure(Call<RespuestaWSFoto> call, Throwable t) {

            }
        });
//        Call<RespuestaWSFoto> call = servicioWeb.subirImagen(nombre,file,tokenBearer);
//        Log.d("Body", "TOSTRING: " + image.toString());
//        Log.d("File", "TOSTRING: " + archivoImagen.toString());
//        Log.d("TokenBearer", "TOSTRING: " + tokenBearer);
//        call.enqueue(new Callback<RespuestaWSFoto>() {
//            @Override
//            public void onResponse(Call<RespuestaWSFoto> call, Response<RespuestaWSFoto> response) {
//                RespuestaWSFoto respuesta = response.body();
//                Log.d("RESPUESTA", "Response: " + response);
//                Log.d("RESPUESTA", "Response Body: " + response);
//                if(response.isSuccessful() && response!= null && response.body() != null){
//                    Log.d("Retrofit","Exito: "+ response.body().toString());
//                    Toast toast =
//                            Toast.makeText(getApplicationContext(),
//                                    "Registro exitoso", Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RespuestaWSFoto> call, Throwable t) {
//                Log.d("ERROR", "MSG: " + t.getMessage());
//                Toast.makeText(GuardaFotoActivity.this, "Error subiendo", Toast.LENGTH_SHORT).show();
//            }
//        }
//        );
    }

//    private void subirImagen(){
//        File archivoImagen = new File(pathPhoto);
//
//        RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImagen);
//
//        MultipartBody.Part file = MultipartBody.Part.createFormData("file", archivoImagen.getName(), imagen);
//
//        RequestBody nombre = RequestBody.create(MediaType.parse("multipart/form-data"), "Móviles 2020");
//
//        servicioWeb.subirImage(file, nombre).enqueue(new Callback<RespuestaWSFoto>() {
//            @Override
//            public void onResponse(Call<RespuestaWSFoto> call, Response<RespuestaWSFoto> response) {
//                RespuestaWSFoto respuesta = response.body();
//                Toast.makeText(GuardaFotoActivity.this, "Subida con exito", Toast.LENGTH_SHORT).show();
//                Log.d("EXITO", "TOSTRING: " + respuesta.toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<RespuestaWSFoto> call, Throwable t) {
//                Log.d("ERROR", "MSG: " + t.getMessage());
//                Toast.makeText(GuardaFotoActivity.this, "Error subiendo", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private boolean verifyPermission(){
        for(String permission : PERMISSION_REQUIRED){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION){
            if(verifyPermission()){
                startCameraInit();
            }else{
                Toast.makeText(this, "Los permisos deben ser autorizados", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startCameraInit(){
        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });
    }

    private void startCamera(){
        if(false){
            Intent iniciarCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(iniciarCamara.resolveActivity(getPackageManager()) != null){
                startActivityForResult(iniciarCamara, REQUEST_CAMERA);
            }
        }else{
            Intent iniciarCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(iniciarCamara.resolveActivity(getPackageManager()) != null){
                File photoFile = null;
                try{
                    photoFile = createFilePhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(photoFile != null){
                    Uri photoUri = FileProvider.getUriForFile(this,
                            "com.example.proyectoandroid.fileprovider",
                            photoFile);
                    iniciarCamara.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(iniciarCamara, REQUEST_CAMERA);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
            if(false){
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                contenedorFoto.setImageBitmap(imageBitmap);
            }else{
                showPhoto();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showPhoto(){
        int targetW = contenedorFoto.getWidth();
        int targetH = contenedorFoto.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int scale = (int) targetW/targetH;

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scale;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(pathPhoto, bmOptions);
        contenedorFoto.setImageBitmap(bitmap);
    }

    private File createFilePhoto() throws IOException {
        String timestamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String file_name = "JPEG_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(
                file_name,
                ".jpg",
                storageDir
        );
        pathPhoto = photo.getAbsolutePath();
        return photo;
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