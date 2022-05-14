package com.svarom.proyectofirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PedidosUAdapter  extends RecyclerView.Adapter<PedidosUAdapter.MyViewHandle3>{
    Context context;
    ArrayList<Pedidos> pedidosArrayList;
    public PedidosUAdapter(Context context, ArrayList<Pedidos> pedidosArrayList){
        this.context = context;
        this.pedidosArrayList = pedidosArrayList;
    }
    @NonNull
    @Override
    public PedidosUAdapter.MyViewHandle3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list_item_single_ped_us,parent,false);
        return new PedidosUAdapter.MyViewHandle3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosUAdapter.MyViewHandle3 holder, int position) {
        Pedidos pedidos=pedidosArrayList.get(position);
        holder.list_usuario.setText(pedidos.user);
        holder.list_nombrep.setText(pedidos.nombre_producto);
        holder.list_cantidad.setText(String.valueOf(pedidos.cantidad));
    }

    @Override
    public int getItemCount() {return pedidosArrayList.size();}

    public static class MyViewHandle3 extends RecyclerView.ViewHolder{
        TextView list_nombrep,list_cantidad,list_usuario;
        public MyViewHandle3(@NonNull View itemView) {
            super(itemView);
            list_usuario=itemView.findViewById(R.id.list_usuario);
            list_nombrep=itemView.findViewById(R.id.list_nombrepu);
            list_cantidad=itemView.findViewById(R.id.list_cantidadu);
        }
    }
}
