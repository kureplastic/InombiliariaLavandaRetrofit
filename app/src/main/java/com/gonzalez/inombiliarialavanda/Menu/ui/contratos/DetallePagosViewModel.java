package com.gonzalez.inombiliarialavanda.Menu.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gonzalez.inombiliarialavanda.modelo.Contrato;
import com.gonzalez.inombiliarialavanda.modelo.Pago;
import com.gonzalez.inombiliarialavanda.request.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> mutablePagos;
    private MutableLiveData<String> mutableAviso;
    private Context context;

    public DetallePagosViewModel(@NonNull Application application) {
        super(application);
        mutablePagos = new MutableLiveData<>();
        mutableAviso = new MutableLiveData<>();
        context = application.getApplicationContext();
    }
    public LiveData<List<Pago>> getMutablePagos(){ return mutablePagos;}
    public LiveData<String> getMutableAviso(){ return mutableAviso;}

    public void obtenerDatos(Contrato contrato){
        //usar retrofit
        ApiClientRetrofit.EndPointInmobiliaria end = ApiClientRetrofit.getEndpointInmobiliaria();
        Call<List<Pago>> call = end.obtenerPagos(context.getSharedPreferences("token.xml",0).getString("token",""),contrato.getId());
        call.enqueue(new Callback<List<Pago>>() {

            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<Pago> pagosObtenidos = response.body();
                        Log.d("salida pagos", "Pagos obtenidos: " + response);
                        if(pagosObtenidos.isEmpty()){
                            mutableAviso.setValue("No hay pagos registrados para este contrato");
                        }else {
                            mutableAviso.setValue("Pagos registrados: "+ pagosObtenidos.size());
                            mutablePagos.setValue(pagosObtenidos);
                        }

                    }
                }else{
                    Log.d("salida pagos error", "No se pudo obtener pagos: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("salida error", t.getMessage());
            }
        });
        /*
        ArrayList<Pago> pagos = ApiClient.getApi().obtenerPagos(contrato);
        if(pagos.size()>0){
            mutablePagos.setValue(pagos);
            mutableAviso.setValue("Pagos registrados: "+ pagos.size());
        }else{
            mutableAviso.setValue("No hay pagos registrados");
        }

         */
    }
}