package com.example.proyectoandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private EditText mensajeLayout;
    private ImageButton enviarMensaje;
    private ServicioWeb servicio;
    private String username;
    private String user_id;
    private String token;
    private String pathPhoto;



    public OneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OneFragment newInstance(String param1, String param2,String param3,String param4) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicio = retrofit.create(ServicioWeb.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one
                , container, false);
        mensajeLayout = (EditText) rootView.findViewById(R.id.cuadradoMensaje);
        enviarMensaje = (ImageButton) rootView.findViewById(R.id.enviarBtn);
        enviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Retrofit", mensajeLayout.getText().toString());
                user_id = mParam1;
                username = mParam2;
                token = "Bearer " + mParam3;
                pathPhoto = mParam4;
                RequestBody usernameRb =RequestBody.create(MediaType.parse("multipart/form-data"), username);
                RequestBody idRb =RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
                //Log.d("Retrofit", pathPhoto);
                //File archivoImagen = new File(pathPhoto);
                //RequestBody imagen = RequestBody.create(MediaType.parse("multipart/form-data"), archivoImagen);
               // MultipartBody.Part file = MultipartBody.Part.createFormData("user_image", "imagen", imagen);
                RequestBody msnRb =RequestBody.create(MediaType.parse("multipart/form-data"),
                        mensajeLayout.getText().toString());
                Call<RespuestaWS> call = servicio.mSend(idRb,usernameRb,msnRb,null,
                        null,null,token);
                call.enqueue(new Callback<RespuestaWS>() {
                    @Override
                    public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                        Log.d("Retrofit",response.toString());
                        if(response.isSuccessful() && response != null && response.body() != null){


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
        });
        return  rootView;
    }

}