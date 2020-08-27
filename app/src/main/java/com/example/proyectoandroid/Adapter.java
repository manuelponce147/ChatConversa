package com.example.proyectoandroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MensajesViewHolder> {
    static List<Data> mensajes;
    private AdapterView.OnItemClickListener mListener;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MensajesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewNombre, textViewMensaje, textViewHoraMensaje;
        ImageView imageViewFotoPerfilMensaje, imageViewMensajeFoto,imageButtonPerfilMensaje, imageButtonFotoMensaje;
//        ImageButton ;
        Context context;
        List<Data> mensajes;


        public MensajesViewHolder(@NonNull View itemView,Context context, List<Data> mensajes, final OnItemClickListener listener) {
            super(itemView);
            textViewNombre =itemView.findViewById(R.id.nombre);
            textViewMensaje=itemView.findViewById(R.id.mensaje);
            textViewHoraMensaje=itemView.findViewById(R.id.horaMensaje);

            imageButtonPerfilMensaje =itemView.findViewById(R.id.thumb_image);
            imageViewFotoPerfilMensaje=itemView.findViewById(R.id.expanded_image);

            imageButtonFotoMensaje =itemView.findViewById(R.id.mensajeFoto);
            imageViewMensajeFoto=itemView.findViewById(R.id.expanded_message);

            imageButtonPerfilMensaje.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            this.context=context;
            this.mensajes =mensajes;

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){


            }
        }

     }


    public Adapter (List<Data> mensajes, Context context){
        this.context=context;
        this.mensajes=mensajes;
    }

    public void addMensaje(Data data){
        mensajes.add(data);
        notifyItemInserted(mensajes.size());
    }

    @NonNull
    @Override
    public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler,parent, false);
        MensajesViewHolder holder =new MensajesViewHolder(v,context,mensajes, (OnItemClickListener) mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {
        Data mensaje= mensajes.get(position);
        if(mensaje.getUser().getUsername().length()>=5){
            holder.textViewNombre.setText(mensaje.getUser().getUsername());
        }
        holder.textViewMensaje.setText(mensaje.getMessage());
        holder.textViewHoraMensaje.setText(mensaje.getDate());


        if (!mensaje.getUser().getUser_image().equals("")){
            System.out.println("Path de la foto de "+mensaje.getUser().getUsername()+" " +mensaje.getUser().getUser_image());
            Glide.with(context)
                    .load(mensaje.getUser().getUser_thumbnail())
                    .into(holder.imageButtonPerfilMensaje);
            Glide.with(context)

                    .load(mensaje.getUser().getUser_image())
                    .into(holder.imageViewFotoPerfilMensaje);
        } else {
//            holder.imageButtonPerfilMensaje.setVisibility(View.INVISIBLE);
//            holder.imageViewFotoPerfilMensaje.setVisibility(View.INVISIBLE);
        }

        //Llenado mensaje, thumb y full
        if (mensaje.getThumbnail()!=null){
            holder.imageButtonFotoMensaje.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(mensaje.getThumbnail())
                    .apply(new RequestOptions().override(500, 500))
                    .into(holder.imageButtonFotoMensaje);
            Glide.with(context)
                    .load(mensaje.getImage())
                    .into(holder.imageViewMensajeFoto);
        }else {

//            holder.imageButtonFotoMensaje.setVisibility(View.GONE);
//            holder.imageViewMensajeFoto.setVisibility(View.GONE);
        }
        ;



        // Funciones on click
        holder.imageButtonPerfilMensaje.setOnClickListener(view -> {
            Log.d("TAG", "Click imagenPerfil: " + mensaje.getUser().getUsername());
            if(holder.imageViewFotoPerfilMensaje.getVisibility()==View.VISIBLE){
                holder.imageViewFotoPerfilMensaje.setVisibility(View.GONE);
            } else {

                holder.imageViewFotoPerfilMensaje.setVisibility(View.VISIBLE);
            }

        });

        holder.imageButtonFotoMensaje.setOnClickListener(view -> {
            Log.d("TAG", "Click imagenMensaje: " + mensajes.get(position));
            if(holder.imageViewMensajeFoto.getVisibility()==View.VISIBLE){
                holder.imageViewMensajeFoto.setVisibility(View.GONE);
            } else {

                holder.imageViewMensajeFoto.setVisibility(View.VISIBLE);
            }

        });
    }


    @Override
    public int getItemCount() {
        return mensajes.size();
    }



}
