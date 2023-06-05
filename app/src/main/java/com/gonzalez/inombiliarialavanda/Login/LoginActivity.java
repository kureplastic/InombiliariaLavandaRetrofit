package com.gonzalez.inombiliarialavanda.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;


import com.gonzalez.inombiliarialavanda.databinding.ActivityLoginBinding;
import com.gonzalez.inombiliarialavanda.modelo.DeteccionMovimiento;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel viewModel;
    private DeteccionMovimiento deteccionMovimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);

        deteccionMovimiento = new DeteccionMovimiento(this);
        deteccionMovimiento.startListening();

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etUser.getText().toString();
                String pass = binding.etPassword.getText().toString();
                viewModel.login(email,pass);
            }
        });

        viewModel.getMutableError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                binding.tvError.setText(error);
                binding.etUser.setError(error);
                binding.etPassword.setError(error);
            }
        });
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deteccionMovimiento.stopListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        deteccionMovimiento.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        deteccionMovimiento.startListening();
    }

}