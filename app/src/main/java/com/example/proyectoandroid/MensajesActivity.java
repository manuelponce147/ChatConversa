package com.example.proyectoandroid;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.iceteck.silicompressorr.FileUtils;
import com.iceteck.silicompressorr.SiliCompressor;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;


public class MensajesActivity extends FragmentActivity {

    private final static int REQUEST_PERMISSION = 1001;
    private final static int REQUEST_CAMERA = 1002;
    private final static String[] PERMISSION_REQUIRED =
            new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE" };
    private String pathPhotoCompressed;

    private String token;
    private int id;
    private String username;
    private EditText mensajeLayoutTxt;
    private ServicioWeb servicio;
    private static final int PHOTO_SEND = 1;
    private static final String CHANNEL_ID= "PUSHER_MSG";
    RecyclerView rv;
    Adapter adapter;
    List<Data> mensajes;
    private ImageButton btnImagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);
        Bundle params = getIntent().getExtras();
        token= params.getString("token");
        String tokenB1 = "Bearer " + token;
        id= params.getInt("id");
        username= params.getString("username");
        btnImagen = (ImageButton) findViewById(R.id.botonGaleria);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicio = retrofit.create(ServicioWeb.class);
        //////////////
        //////////////
        createChannel();
        PusherOptions options = new PusherOptions();
        options.setCluster("us2");
        Pusher pusher = new Pusher("46e8ded9439a0fef8cbc",options);
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                Log.d("PUSHER","Estado actual"+change.getCurrentState().name()+ " Estado" +
                        " previo " + change.getPreviousState().name());


            }

            @Override
            public void onError(String message, String code, Exception e) {
                Log.d("PUSHER", "Error Pusher\n"
                        + "Mensaje " + message + "\n"
                        + "Codigo " + code + "\n"
                        + "e " + e + "\n"
                );
            }
        }, ConnectionState.ALL);
        Channel channel = pusher.subscribe("my-channel");
        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                Log.d("PUSHER", "Nuevo Mensaje " + event.toString());
                JSONObject jsonObject = null;
                NotificationCompat.Builder nBuilder =
                        new NotificationCompat.Builder(MensajesActivity.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_chart_notification)
                            .setContentTitle("Notifiacion")
                            .setContentText("Mensaje")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);
                RequestBody usernameRb =RequestBody.create(MediaType.parse("multipart/form-data"), username);
                RequestBody idRb =RequestBody.create(MediaType.parse("multipart/form-data"), id+"");
                Call<RespuestaWSLastM> call = servicio.mGet(idRb,usernameRb,tokenB1);
                call.enqueue(new Callback<RespuestaWSLastM>() {
                    @Override
                    public void onResponse(Call<RespuestaWSLastM> call, Response<RespuestaWSLastM> response) {
                        if(response.isSuccessful()){
                            Data data = new Data();
                            rv = findViewById(R.id.containerF2);
                            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            mensajes=response.body().getData();
                            adapter=new Adapter(mensajes,getApplicationContext());
                            adapter.addMensaje(data);
                            rv.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaWSLastM> call, Throwable t) {

                    }
                });

                try{
                    jsonObject = new JSONObject(event.toString());
                    nBuilder=new NotificationCompat.Builder(MensajesActivity.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_chart_notification)
                            .setContentTitle("Nuevo mensaje de "+ jsonObject.getJSONObject("data").getJSONObject("message").getJSONObject("user").getString("username"))
                            .setContentText(jsonObject.getJSONObject("data").getJSONObject("message").getString("message"))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                NotificationManagerCompat notificationManagerCompat =
                        NotificationManagerCompat.from(MensajesActivity.this);
                notificationManagerCompat.notify(66,nBuilder.build());
            }
        });
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
                    adapter=new Adapter(mensajes,getApplicationContext());
                    rv.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<RespuestaWSLastM> call, Throwable t) {

            }
        });

    }
    private void createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID, "PUSHER",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
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
                                adapter=new Adapter(mensajes,getApplicationContext());
                                rv.setAdapter(adapter);
                                mensajeLayoutTxt.setText("");



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
    public void enviarFoto(View view){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/jpeg");
        i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(Intent.createChooser(i,"Seleciona una foto"),PHOTO_SEND);
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

    private File createFilePhoto() throws IOException {
        String timestamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String file_name = "JPEG_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(
                file_name,
                ".jpg",
                storageDir
        );
        return photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PHOTO_SEND && resultCode ==RESULT_OK){
            Uri u = data.getData();
            Bitmap bitmap=null;
            File archivoImagenC=null;
            try {
                archivoImagenC= createFilePhoto();
               bitmap = Picasso.get().load(u.getPath()).resize(50,50).get();
               OutputStream os = new BufferedOutputStream(new FileOutputStream(archivoImagenC));
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
                os.close();
            }catch (Exception e){

            }
            Log.d("Retrofit", archivoImagenC.getAbsolutePath());

            //File archivoImagenC = new File(SiliCompressor.with(getApplicationContext()).compress(u.getPath(), new File(this.getCacheDir(),"temp")));
            RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImagenC);
            MultipartBody.Part file = MultipartBody.Part.createFormData("image", archivoImagenC.getName(), imagen);

            Log.d("Retrofit", u.getPath());
            Log.d("Retrofit", username);
            Log.d("Retrofit", id+"");
            RequestBody usernameRb =RequestBody.create(MediaType.parse("multipart/form-data"), username);
            RequestBody idRb =RequestBody.create(MediaType.parse("multipart/form-data"), id+"");
            RequestBody msnRb =RequestBody.create(MediaType.parse("multipart/form-data"),
                    "");
            String tokenB = "Bearer " + token;
            Call<RespuestaWS> call = servicio.mSend(idRb,usernameRb,msnRb,file,
                    null,null,tokenB);
            call.enqueue(new Callback<RespuestaWS>() {
                @Override
                public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {

                    if(response.isSuccessful() && response != null && response.body() != null){
                        Log.d("Retrofit firt onResponse",response.body().getData().toString());
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
                                    mensajeLayoutTxt.setText("");
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
    }
}