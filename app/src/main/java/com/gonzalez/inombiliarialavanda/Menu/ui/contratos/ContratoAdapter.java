package com.gonzalez.inombiliarialavanda.Menu.ui.contratos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.modelo.Contrato;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;
import com.gonzalez.inombiliarialavanda.request.ApiClient;

import java.util.ArrayList;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Inmueble> inmueblesAlquilados;
    public ContratoAdapter(Context context,ArrayList<Inmueble> inmueblesAlquilados, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        this.inmueblesAlquilados = inmueblesAlquilados;
    }
    @NonNull
    @Override
    public ContratoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root= inflater.inflate(R.layout.item_inquilino,parent,false);
        return new ContratoAdapter.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //por cada item hacer lo siguiente
        holder.tvDireccion.setText(inmueblesAlquilados.get(position).getDireccion());
        Glide.with(context)
                .load("http://www.secsanluis.com.ar/servicios/casa1.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivFoto);
        holder.btVerInquilino.setText("Ver Contrato");
        holder.btVerInquilino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bndl = new Bundle();
                Inmueble inmueble = inmueblesAlquilados.get(position);
                bndl.putSerializable("inmueble",inmueble);
                Navigation.findNavController((Activity) context,R.id.nav_host_fragment_content_menu).navigate(R.id.nav_detalleContrato,bndl);

            }
        });
    }

    @Override
    public int getItemCount() {
        return inmueblesAlquilados.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDireccion;
        ImageView ivFoto;
        Button btVerInquilino;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            btVerInquilino = itemView.findViewById(R.id.btVerInquilino);
        }
    }
}
