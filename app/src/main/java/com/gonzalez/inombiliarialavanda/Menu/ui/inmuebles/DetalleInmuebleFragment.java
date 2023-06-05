package com.gonzalez.inombiliarialavanda.Menu.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.databinding.FragmentDetalleInmuebleBinding;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInmuebleBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        Bundle inmuebleRecuperado = getArguments();

        mViewModel.obtenerDatos((Inmueble) inmuebleRecuperado.getSerializable("inmueble"));
        mViewModel.getMutableInmueble().observe(getViewLifecycleOwner(),new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.tvDireccionDetalle.setText(inmueble.getDireccion());
                binding.tvAmbientesDet.setText(""+inmueble.getAmbientes());
                binding.tvPrecioDet.setText("$" + inmueble.getPrecio());
                binding.tvTipoDet.setText(inmueble.getTipo());
                binding.tvUsoDet.setText("Default");
                binding.tvDisponibleDet.setText(inmueble.isEstado()? "Disponible":"No disponible");
                Glide.with(getContext())
                        .load("http://www.secsanluis.com.ar/servicios/casa2.jpg")
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivFotoDetalle);
            }
        });
        binding.btDisponibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.cambiarDisponibilidad();
            }
        });

        return binding.getRoot();
    }


}