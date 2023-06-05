package com.gonzalez.inombiliarialavanda.modelo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dialogos {

    public static void mostrarDialogoBotones(Context context){

        new AlertDialog.Builder(context)
                .setTitle("Cerrando")
                .setMessage("Desea cerrar la sesion ?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((AppCompatActivity)context).finishAndRemoveTask();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"Continuamos",Toast.LENGTH_LONG).show();
                    }
                })
                .show();

    }
}
