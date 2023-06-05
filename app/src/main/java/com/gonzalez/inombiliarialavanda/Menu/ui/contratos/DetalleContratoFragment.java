package com.gonzalez.inombiliarialavanda.Menu.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.databinding.FragmentDetalleContratoBinding;
import com.gonzalez.inombiliarialavanda.modelo.Contrato;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel detalleContratoViewModel;
    private FragmentDetalleContratoBinding binding;
    private Contrato contratoVer;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        detalleContratoViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater,container,false);
        Bundle inmuebleRecuperado = getArguments();
        detalleContratoViewModel.obtenerDatos( (Inmueble) inmuebleRecuperado.getSerializable("inmueble"));
        detalleContratoViewModel.getMutableContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {

            @Override
            public void onChanged(Contrato contrato) {
                //llenar campos
                binding.tvInicioContrato.setText(contrato.getFechaInicio());
                binding.tvFinContrato.setText(contrato.getFechaFin());
                binding.tvInmueble.setText(contrato.getInmueble().getDireccion());
                binding.tvInquilino.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
                binding.tvMontoMensual.setText("$" + contrato.getMontoMensual());
                contratoVer = contrato;
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bndl = new Bundle();
                bndl.putSerializable("contrato",contratoVer);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_menu).navigate(R.id.nav_detallePagos,bndl);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detalleContratoViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}