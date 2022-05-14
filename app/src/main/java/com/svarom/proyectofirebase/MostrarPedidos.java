package com.svarom.proyectofirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MostrarPedidos extends AppCompatActivity {

    private FirebaseAuth mAuth;
    RecyclerView vistaPedidos;
    ArrayList<Pedidos> predidosArrayList;
    PedidosAdapter pedidosAdapter;
    FirebaseFirestore db;
    ProgressDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pedidos);
        Toolbar bar=findViewById(R.id.toolbar4);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        processDialog=new ProgressDialog(this);
        processDialog.setCancelable(false);
        processDialog.setMessage("Fetching data...");
        processDialog.show();
        vistaPedidos=findViewById(R.id.rvListaP);
        vistaPedidos.setHasFixedSize(true);
        vistaPedidos.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        predidosArrayList=new ArrayList<Pedidos>();
        pedidosAdapter=new PedidosAdapter(MostrarPedidos.this,predidosArrayList);

        vistaPedidos.setAdapter(pedidosAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("pedidos").whereEqualTo("user",mAuth.getCurrentUser().getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            if(processDialog.isShowing()){
                                processDialog.dismiss();
                            }
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                predidosArrayList.add(dc.getDocument().toObject(Pedidos.class));
                            }
                            pedidosAdapter.notifyDataSetChanged();
                        }
                        if(processDialog.isShowing()){
                            processDialog.dismiss();
                        }
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menutoolbarreg,menu);
        return true;
    }

}