package com.gonzalez.inombiliarialavanda.Menu.ui.perfil;

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


import com.gonzalez.inombiliarialavanda.databinding.FragmentPerfilBinding;
import com.gonzalez.inombiliarialavanda.modelo.Propietario;
import com.gonzalez.inombiliarialavanda.request.ApiClient;


public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        perfilViewModel.getMutablePropietario().observe(getActivity(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etDNI.setText(propietario.getDni().toString());
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etPass.setText("");
                binding.etEmail.setText(propietario.getEmail());
                binding.etTelefono.setText(propietario.getTelefono());
            }
        });

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btEditar.setText("Guardar");
                binding.etNombre.setEnabled(true);
                binding.etApellido.setEnabled(true);
                binding.etEmail.setEnabled(false);
                binding.etPass.setEnabled(true);
                binding.etTelefono.setEnabled(true);
                binding.etDNI.setEnabled(true);
                binding.btEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Propietario propietarioEditado = new Propietario();
                        propietarioEditado.setEmail(binding.etEmail.getText().toString());
                        propietarioEditado.setApellido(binding.etApellido.getText().toString());
                        propietarioEditado.setNombre(binding.etNombre.getText().toString());
                        propietarioEditado.setTelefono(binding.etTelefono.getText().toString());
                        propietarioEditado.setClave(binding.etPass.getText().toString());
                        propietarioEditado.setDni(Long.parseLong(binding.etDNI.getText().toString()));
                        perfilViewModel.editarPerfil(propietarioEditado);
                    }
                });
            }
        });

        perfilViewModel.getMutableError().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                binding.tvError.setText(error);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}