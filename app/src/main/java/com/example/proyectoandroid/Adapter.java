package com.example.proyectoandroid;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MensajesViewHolder> {
    List<Data> mensajes;

    public Adapter (List<Data> mensajes){

        this.mensajes=mensajes;
    }

    @NonNull
    @Override
    public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler,parent, false);
        MensajesViewHolder holder =new MensajesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {
        Data mensaje= mensajes.get(position);
        holder.textViewNombre.setText(mensaje.getUser().getUsername());
        holder.textViewMensaje.setText(mensaje.getMessage());
//        Picasso.get().load(mensaje.getUser().getUser_thumbnail().toString()+"").into(holder.imageViewUsuario);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    public static class MensajesViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNombre, textViewMensaje;
        ImageView imageViewUsuario;


        public MensajesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre=itemView.findViewById(R.id.nombre);
            textViewMensaje=itemView.findViewById(R.id.mensaje);
//            imageViewUsuario=itemView.findViewById(R.id.imagen);
        }
    }
}
