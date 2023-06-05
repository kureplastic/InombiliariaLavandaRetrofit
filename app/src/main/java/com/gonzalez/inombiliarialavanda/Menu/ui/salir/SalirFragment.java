package com.gonzalez.inombiliarialavanda.Menu.ui.salir;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.modelo.Dialogos;

public class SalirFragment extends Fragment {

    private SalirViewModel mViewModel;

    public static SalirFragment newInstance() {
        return new SalirFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Dialogos d = new Dialogos();
        d.mostrarDialogoBotones(getContext());
        return inflater.inflate(R.layout.fragment_salir, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SalirViewModel.class);
        // TODO: Use the ViewModel
    }

}