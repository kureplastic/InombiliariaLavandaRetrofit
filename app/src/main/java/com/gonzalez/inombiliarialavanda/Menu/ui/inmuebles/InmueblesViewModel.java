package com.gonzalez.inombiliarialavanda.Menu.ui.inmuebles;

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

public class InmueblesViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Inmueble>> mutableInmuebles;
    private MutableLiveData<String> mutableAviso;
    private Context context;

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        mutableInmuebles = new MutableLiveData<>();
        mutableAviso = new MutableLiveData<>();
        context = application.getApplicationContext();
        cargarDatos();
    }

    public LiveData<ArrayList<Inmueble>> getMutableInmuebles(){
        return mutableInmuebles;
    }
    public LiveData<String> getMutableAviso(){
        return mutableAviso;
    }

    public void cargarDatos(){
        //aca Usar ApiClientRetrofit
        ApiClientRetrofit.EndPointInmobiliaria end = ApiClientRetrofit.getEndpointInmobiliaria();
        Call<List<Inmueble>> call = end.obtenerInmueblesPorPropietario(context.getSharedPreferences("token.xml",0).getString("token",""));
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //llenar campos de inmuebles
                        mutableAviso.setValue("Sus inmuebles registrados:");
                        ArrayList<Inmueble> inmueblesObtenidos = (ArrayList<Inmueble>) response.body();
                        for(Inmueble inmueble: inmueblesObtenidos) {
                            Log.d("salida Inmuebles: ", inmueble.getDireccion());
                            // recordar que los inmuebles aca no tienen foto ni uso
                            //por lo que hay que implementar una forma para llenar la foto con alguna imagen de "no tiene foto"
                            inmueble.setImagen("http://www.secsanluis.com.ar/servicios/casa1.jpg");
                            inmueble.setUso("particular");
                        }
                        mutableInmuebles.setValue(inmueblesObtenidos);
                    }else{
                        mutableAviso.setValue("No dispone de inmuebles registrados");
                        // error no habia nada en el body
                        Log.d("Salida Inmueble:","error no habia nada en el body");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("Salida Inmueble error:",t.getMessage());
            }
        });
        /*
        ArrayList<Inmueble> inmuebles = ApiClient.getApi().obtnerPropiedades();
        if(inmuebles.isEmpty()){
            mutableAviso.setValue("No dispone de inmuebles registrados");
        }
        else {
            mutableAviso.setValue("Sus inmuebles registrados:");
            mutableInmuebles.setValue(inmuebles);
        }
        */
    }
}