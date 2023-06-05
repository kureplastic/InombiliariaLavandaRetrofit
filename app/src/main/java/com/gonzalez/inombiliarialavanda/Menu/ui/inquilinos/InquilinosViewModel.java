package com.gonzalez.inombiliarialavanda.Menu.ui.inquilinos;

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

public class InquilinosViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Inmueble>> mutableInmueblesAlquilados;
    private MutableLiveData<String> mutableAviso;
    private Context context;
    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        mutableAviso = new MutableLiveData<>();
        mutableInmueblesAlquilados = new MutableLiveData<>();
        context = application.getApplicationContext();
        cargarDatos();
    }

    public LiveData<String> getMutableAviso(){ return mutableAviso; }
    public LiveData<ArrayList<Inmueble>> getMutableInmueblesAlquilados(){ return mutableInmueblesAlquilados; }
    private void cargarDatos(){
        //usar retrofit
        ApiClientRetrofit.EndPointInmobiliaria end = ApiClientRetrofit.getEndpointInmobiliaria();
        Call<List<Inmueble>> call = end.obtenerInmueblesAlquilados(context.getSharedPreferences("token.xml",0).getString("token",""));
        call.enqueue(new Callback<List<Inmueble>>() {

            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<Inmueble> inmueblesObtenidos = (ArrayList<Inmueble>) response.body();
                        if (!inmueblesObtenidos.isEmpty()) {
                            mutableAviso.setValue("Sus inmuebles alquilados:");
                            mutableInmueblesAlquilados.setValue(inmueblesObtenidos);
                        } else {
                            mutableAviso.setValue("No dispone de inmuebles alquilados");
                        }
                    } else {
                        Log.d("salida inquilino error", "No se pudo obtener inmuebles alquilados: " + response);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("salida  error", t.getMessage());
            }
        });

        /*
        ArrayList<Inmueble> inmueblesAlquilados = ApiClient.getApi().obtenerPropiedadesAlquiladas();
        if(inmueblesAlquilados.isEmpty()){
            mutableAviso.setValue("No dispone de inmuebles alquilados");
        }else{
            mutableAviso.setValue("Sus inmuebles alquilados:");
            mutableInmueblesAlquilados.setValue(inmueblesAlquilados);
        }
        */
    }

}