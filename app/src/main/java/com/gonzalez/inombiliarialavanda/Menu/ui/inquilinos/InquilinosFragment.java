package com.gonzalez.inombiliarialavanda.Menu.ui.inquilinos;

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

import com.gonzalez.inombiliarialavanda.databinding.FragmentInquilinosBinding;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;

import java.util.ArrayList;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel inquilinosViewModel;
    private FragmentInquilinosBinding binding;

    public static InquilinosFragment newInstance() {
        return new InquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inquilinosViewModel = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentInquilinosBinding.inflate(inflater,container,false);

        GridLayoutManager grilla = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        binding.rvListaInquilinos.setLayoutManager(grilla);
        //observers
        inquilinosViewModel.getMutableInmueblesAlquilados().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {
                InquilinoAdapter adapter = new InquilinoAdapter(getContext(),inmuebles,getLayoutInflater());
                binding.rvListaInquilinos.setAdapter(adapter);
            }
        });
        inquilinosViewModel.getMutableAviso().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String aviso) { binding.tvAdvertencia.setText(aviso);}
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inquilinosViewModel = new ViewModelProvider(this).get(InquilinosViewModel.class);
        // TODO: Use the ViewModel
    }

}