package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.CategoriaAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.ProductoAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.AddCategoria;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.AddProducto;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Extras;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.MainActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Categoria;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Models.Producto;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.List;


public class Categorias extends Fragment {

    FloatingActionButton add_categoria;
    CategoriaAdapter adapter;
    RecyclerView recyclerView;
    List<Categoria> categorias;
    long contador_de_objetos;
    Extras call = new Extras(getContext());
    int modifyPos = -1;
    String msj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_categorias, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Categorias");

        add_categoria = (FloatingActionButton) view.findViewById(R.id.agregar_categoria);
        recyclerView = (RecyclerView) view.findViewById(R.id.categoria_list);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView.setLayoutManager(gridLayoutManager);

        contador_de_objetos = Categoria.count(Categoria.class);

        if (savedInstanceState != null)
            modifyPos = savedInstanceState.getInt("modify");

        if (contador_de_objetos >= 0){

            categorias = Categoria.listAll(Categoria.class);
            adapter = new CategoriaAdapter(getContext(), categorias);
            recyclerView.setAdapter(adapter);

            if (categorias.isEmpty())
                msj = "No hay categorias :(";
            else
                msj = "numero de categorias : "+contador_de_objetos;
            call.msg(msj, getContext());
        }



        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_add_24dp);
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, Color.WHITE);
            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);

            add_categoria.setImageDrawable(drawable);

        }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {


                final int position = viewHolder.getAdapterPosition();
                final Categoria categoria = categorias.get(viewHolder.getAdapterPosition());
                categorias.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(position);
                categoria.delete();
                contador_de_objetos -= 1;

                call.msg("Categoria Eliminada", view.getContext());

                /*
                Snackbar.make(recyclerView, "Categoria Eliminada", Snackbar.LENGTH_SHORT)
                        .setAction("deshacer", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                categoria.save();
                                categorias.add(position, categoria);
                                adapter.notifyItemInserted(position);
                                contador_de_objetos += 1;
                            }
                        })
                        .show(); */
            }

        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        adapter.SetOnItemClickListener(new CategoriaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.d("Main", "click");
                Intent i = new Intent(view.getContext(), AddCategoria.class);
                i.putExtra("editando", true);
                i.putExtra("nombre", categorias.get(position).toString());
                modifyPos = position;
                startActivity(i);
            }
        });

        add_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCategoria.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final long nuevo_contador = Categoria.count(Categoria.class);

        if (nuevo_contador > contador_de_objetos) {
            Log.e("lista : " ,"se ha creado una nueva categoria");
            Categoria categoria = Categoria.last(Categoria.class);
            categorias.add(categoria);
            adapter.notifyItemInserted((int) nuevo_contador);
            contador_de_objetos = nuevo_contador;
        }

        if (modifyPos != -1) {
            categorias.set(modifyPos, Categoria.listAll(Categoria.class).get(modifyPos));
            adapter.notifyItemChanged(modifyPos);
        }

    }


}
