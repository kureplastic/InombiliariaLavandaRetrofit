package com.gonzalez.inombiliarialavanda.Menu.ui.inmuebles;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.gonzalez.inombiliarialavanda.databinding.FragmentInmueblesBinding;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;

import java.util.ArrayList;


public class InmueblesFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private InmueblesViewModel inmueblesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inmueblesViewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);

        binding = FragmentInmueblesBinding.inflate(inflater, container, false);

        GridLayoutManager grilla = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        binding.rvInmuebles.setLayoutManager(grilla);

        inmueblesViewModel.getMutableInmuebles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {
                InmuebleAdapter adapter = new InmuebleAdapter(getContext(),inmuebles,getLayoutInflater());
                binding.rvInmuebles.setAdapter(adapter);
            }
        });
        inmueblesViewModel.getMutableAviso().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String aviso) {
                binding.tvAviso.setText(aviso);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        inmueblesViewModel.cargarDatos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}