package com.gonzalez.inombiliarialavanda.Menu.ui.inmuebles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;

import java.util.ArrayList;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Inmueble> inmuebles;
    public InmuebleAdapter(Context context,ArrayList<Inmueble> inmuebles, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        this.inmuebles = inmuebles;
    }
    @NonNull
    @Override
    public InmuebleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root= inflater.inflate(R.layout.item_inmueble,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    //por cada item hacer lo siguiente
        holder.tvDireccion.setText(inmuebles.get(position).getDireccion());
        holder.tvEstado.setText(inmuebles.get(position).isEstado() ? "Disponible": "No Disponible");
        holder.tvPrecio.setText("$" + inmuebles.get(position).getPrecio());
        Glide.with(context)
                .load(inmuebles.get(position).getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivFoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bndl = new Bundle();
                bndl.putSerializable("inmueble",inmuebles.get(position));
                Navigation.findNavController((Activity) context,R.id.nav_host_fragment_content_menu).navigate(R.id.nav_detalleInmueble,bndl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDireccion, tvEstado, tvPrecio;
        ImageView ivFoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            ivFoto = itemView.findViewById(R.id.ivFoto);
        }
    }
}
