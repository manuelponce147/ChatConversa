package com.example.proyectoandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MensajesViewHolder> {

    public static class MensajesViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNombre, textViewMensaje, textViewHoraMensaje, textViewFechaMensaje;
        ImageView imageViewFotoPerfilMensaje, imageViewMensajeFoto;



        public MensajesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre =itemView.findViewById(R.id.nombre);
            textViewMensaje=itemView.findViewById(R.id.mensaje);
            textViewHoraMensaje=itemView.findViewById(R.id.horaMensaje);
            textViewFechaMensaje=itemView.findViewById(R.id.fechaMensaje);
            imageViewFotoPerfilMensaje=itemView.findViewById(R.id.fotoPerfilMensaje);
            imageViewMensajeFoto=itemView.findViewById(R.id.mensajeFoto);

        }
    }
    List<Data> mensajes;

    public Adapter (List<Data> mensajes){

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
        MensajesViewHolder holder =new MensajesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {
        Data mensaje= mensajes.get(position);
        holder.textViewNombre.setText(mensaje.getUsername());
        holder.textViewMensaje.setText(mensaje.getMessage());
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }


}
