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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Categoria;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Producto;

import java.util.ArrayList;
import java.util.List;


public class AddProducto extends AppCompatActivity {

    Toolbar toolbar;
    Producto producto;
    FloatingActionButton btn_salvar;
    EditText nombre, cantidad;
    Spinner categorias;
    String nombre_, cantidad_, categoria_, old_name;
    int _cantidad_;
    int _categoria_;
    boolean editarProducto;
    Extras call = new Extras(this);

    private ArrayList<Categoria> data;
    private ArrayAdapter<String> adapter;
    List<Producto> get_producto;

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

        List<Categoria> cats = Categoria.listAll(Categoria.class);

        nombre = (EditText) findViewById(R.id.nombre_producto);
        cantidad = (EditText) findViewById(R.id.cantidad);
        categorias = (Spinner) findViewById(R.id.categoria);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cats);

        categorias.setAdapter(adapter);

        editarProducto = getIntent().getBooleanExtra("editando", false);

        if (editarProducto) {
            nombre_ = getIntent().getStringExtra("producto");
            old_name = nombre_;
            get_producto = Producto.find(Producto.class, "nombre = ?", old_name);
            cantidad_ = getIntent().getStringExtra("cantidad");
            _categoria_ = getIntent().getIntExtra("categoria", 0);
            nombre.setText(nombre_);
            cantidad.setText(cantidad_, TextView.BufferType.EDITABLE);
            editarProducto = true;
        }

        String cat_spinner = "";
        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Categoria cat = (Categoria) parent.getItemAtPosition(position);
                categoria_ = cat.nombre;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //CategoriaModel.deleteAll(CategoriaModel.class);
        //call.msg("categorias borradas", this);

        btn_salvar = (FloatingActionButton) findViewById(R.id.salvar_producto);
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre_ = nombre.getText().toString();
                cantidad_ = cantidad.getText().toString();
                if (nombre_.length() > 0 && cantidad_.length() > 0 && categoria_.length() > 0) {
                    try {

                        _cantidad_ = Int(cantidad_);
                        List<Categoria> cat_ = Categoria.find(Categoria.class, "nombre = ?", categoria_);

                        if (editarProducto){

                            if (get_producto.size() > 0) {

                                Producto p = get_producto.get(0);
                                p.setNombre(nombre_);
                                p.cantidad = _cantidad_;
                                Categoria c = cat_.get(0);

                                if (p.getCategoria() != c){
                                    c.setProductos(c.getProductos()+1);
                                    c.save();
                                    p.categoria.setProductos(p.categoria.getProductos()-1);
                                    p.categoria.save();
                                }

                                p.categoria = cat_.get(0);
                                p.save();
                                finish();
                            }
                        }else{
                            List<Producto> prod_ = Producto.find(Producto.class, "nombre = ?", nombre_);
                            if (!cat_.isEmpty() && prod_.isEmpty()) {
                                producto = new Producto(nombre_, _cantidad_, cat_.get(0));
                                producto.save();
                                Categoria cat = cat_.get(0);
                                cat.productos++;
                                cat.save();
                                finish();
                            } else {
                                call.Alert("Error", "este producto ya existe", v.getContext());
                            }
                        }

                    } catch (Exception e) {
                        call.msg("Error", v.getContext());
                        Log.e(e.getMessage(), "error");
                    }
                } else {
                    call.Alert("Error, Registro Fallido", "Por favor complete los campos para crear" +
                            "una nuevo producto", v.getContext());
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

    public int Int(String c) {
        int val = Integer.parseInt(c);
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
