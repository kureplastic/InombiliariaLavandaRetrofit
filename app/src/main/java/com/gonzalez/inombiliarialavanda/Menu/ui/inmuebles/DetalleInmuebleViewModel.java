package com.gonzalez.inombiliarialavanda.Menu.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gonzalez.inombiliarialavanda.modelo.Inmueble;
import com.gonzalez.inombiliarialavanda.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mutableInmueble;
    private Context context;
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        mutableInmueble = new MutableLiveData<>();
        context = application.getApplicationContext();
    }

    public LiveData<Inmueble> getMutableInmueble(){
        return mutableInmueble;
    }
    public void obtenerDatos(Inmueble inmuebleRecuperado){
        mutableInmueble.setValue(inmuebleRecuperado);
    }

    public void cambiarDisponibilidad(){
        ApiClientRetrofit.EndPointInmobiliaria end = ApiClientRetrofit.getEndpointInmobiliaria();
        Call<Inmueble> call = end.modificarDisponibilidad(context.getSharedPreferences("token.xml",0).getString("token",""), mutableInmueble.getValue().getId());
        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    mutableInmueble.setValue(response.body());
                    Toast.makeText(context, "Se actualizo el inmueble!", Toast.LENGTH_SHORT).show();
                    Log.d("salida: ", "Se obtuvo el inmueble: " + response.body().getDireccion());
                }
                else {
                    Log.d("salida: ", "No se pudo obtener el inmueble: " + response);
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("salida: ", "Error: " + t.getMessage());
            }
        });

    }
}