package com.gonzalez.inombiliarialavanda.Menu;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gonzalez.inombiliarialavanda.R;
import com.gonzalez.inombiliarialavanda.modelo.Propietario;
import com.gonzalez.inombiliarialavanda.request.ApiClient;
import com.gonzalez.inombiliarialavanda.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> mutableUsuario;;
    private Context context;
    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
        mutableUsuario = new MutableLiveData<>();
        context = application.getApplicationContext();
    }
    public LiveData<Propietario> getMutableUsuario() {
        return mutableUsuario;
    }

    public void obtenerUsuario(){
        ApiClientRetrofit.EndPointInmobiliaria end=ApiClientRetrofit.getEndpointInmobiliaria();
        Call<Propietario> call= end.obtenerPerfil(context.getSharedPreferences("token.xml",0).getString("token",""));
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    Propietario usuario = response.body();
                    if(usuario.getAvatar() == 0){
                        usuario.setAvatar(R.drawable.juan);
                    }
                    mutableUsuario.postValue(usuario);
                }else{
                    Log.d("salida error: ","al traer el perfil se obtuvo:"  + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {

            }
        });
        //mutableUsuario.setValue(ApiClient.getApi().obtenerUsuarioActual());
    }

}
