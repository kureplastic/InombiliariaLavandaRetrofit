package com.gonzalez.inombiliarialavanda.request;

import com.gonzalez.inombiliarialavanda.modelo.Contrato;
import com.gonzalez.inombiliarialavanda.modelo.Inmueble;
import com.gonzalez.inombiliarialavanda.modelo.Inquilino;
import com.gonzalez.inombiliarialavanda.modelo.Pago;
import com.gonzalez.inombiliarialavanda.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiClientRetrofit {

    private static final String PATH="http://192.168.0.15:5029/API/";
    private static  EndPointInmobiliaria endPointInmobiliaria;

    public static EndPointInmobiliaria getEndpointInmobiliaria(){

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        endPointInmobiliaria=retrofit.create(EndPointInmobiliaria.class);

        return endPointInmobiliaria;
    }

    public interface EndPointInmobiliaria{

        @POST("Propietarios/login")
        Call<String> login(@Body Propietario propietario);

        @GET("Propietarios/MiPerfil")
        Call<Propietario> obtenerPerfil(@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("Propietarios/ActualizarPerfil")
        Call<Propietario> actualizarPerfil(@Header("Authorization") String token, @Field("dni") Long dni, @Field("nombre") String nombre, @Field("apellido") String apellido, @Field("telefono") String telefono,@Field("email") String email,@Field("clave") String clave);


        @GET("Inmuebles/ObtenerPorPropietario")
        Call<List<Inmueble>> obtenerInmueblesPorPropietario(@Header("Authorization") String token);

        @PUT("Inmuebles/ModificarDisponibilidad/{id}")
        Call<Inmueble> modificarDisponibilidad(@Header("Authorization") String token, @Path("id") int IdInmueble);

        @GET("Contratos/ObtenerInmueblesAlquilados")
        Call<List<Inmueble>> obtenerInmueblesAlquilados(@Header("Authorization") String token);

        @GET("Contratos/ObtenerInquilino/{id}")
        Call<Inquilino> obtenerInquilino(@Header("Authorization") String token, @Path("id") int id);

        @GET("Contratos/obtenerContratoVigente/{id}")
        Call<Contrato> obtenerContratoVigente(@Header("Authorization") String token, @Path("id") int id);

        @GET("Pagos/ObtenerPagos/{id}")
        Call<List<Pago>> obtenerPagos(@Header("Authorization") String token, @Path("id") int id);

    }
}
