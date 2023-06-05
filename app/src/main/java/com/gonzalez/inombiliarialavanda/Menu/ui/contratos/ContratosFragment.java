package com.gonzalez.inombiliarialavanda.Menu.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gonzalez.inombiliarialavanda.Menu.ui.inquilinos.InquilinosViewModel;
import com.gonzalez.inombiliarialavanda.databinding.FragmentContratosBinding;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;

import java.util.ArrayList;

public class ContratosFragment extends Fragment {

    private ContratosViewModel contratosViewModel;
    private FragmentContratosBinding binding;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        contratosViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater,container,false);

        GridLayoutManager grilla = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        binding.rvListaInquilinos.setLayoutManager(grilla);
        //observers
        contratosViewModel.getMutableInmueblesContratados().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {
                //cambiar el adapter
                ContratoAdapter adapter = new ContratoAdapter(getContext(),inmuebles,getLayoutInflater());
                binding.rvListaInquilinos.setAdapter(adapter);
            }
        });
        contratosViewModel.getMutableAviso().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String aviso) { binding.tvAdvertencia.setText(aviso);}
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contratosViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);

    }

}