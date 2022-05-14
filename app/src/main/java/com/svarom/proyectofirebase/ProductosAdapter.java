package com.svarom.proyectofirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.MyViewHolder> {
    Context context;
    ArrayList<Productos> productosArrayList;

    public ProductosAdapter(Context context, ArrayList<Productos> productosArrayList) {
        this.context = context;
        this.productosArrayList = productosArrayList;
    }

    @NonNull
    @Override
    public ProductosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list_item_single,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosAdapter.MyViewHolder holder, int position) {

        Productos productos=productosArrayList.get(position);
        holder.list_nombre.setText(productos.nombre);
        holder.list_precio.setText(String.valueOf(productos.precio));

    }

    @Override
    public int getItemCount() {
        return productosArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView list_nombre,list_precio;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_nombre=itemView.findViewById(R.id.list_nombre);
            list_precio=itemView.findViewById(R.id.list_precio);
        }
    }
}
