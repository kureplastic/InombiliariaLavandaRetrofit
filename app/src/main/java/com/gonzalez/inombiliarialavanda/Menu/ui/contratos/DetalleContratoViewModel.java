package com.gonzalez.inombiliarialavanda.Menu.ui.contratos;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.modelo.Contrato;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;
import com.gonzalez.inombiliarialavanda.modelo.Pago;
import com.gonzalez.inombiliarialavanda.request.ApiClient;
import com.gonzalez.inombiliarialavanda.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> mutableContrato;
    private Context context;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        mutableContrato = new MutableLiveData<>();
        context = application.getApplicationContext();
    }
    public LiveData<Contrato> getMutableContrato(){ return mutableContrato;}

    public void obtenerDatos(Inmueble inmuebleRecuperado){
        //usar retrofit
        ApiClientRetrofit.EndPointInmobiliaria end = ApiClientRetrofit.getEndpointInmobiliaria();
        Call<Contrato> call = end.obtenerContratoVigente(context.getSharedPreferences("token.xml",0).getString("token",""),inmuebleRecuperado.getId());
        call.enqueue(new Callback<Contrato>() {

            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mutableContrato.setValue(response.body());
                    }else{
                        mutableContrato.setValue(null);
                    }
                }else {
                    Log.d("salida contrato error", "No se pudo obtener contrato: " + response);
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("Salida error", t.getMessage());
            }
        });
        /*
        Contrato contrato = ApiClient.getApi().obtenerContratoVigente(inmuebleRecuperado);
        mutableContrato.setValue(contrato);

         */
    }

    public void VerPagos(){
        Bundle bndl = new Bundle();
        ArrayList<Pago> pagos = ApiClient.getApi().obtenerPagos(getMutableContrato().getValue());
        bndl.putSerializable("pagos",pagos);
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_menu).navigate(R.id.nav_detallePagos,bndl);
    }
}