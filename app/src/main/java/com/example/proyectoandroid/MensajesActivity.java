package com.example.proyectoandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private ImageButton tomarFoto;
    RecyclerView rv;
    private Adapter adapter;
    List<Data> mensajes;
    private ImageView contenedorFoto;



    private final static int REQUEST_PERMISSION = 1001;
    private final static int REQUEST_CAMERA = 1002;
    private final static String[] PERMISSION_REQUIRED =
            new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE" };

    private String pathPhoto;



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


        /////////////
        tomarFoto = findViewById(R.id.tomarFoto);
        contenedorFoto=findViewById(R.id.contenedorImagen);
//        tomarFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(pathPhoto != null){
//                    contenedorFoto.setVisibility(View.VISIBLE);
//                    showPhoto();
//                }else{
//                    Toast.makeText(MensajesActivity.this, "No hay foto, debe sacar una", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        if(verifyPermission()){
            startCameraInit();
        }else{
            ActivityCompat.requestPermissions(this, PERMISSION_REQUIRED, REQUEST_PERMISSION);
        }
        ////////////

        RequestBody usernameRb =RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody idRb =RequestBody.create(MediaType.parse("multipart/form-data"), id+"");
        Call<RespuestaWSLastM> call = servicio.mGet(idRb,usernameRb,tokenB1);
        rv = findViewById(R.id.containerF2);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        call.enqueue(new Callback<RespuestaWSLastM>() {
            @Override
            public void onResponse(Call<RespuestaWSLastM> call, Response<RespuestaWSLastM> response) {
                if(response.isSuccessful()){
                    Data data = new Data();
//                  mensajes.removeAll(mensajes);

                    mensajes=response.body().getData();
                    adapter=new Adapter(mensajes,getApplicationContext());
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
        //// Parte llamada foto

            File archivoImagen = new File(SiliCompressor.with(getApplicationContext()).compress(pathPhoto, new File(this.getCacheDir(),"temp")));
            RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImagen);
            MultipartBody.Part file = MultipartBody.Part.createFormData("image", archivoImagen.getName(), imagen);
            System.out.println(pathPhoto);


        ///
        Call<RespuestaWS> call = servicio.mSend(idRb,usernameRb,msnRb,file,
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
                                adapter=new Adapter(mensajes,getApplicationContext());
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
    public void capturarFoto(View view){
}



////////////////////// Metodos capturar foto


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
//                File photoFileCompressed = null;
                try{
                    photoFile = createFilePhoto();
//                    photoFileCompressed=createFilePhotoTemp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(photoFile != null){
                    Uri photoUri = FileProvider.getUriForFile(this,
                            "com.example.proyectoandroid.fileprovider",
                            photoFile);
//                    Uri photoUriCompressed = FileProvider.getUriForFile(this,
//                            "com.example.proyectoandroid.fileprovider",
//                            photoFileCompressed);
                    iniciarCamara.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                    iniciarCamara.putExtra(MediaStore.EXTRA_OUTPUT, photoUriCompressed);
                    startActivityForResult(iniciarCamara, REQUEST_CAMERA);
                }
            }
        }
    }



    private void showPhoto(){
        int targetW = 200;
        int targetH = 200;

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