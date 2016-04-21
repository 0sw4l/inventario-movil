package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.CategoriaModel;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.ProductoModel;
import java.util.ArrayList;


public class AddProducto extends AppCompatActivity {

    Toolbar toolbar;
    ProductoModel producto;
    FloatingActionButton btn_salvar, btn_borrar;
    EditText nombre, cantidad, categoria_id;
    String nombre_, cantidad_, categoria_;
    int _cantidad_; long _categoria_;
    Extras call = new Extras(this);

    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);

        toolbar = (Toolbar) findViewById(R.id.addnote_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_clear_24dp);

        getSupportActionBar().setTitle("Formulario de Producto");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        AddProducto.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        nombre = (EditText)findViewById(R.id.nombre_producto);
        cantidad = (EditText)findViewById(R.id.cantidad);
        categoria_id = (EditText)findViewById(R.id.categoria);

        btn_salvar = (FloatingActionButton)findViewById(R.id.salvar_producto);
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre_ = nombre.getText().toString();
                cantidad_ = cantidad.getText().toString();
                categoria_ = categoria_id.getText().toString();

                if (nombre_.length() > 0 && cantidad_.length() > 0  && categoria_.length() > 0){
                    _categoria_ = Int(categoria_);
                    call.msg("categoria # "+_categoria_, v.getContext());
                    try {
                        CategoriaModel categoria = CategoriaModel.findById(CategoriaModel.class, _categoria_);
                        call.msg("Categoria obtenida : ", v.getContext());
                        producto = new ProductoModel(nombre_, _cantidad_, categoria);
                        producto.save();
                        categoria.productos += 1;
                        categoria.save();
                        call.msg("Producto Creado Exitosamente!", v.getContext());
                    }catch (Exception e){
                        call.msg("Error", v.getContext());
                        Log.e(e.getMessage(), "error");
                        //  call.msg("Esta categoria no existe", v.getContext());
                    }
                }else {
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

    public long Int(String c){
        long val = Integer.parseInt(c);
        return val;
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
