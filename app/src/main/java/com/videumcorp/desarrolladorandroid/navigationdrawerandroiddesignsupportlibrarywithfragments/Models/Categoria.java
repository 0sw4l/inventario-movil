package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

public class Categoria extends SugarRecord {

    public String nombre;
    public int productos;

    public Categoria(String nombre, int productos) {
        this.nombre = nombre;
        this.productos = productos;
    }

    public Categoria(){}

    public int getProductos() {
        return productos;
    }

    public String toString() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProductos(int productos) {
        this.productos = productos;
    }

}
