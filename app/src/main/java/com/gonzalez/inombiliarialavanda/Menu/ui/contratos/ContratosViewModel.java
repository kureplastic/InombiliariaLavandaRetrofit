package com.gonzalez.inombiliarialavanda.Menu.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gonzalez.inombiliarialavanda.modelo.Inmueble;
import com.gonzalez.inombiliarialavanda.request.ApiClient;
import com.gonzalez.inombiliarialavanda.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Inmueble>> mutableInmueblesContratados;
    private MutableLiveData<String> mutableAviso;
    private Context context;
    public ContratosViewModel(@NonNull Application application) {
        super(application);
        mutableAviso = new MutableLiveData<>();
        mutableInmueblesContratados = new MutableLiveData<>();
        context = application.getApplicationContext();
        cargarDatos();
    }

    public LiveData<String> getMutableAviso(){ return mutableAviso; }
    public LiveData<ArrayList<Inmueble>> getMutableInmueblesContratados(){ return mutableInmueblesContratados; }
    private void cargarDatos(){
        //usar Retrofit
        ApiClientRetrofit.EndPointInmobiliaria end = ApiClientRetrofit.getEndpointInmobiliaria();
        Call<List<Inmueble>> call = end.obtenerInmueblesAlquilados(context.getSharedPreferences("token.xml",0).getString("token",""));
        call.enqueue(new Callback<List<Inmueble>>() {

            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //llenar campos de inmuebles
                        ArrayList<Inmueble> inmueblesObtenidos = (ArrayList<Inmueble>) response.body();
                        if (!inmueblesObtenidos.isEmpty()) {
                            mutableAviso.setValue("Sus inmuebles con contrato actuales:");
                            mutableInmueblesContratados.setValue(inmueblesObtenidos);
                        } else {
                            mutableAviso.setValue("No dispone de inmuebles con contrato actualmente");
                        }
                    } else {
                        Log.d("salida contrato error", "No se pudo obtener inmuebles alquilados: " + response);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("salida  error", t.getMessage());
            }
        });

        /*
        ArrayList<Inmueble> inmueblesContratados = ApiClient.getApi().obtenerPropiedadesAlquiladas();
        if(inmueblesContratados.isEmpty()){
            mutableAviso.setValue("No dispone de inmuebles con contrato");
        }else{
            mutableAviso.setValue("Sus inmuebles contratados:");
            mutableInmueblesContratados.setValue(inmueblesContratados);
        }
        */
    }
}