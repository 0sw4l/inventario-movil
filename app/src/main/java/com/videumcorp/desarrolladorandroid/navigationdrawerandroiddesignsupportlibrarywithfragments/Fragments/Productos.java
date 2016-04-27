package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.ProductoAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.AddProducto;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Extras;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.MainActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Producto;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.ArrayList;
import java.util.List;


public class Productos extends Fragment {

    FloatingActionButton add_producto;
    ProductoAdapter adapter;
    RecyclerView recyclerView;
    List<Producto> productos;
    long contador_de_objetos;
    String msj;
    Extras call = new Extras(getContext());
    int modifyPos = -1;

    public Productos() {
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_productos, container, false);


        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Productos");

        add_producto = (FloatingActionButton) view.findViewById(R.id.agregar_producto);
        recyclerView = (RecyclerView) view.findViewById(R.id.producto_list);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView.setLayoutManager(gridLayoutManager);

        contador_de_objetos = Producto.count(Producto.class);

        if (savedInstanceState != null)
            modifyPos = savedInstanceState.getInt("modify");

        if (contador_de_objetos >= 0){

            productos = Producto.listAll(Producto.class);
            adapter = new ProductoAdapter(getContext(), productos);
            recyclerView.setAdapter(adapter);

            if (productos.isEmpty())
                msj = "No hay productos :(";
            else
                msj = "cantidad de productos : "+contador_de_objetos;
            call.msg(msj, getContext());
        }


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_add_24dp);
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, Color.WHITE);
            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);

            add_producto.setImageDrawable(drawable);

        }

        // Handling swipe to delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //Remove swiped item from list and notify the RecyclerView

                final int position = viewHolder.getAdapterPosition();
                final Producto producto = productos.get(viewHolder.getAdapterPosition());
                productos.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(position);
                producto.categoria.setProductos(producto.categoria.getProductos()-1);
                producto.categoria.save();
                producto.delete();
                contador_de_objetos -= 1;

                call.msg("producto eliminado", view.getContext());

                /*
                Snackbar.make(recyclerView, "Producto borrado", Snackbar.LENGTH_SHORT)
                        .setAction("deshacer", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                producto.save();
                                productos.add(position, producto);
                                adapter.notifyItemInserted(position);
                                contador_de_objetos += 1;
                            }
                        })
                        .show(); */
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        adapter.SetOnItemClickListener(new ProductoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.d("Main", "click");
                Intent i = new Intent(view.getContext(), AddProducto.class);
                i.putExtra("editando", true);
                i.putExtra("producto", productos.get(position).getNombre());
                String cantidad=""+productos.get(position).getCantidad();
                i.putExtra("cantidad", cantidad);
                modifyPos = position;
                startActivity(i);
            }
        });


        add_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddProducto.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final long nuevo_contador = Producto.count(Producto.class);

        if (nuevo_contador > contador_de_objetos) {
            Log.e("lista : " ,"agregando nuevo producto");
            Producto producto = Producto.last(Producto.class);
            productos.add(producto);
            adapter.notifyItemInserted((int) nuevo_contador);
            contador_de_objetos = nuevo_contador;
        }

        if (modifyPos != -1) {
            productos.set(modifyPos, Producto.listAll(Producto.class).get(modifyPos));
            adapter.notifyItemChanged(modifyPos);
        }

    }

}
