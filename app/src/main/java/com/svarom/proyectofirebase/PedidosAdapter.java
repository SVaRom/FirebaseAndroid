package com.svarom.proyectofirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.MyViewHandle2> {

    Context context;
    ArrayList<Pedidos> pedidosArrayList;

    public PedidosAdapter(Context context, ArrayList<Pedidos> pedidosArrayList){
        this.context = context;
        this.pedidosArrayList = pedidosArrayList;
    }

    @NonNull
    @Override
    public PedidosAdapter.MyViewHandle2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list_item_single_ped,parent,false);
        return new PedidosAdapter.MyViewHandle2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAdapter.MyViewHandle2 holder, int position) {
        Pedidos pedidos=pedidosArrayList.get(position);
        holder.list_nombrep.setText(pedidos.nombre_producto);
        holder.list_cantidad.setText(String.valueOf(pedidos.cantidad));
    }

    @Override
    public int getItemCount() {return pedidosArrayList.size();}

    public static class MyViewHandle2 extends RecyclerView.ViewHolder{
        TextView list_nombrep,list_cantidad;
        public MyViewHandle2(@NonNull View itemView) {
            super(itemView);
            list_nombrep=itemView.findViewById(R.id.list_nombrep);
            list_cantidad=itemView.findViewById(R.id.list_cantidad);
        }
    }
}
