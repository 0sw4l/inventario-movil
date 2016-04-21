package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

public class CategoriaModel extends SugarRecord {

    String nombre;
    public int productos;

    public CategoriaModel(String nombre, int productos) {
        this.nombre = nombre;
        this.productos = productos;
    }

    public int getProductos() {
        return productos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProductos(int productos) {
        this.productos = productos;
    }
}
