package com.example.proyectoandroid;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragment extends Fragment {
    RecyclerView rv;
    Adapter adapter;
    List<Data> mensajes;
    private TextView texto1;
    private FragmentViewModel fragmentViewModel;
    private ServicioWeb servicio;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String user_id;
    private String username;
    private String token;


    public TwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoFragment newInstance(String param1, String param2, String param3) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
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
        }
        fragmentViewModel = ViewModelProviders.of(getActivity()).get(FragmentViewModel.class);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicio = retrofit.create(ServicioWeb.class);
        Context actual=this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_two, container, false);
        user_id = mParam1;
        username = mParam2;
        token = "Bearer " + mParam3;
        RequestBody usernameRb =RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody idRb =RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        /////

        ////
        Call<RespuestaWSLastM> call = servicio.mGet(idRb,usernameRb,token);
        call.enqueue(new Callback<RespuestaWSLastM>() {
            @Override
            public void onResponse(Call<RespuestaWSLastM> call, Response<RespuestaWSLastM> response) {
                if(response.isSuccessful()){
                    Data data = new Data();
//                    mensajes.removeAll(mensajes);
                    rv = root.findViewById(R.id.containerF2);
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    mensajes=response.body().getData();
                    adapter=new Adapter(mensajes);
                    rv.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();

//                    if(response.body().getData().length >2) {
//                        for (int i = 0; i < response.body().getData().length; i++) {
//                            if(response.body().getData()[i]!=null) {
//                                Log.d("Retrofit", response.body().getMessage());
//                                Log.d("Retrofit", response.body().getData()[i].toString());
//                                data = response.body().getData()[i];
//                                String mensaje = response.body().getData()[i].getMessage();

//                                EditText editText = new EditText(getActivity());
//                                editText.setText("Hello");
//                                recycler.addView(editText);
//                            }
//                        }
                    }
//                }
            }

            @Override
            public void onFailure(Call<RespuestaWSLastM> call, Throwable t) {

            }
        });
        return root;
    }
}