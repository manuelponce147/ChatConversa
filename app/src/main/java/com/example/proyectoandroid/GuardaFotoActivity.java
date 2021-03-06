package com.example.proyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

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
import com.iceteck.silicompressorr.FileUtils;
import com.iceteck.silicompressorr.SiliCompressor;
import com.squareup.picasso.Picasso;

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
    private String pathPhotoCompressed;

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

        if(((Constantes) getApplication()).getImage()!=null){
            System.out.println("Presenta la imagen guardada");
            System.out.println(((Constantes) getApplication()).getImage());
            Picasso.get().load(""+((Constantes) getApplication()).getImage()).into(contenedorFoto);
//            Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png").into(imageView);
        }

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
        File archivoImagen = new File(SiliCompressor.with(getApplicationContext()).compress(pathPhoto, new File(this.getCacheDir(),"temp")));
        RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImagen);
        MultipartBody.Part file = MultipartBody.Part.createFormData("user_image", archivoImagen.getName(), imagen);
        RequestBody userRB = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody idRB = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(id));
        String tokenBearer = "Bearer " + token;
        Call<RespuestaWS> call = servicioWeb.subirImage(file,userRB,idRB,tokenBearer);
        Log.d("Retrofit",call.toString());

        call.enqueue(new Callback<RespuestaWS>() {
            @Override
            public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                Log.d("Retrofit",response.toString());
                if(response.isSuccessful() && response != null && response.body() != null){
                    ((Constantes) getApplication()).setImage(response.body().getData().getImage());
                    System.out.println(response.body().getData().getImage());
                    ((Constantes) getApplication()).setThumbnail(response.body().getData().getThumbnail());
                    System.out.println(response.body().getData().getThumbnail());
                    Log.d("Retrofit",response.body().getMessage());
                    Log.d("Retrofit",response.body().getStatus_code());
                    Log.d("Retrofit",response.body().getData().toString());

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Subida correctamente", Toast.LENGTH_SHORT);

                    toast1.show();
                } else {

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
//    private File createFilePhotoTemp() throws IOException {
//        String timestamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String file_name = "JPEG_" + timestamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File photo = File.createTempFile(
//                file_name,
//                "temp.jpg",
//                storageDir
//        );
//        pathPhotoCompressed = photo.getAbsolutePath();
//        return photo;
//    }
//

    public void back(View view){
        parametrosLogoutBack(token,id,username);
    }
    public void parametrosLogoutBack(String token, int id, String username){
        Intent intent = new Intent(this, ConfiguracionActivity.class);
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