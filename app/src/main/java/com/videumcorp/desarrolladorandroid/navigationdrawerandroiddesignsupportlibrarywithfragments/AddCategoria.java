package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Fragments.Productos;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Categoria;

import java.util.List;

public class AddCategoria extends AppCompatActivity {

    Toolbar toolbar;
    EditText categoria_nombre;
    FloatingActionButton salvar_categoria;
    Extras call = new Extras(this);
    String nombre, nombre_;
    Categoria categoria;
    boolean editarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categoria);

        toolbar = (Toolbar) findViewById(R.id.addnote_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_clear_24dp);

        getSupportActionBar().setTitle("Formulario de Categoria");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        AddCategoria.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        salvar_categoria = (FloatingActionButton)findViewById(R.id.salvar_categoria);
        categoria_nombre = (EditText)findViewById(R.id.categoria_nombre);

        editarCategoria = getIntent().getBooleanExtra("editando", false);

        if (editarCategoria){
            nombre_ = getIntent().getStringExtra("nombre");
            categoria_nombre.setText(nombre_);
            editarCategoria=true;
        }

        salvar_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = categoria_nombre.getText().toString();
                if (nombre.length() > 0){
                    if(editarCategoria){
                        List<Categoria> cat_ = Categoria.find(Categoria.class, "nombre = ?", nombre_);
                        if (!cat_.isEmpty()){
                            Categoria c = cat_.get(0);
                            c.setNombre(nombre);
                            c.save();
                            finish();
                        }

                    }else{
                        List<Categoria> cat_ = Categoria.find(Categoria.class, "nombre = ?", nombre);
                        if (cat_.isEmpty()) {
                            categoria = new Categoria(nombre, 0);
                            categoria.save();
                            finish();
                        } else
                            call.msg("Esta categoria ya existe!", v.getContext());
                    }
                }
                else {
                    call.Alert("Error, Registro Fallido", "Por favor complete los campos para crear" +
                            "una nueva categoria", v.getContext());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
