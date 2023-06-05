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

import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.databinding.FragmentDetallePagosBinding;
import com.gonzalez.inombiliarialavanda.modelo.Contrato;
import com.gonzalez.inombiliarialavanda.modelo.Pago;

import java.util.ArrayList;
import java.util.List;

public class DetallePagosFragment extends Fragment {

    private DetallePagosViewModel mViewModel;
    private FragmentDetallePagosBinding binding;

    public static DetallePagosFragment newInstance() {
        return new DetallePagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetallePagosViewModel.class);
        binding = FragmentDetallePagosBinding.inflate(inflater, container, false);

        Bundle contratoRecuperado = getArguments();
        mViewModel.obtenerDatos((Contrato) contratoRecuperado.getSerializable("contrato"));

        GridLayoutManager grilla = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        binding.rvListaPagos.setLayoutManager(grilla);

        mViewModel.getMutablePagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter adapter = new PagoAdapter(getContext(),pagos,getLayoutInflater());
                binding.rvListaPagos.setAdapter(adapter);
            }
        });
        mViewModel.getMutableAviso().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String aviso) { binding.tvAviso.setText(aviso);}
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetallePagosViewModel.class);
    }

}