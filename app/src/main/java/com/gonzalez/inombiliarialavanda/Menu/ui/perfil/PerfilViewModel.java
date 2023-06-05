package com.gonzalez.inombiliarialavanda.Menu.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gonzalez.inombiliarialavanda.modelo.Propietario;
import com.gonzalez.inombiliarialavanda.request.ApiClient;
import com.gonzalez.inombiliarialavanda.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Propietario> mutablePropietario;
    private MutableLiveData<String> mutableError;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        mutablePropietario = new MutableLiveData<>();
        mutableError = new MutableLiveData<>();
        llenarDatos();
    }
    public LiveData<Propietario> getMutablePropietario(){ return mutablePropietario;}
    public LiveData<String> getMutableError(){return mutableError;}
    public void llenarDatos(){
        ApiClientRetrofit.EndPointInmobiliaria end=ApiClientRetrofit.getEndpointInmobiliaria();
        Call<Propietario> call= end.obtenerPerfil(context.getSharedPreferences("token.xml",0).getString("token",""));
        call.enqueue(new Callback<Propietario>() {

            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mutablePropietario.postValue(response.body());
                }else{
                    Log.d("salida error: ","al traer el perfil se obtuvo:"  + response.body().toString());
                    mutableError.postValue("Error, al obtener el perfil");
                }
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                mutableError.postValue("Error, Call OnFailure: " + t.getMessage());
            }
        });
        //Propietario propietario = ApiClient.getApi().obtenerUsuarioActual();
        //mutablePropietario.setValue(propietario);
    }
    private boolean validarDatos(Propietario propietario){
        if(propietario.getNombre().equals("") ||
                propietario.getApellido().equals("") ||
                propietario.getDni() == null ||
                propietario.getTelefono().equals(""))
        {
            return false;
        }
        if (propietario.getClave().equals("")) {
            propietario.setClave("vacia");
        }
        return true;
    }

    public void editarPerfil(Propietario propietario){
        if(validarDatos(propietario)){
            // usar ApiClientRetrofit
            ApiClientRetrofit.EndPointInmobiliaria end=ApiClientRetrofit.getEndpointInmobiliaria();
            Call<Propietario> call= end.actualizarPerfil(context.getSharedPreferences("token.xml",0).getString("token",""),propietario.getDni(),propietario.getNombre(),propietario.getApellido(),propietario.getTelefono(),propietario.getEmail(), propietario.getClave());
            call.enqueue(new Callback<Propietario>() {

                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        mutablePropietario.postValue(response.body());
                    }else{
                        Log.d("salida error: ","al actualizar el perfil se obtuvo:"  + response);
                        mutableError.postValue("Error, al actualizar el perfil");
                    }
                }
                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    mutableError.postValue("Error, Call OnFailure: " + t.getMessage());
                }
            });
            //ApiClient api = ApiClient.getApi();
            //api.actualizarPerfil(propietario);
            Toast.makeText(context, "USUARIO ACTUALIZADO!", Toast.LENGTH_SHORT).show();
            mutableError.postValue("");
        }else {
            Toast.makeText(context, "ERROR AL ACTUALIZAR!", Toast.LENGTH_SHORT).show();
            mutableError.postValue("Error, corrobore que todos los campos esten completos");
        }
    }
}