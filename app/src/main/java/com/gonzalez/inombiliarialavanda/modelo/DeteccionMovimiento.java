package com.gonzalez.inombiliarialavanda.modelo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class DeteccionMovimiento implements SensorEventListener {
    private static final int UMBRAL_INTENSIDAD = 30; // ajusta la intensidad del movimiento necesario para activar la deteccion
    private static final int INTERVALO_MINIMO = 5000; // ajusta el intervalo minimo entre tiempos de movimiento
    private static final int DURACION_MOVIMIENTO = 300; // ajusta la minima duracion de un movimiento
    private SensorManager sensorManager;
    private long ultimoTiempoDeDeteccion;
    private long ultimaDuracionDetectada;
    private Context contexto;

    public DeteccionMovimiento(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        contexto = context;
    }

    public void startListening() {
        Sensor acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometro != null) {
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(contexto, "Acelerometro no disponible", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopListening() {
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        float aceleracion = (float) Math.sqrt(x * x + y * y + z * z);

        Log.d("DeteccionMovimiento", "Aceleracion: " + aceleracion);
        long tiempoActual = System.currentTimeMillis();
        long diferenciaTiempo = tiempoActual - ultimoTiempoDeDeteccion;
        Log.d("DeteccionMovimiento", "Tiempo: " + diferenciaTiempo);
        Log.d("DeteccionMovimiento", "UltimaDuracion: " + ultimaDuracionDetectada);
        if (aceleracion > UMBRAL_INTENSIDAD) {
            if (diferenciaTiempo > INTERVALO_MINIMO) {
                Log.d("DeteccionMovimiento", "deteccion de movimiento");
                ultimoTiempoDeDeteccion = tiempoActual;
                ultimaDuracionDetectada = 0;
            } else if (ultimaDuracionDetectada < DURACION_MOVIMIENTO) {
                Log.d("DeteccionMovimiento", "Movimiento continuo alcanzado");
                ultimaDuracionDetectada = tiempoActual - ultimoTiempoDeDeteccion;
                if (ultimaDuracionDetectada >= DURACION_MOVIMIENTO) {
                    Log.d("DeteccionMovimiento", "Cantidad de tiempo en movimiento alcanzada");
                    iniciarActionLlamar();
                }
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {    }
    private void iniciarActionLlamar() {
        String telefonoInmobiliaria = "26655282632";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefonoInmobiliaria, null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        contexto.startActivity(intent);
    }
}
