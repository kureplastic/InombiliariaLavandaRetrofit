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
import com.gonzalez.inombiliarialavanda.modelo.Inquilino;
import com.gonzalez.inombiliarialavanda.request.ApiClient;
import com.gonzalez.inombiliarialavanda.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> mutableInquilino;
    private Context context;
    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
        mutableInquilino = new MutableLiveData<>();
        context = application.getApplicationContext();
    }
    public LiveData<Inquilino> getMutableInquilino(){ return mutableInquilino; }

    public void obtenerDatos(Inmueble inmuebleRecuperado){
        //Usar retrofit
        ApiClientRetrofit.EndPointInmobiliaria end = ApiClientRetrofit.getEndpointInmobiliaria();
        Call<Inquilino> call = end.obtenerInquilino(context.getSharedPreferences("token.xml",0).getString("token",""),inmuebleRecuperado.getId());
        call.enqueue(new Callback<Inquilino>() {

            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Inquilino inquilinoRecuperado = response.body();
                        if (inquilinoRecuperado.getLugarDeTrabajo() == null){inquilinoRecuperado.setLugarDeTrabajo("N/A");}
                        mutableInquilino.setValue(inquilinoRecuperado);
                    }
                }else{
                    Log.d("salida inquilino error", "No se pudo obtener inquilino: " + response);
                }
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {
                Log.d("salida error", t.getMessage());
            }
        });
        /*
        Inquilino inquilinoRecuperado = ApiClient.getApi().obtenerInquilino(inmuebleRecuperado);
        mutableInquilino.setValue(inquilinoRecuperado);
        */
    }
}