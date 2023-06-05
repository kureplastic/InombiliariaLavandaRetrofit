package com.gonzalez.inombiliarialavanda.Menu.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.databinding.FragmentDetalleInquilinoBinding;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;
import com.gonzalez.inombiliarialavanda.modelo.Inquilino;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel mViewModel;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        binding = FragmentDetalleInquilinoBinding.inflate(inflater,container,false);
        Bundle inmuebleRecuperado = getArguments();

        mViewModel.obtenerDatos( (Inmueble) inmuebleRecuperado.getSerializable("inmueble"));
        mViewModel.getMutableInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvDni.setText("" + inquilino.getDNI());
                binding.tvNombre.setText(inquilino.getNombre());
                binding.tvApellido.setText(inquilino.getApellido());
                binding.tvEmail.setText(inquilino.getEmail());
                binding.tvTelefono.setText(inquilino.getTelefono());
                binding.tvLugarTrabajo.setText(inquilino.getLugarDeTrabajo());
                binding.tvGarante.setText(inquilino.getNombreGarante());
                binding.tvGaranteTelefono.setText(inquilino.getTelefonoGarante());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}