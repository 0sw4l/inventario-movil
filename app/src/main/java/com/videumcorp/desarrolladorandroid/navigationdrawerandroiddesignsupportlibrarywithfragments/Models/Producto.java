package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;


public class Producto extends SugarRecord {

    public String nombre;
    public int cantidad;
    public Categoria categoria;

    public Producto(){}

    public Producto(String nombre, int cantidad, Categoria categoria){
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
