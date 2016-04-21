package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;


public class ProductoModel  extends SugarRecord {

    String nombre;
    int cantidad;
    CategoriaModel categoria;

    public ProductoModel(String nombre, int cantidad, CategoriaModel categoria){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
    }
}
