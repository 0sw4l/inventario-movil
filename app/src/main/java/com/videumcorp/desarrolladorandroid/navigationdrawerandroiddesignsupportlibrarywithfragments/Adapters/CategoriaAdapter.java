package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Categoria;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Producto;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Producto;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaVH> {

    Context context;
    List<Categoria> categorias;

    OnItemClickListener clickListener;

    public CategoriaAdapter(Context context, List<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @Override
    public CategoriaVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent, false);
        return new CategoriaVH(view);
    }

    public String nombre, cantidad;

    @Override
    public void onBindViewHolder(CategoriaVH holder, int position) {
        nombre = categorias.get(position).toString();
        holder.titulo.setText(nombre);
        cantidad = "productos : "+categorias.get(position).getProductos();
        holder.cantidad.setText(cantidad);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    class CategoriaVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titulo, cantidad;

        public CategoriaVH(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo_categoria);
            cantidad = (TextView) itemView.findViewById(R.id.productos_categoria);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}