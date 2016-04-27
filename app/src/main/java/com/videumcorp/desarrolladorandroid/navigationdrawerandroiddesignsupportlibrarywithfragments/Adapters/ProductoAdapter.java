package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters;

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

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoVH> {

    Context context;
    List<Producto> productos;

    OnItemClickListener clickListener;

    public ProductoAdapter(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public ProductoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_item, parent, false);
        return new ProductoVH(view);
    }

    public String nombre, cantidad, categoria;

    @Override
    public void onBindViewHolder(ProductoVH holder, int position) {
        nombre = productos.get(position).getNombre();
        holder.titulo.setText(nombre);
        cantidad = "unidades : "+productos.get(position).getCantidad();
        holder.cantidad.setText(cantidad);
        categoria = "categoria : "+productos.get(position).getCategoria().toString();
        holder.categoria.setText(categoria);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    class ProductoVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titulo, cantidad, categoria;

        public ProductoVH(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo_producto);
            cantidad = (TextView) itemView.findViewById(R.id.cantidad_producto);
            categoria = (TextView) itemView.findViewById(R.id.categoria_producto);
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