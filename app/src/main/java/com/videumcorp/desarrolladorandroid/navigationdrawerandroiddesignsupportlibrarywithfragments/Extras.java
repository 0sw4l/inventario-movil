package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class Extras {

    Context context;

    public Extras(Context context){
        this.context = context;
    }

    public void Alert(String titulo, String mensaje, Context self) {
        AlertDialog.Builder alert = new AlertDialog.Builder(self);
        alert.setTitle(titulo);
        alert.setMessage(mensaje);
        alert.setNegativeButton("ok, Entendido", null);
        alert.show();
    }

    public void msg(String text, Context self){
        Toast.makeText(self, text, Toast.LENGTH_SHORT).show();
    }

}
