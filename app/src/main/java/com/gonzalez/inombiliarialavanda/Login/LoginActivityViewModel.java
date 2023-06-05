package com.gonzalez.inombiliarialavanda.Login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gonzalez.inombiliarialavanda.Menu.MenuActivity;
import com.gonzalez.inombiliarialavanda.modelo.Propietario;
import com.gonzalez.inombiliarialavanda.request.ApiClient;
import com.gonzalez.inombiliarialavanda.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mutableError;
    //private ApiClient api;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        mutableError = new MutableLiveData<>();
        //api = ApiClient.getApi();
    }
    public MutableLiveData<String> getMutableError() {
        return mutableError;
    }

    public void login(String mail, String pass){
        Propietario usuario=new Propietario(mail,pass);
        ApiClientRetrofit.EndPointInmobiliaria end=ApiClientRetrofit.getEndpointInmobiliaria();
        Call<String> call= end.login(usuario);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                    if(response.body()!=null){
                        SharedPreferences sp = context.getSharedPreferences("token.xml",0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token","Bearer " + response.body());
                        editor.commit();
                        Intent intent = new Intent(context, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                }else{
                    mutableError.setValue("Corrobore usuario y/0 contraseña!");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(context,"Error al llamar login: " + t,Toast.LENGTH_LONG).show();
            }
        });
        /*
        Propietario user = api.login(mail,pass);
        if(user == null){
            mutableError.setValue("Corrobore usuario y/0 contraseña!");
        }
        else{
            Intent intent = new Intent(context, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        */
    }
}
