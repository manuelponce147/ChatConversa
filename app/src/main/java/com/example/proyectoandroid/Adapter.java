package com.example.proyectoandroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MensajesViewHolder> {
    static List<Data> mensajes;
    Context context;

    public static class MensajesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewNombre, textViewMensaje, textViewHoraMensaje;
        ImageView imageViewFotoPerfilMensaje, imageViewMensajeFoto;
        ImageButton imageButtonPerfilMensaje, imageButtonMensaje;
        Context context;
        List<Data> mensajes;

        public MensajesViewHolder(@NonNull View itemView,Context context, List<Data> mensajes) {
            super(itemView);
            textViewNombre =itemView.findViewById(R.id.nombre);
            textViewMensaje=itemView.findViewById(R.id.mensaje);
            textViewHoraMensaje=itemView.findViewById(R.id.horaMensaje);

            imageButtonPerfilMensaje =itemView.findViewById(R.id.thumb_image);
            imageViewFotoPerfilMensaje=itemView.findViewById(R.id.expanded_image);

            imageButtonMensaje =itemView.findViewById(R.id.mensajeFoto);
            imageViewMensajeFoto=itemView.findViewById(R.id.expanded_message);

            imageButtonPerfilMensaje.setOnClickListener(this);
            itemView.setOnClickListener(this);
            this.context=context;
            this.mensajes =mensajes;

        }


        @Override
        public void onClick(View v) {
//            System.out.println(mensajes.get(getAdapterPosition()).toString());
//            Intent intent=new Intent(context,MuestraImagenGrande.class);
//            Bundle parametros = new Bundle();
//            parametros.putString("user_image",mensajes.get(getAdapterPosition()).getUser().getUser_image());
//            context.startActivity(intent);
//            Toast.makeText(context, mensajes.get(getAdapterPosition()).getUser().getUser_image(), Toast.LENGTH_SHORT).show();


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
        MensajesViewHolder holder =new MensajesViewHolder(v,context,mensajes);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {
        Data mensaje= mensajes.get(position);
        holder.textViewNombre.setText(mensaje.getUser().getUsername());
        holder.textViewMensaje.setText(mensaje.getMessage());
        holder.textViewHoraMensaje.setText(mensaje.getDate());
        if (mensaje.getThumbnail()!=null){
            Glide.with(context)
                    .load(mensaje.getThumbnail())
                    .into(holder.imageButtonPerfilMensaje);
            Glide.with(context)
                    .load(mensaje.getImage())
                    .into(holder.imageViewMensajeFoto);
        }
        if (mensaje.getUser().getUser_image()!=null||!mensaje.getUser().getUser_image().equals("")){
            Glide.with(context)
                    .load(mensaje.getUser().getUser_thumbnail())
                    .into(holder.imageButtonPerfilMensaje);
            Glide.with(context)
                    .load(mensaje.getUser().getUser_image())
                    .into(holder.imageViewFotoPerfilMensaje);
        };



        holder.imageButtonPerfilMensaje.setOnClickListener(view -> {
            Log.d("TAG", "Click imagenPerfil: " + mensajes.get(position));
            if(holder.imageViewFotoPerfilMensaje.getVisibility()==View.VISIBLE){
                holder.imageViewFotoPerfilMensaje.setVisibility(View.GONE);
            } else {

                holder.imageViewFotoPerfilMensaje.setVisibility(View.VISIBLE);
            }

        });

        holder.imageButtonMensaje.setOnClickListener(view -> {
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
