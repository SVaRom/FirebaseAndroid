package com.svarom.proyectofirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VistaAdmin extends AppCompatActivity implements View.OnClickListener{

    RecyclerView vistaProductos;
    ArrayList<Productos> productosArrayList;
    ProductosAdapter productosAdapter;
    FirebaseFirestore db;
    ProgressDialog processDialog;
    FloatingActionButton fabtnAddP,fabtnModP,fabtnDelP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_admin);
        Toolbar bar=findViewById(R.id.toolbar6);
        setSupportActionBar(bar);
        fabtnAddP=findViewById(R.id.fabtnAddP);
        fabtnAddP.setOnClickListener(this::onClick);
        fabtnModP=findViewById(R.id.fabtnModP);
        fabtnModP.setOnClickListener(this::onClick);
        fabtnDelP=findViewById(R.id.fabtnDelP);
        fabtnDelP.setOnClickListener(this::onClick);
        processDialog=new ProgressDialog(this);
        processDialog.setCancelable(false);
        processDialog.setMessage("Fetching data...");
        processDialog.show();
        vistaProductos=findViewById(R.id.rvLista2);
        vistaProductos.setHasFixedSize(true);
        vistaProductos.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        productosArrayList=new ArrayList<Productos>();
        productosAdapter=new ProductosAdapter(VistaAdmin.this,productosArrayList);

        vistaProductos.setAdapter(productosAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("productos").orderBy("nombre", Query.Direction.ASCENDING)
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
                                productosArrayList.add(dc.getDocument().toObject(Productos.class));
                            }
                            productosAdapter.notifyDataSetChanged();
                            if(processDialog.isShowing()){
                                processDialog.dismiss();
                            }
                        }
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menulogoutadmin,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.btnLogoutA:
                //Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Set tittle
                builder.setTitle("Logout");
                //Set message
                builder.setMessage("Seguro que quieres cerrar la sesión?");
                //Positive yes button
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent logout=new Intent(getBaseContext(),MainActivity.class);
                        startActivity(logout);
                    }
                });
                //Negative no button
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                //Show dialog
                builder.show();
                break;
            case R.id.btnPedUs:
                Intent pedus=new Intent(getBaseContext(),MostrarPedidosUsuarios.class);
                startActivity(pedus);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabtnAddP:
                Intent add=new Intent(getBaseContext(),AddProducto.class);
                startActivity(add);
                break;
            case R.id.fabtnModP:
                Intent mod=new Intent(getBaseContext(),ModProducto.class);
                startActivity(mod);
                break;
            case R.id.fabtnDelP:
                Intent del=new Intent(getBaseContext(),DelProducto.class);
                startActivity(del);
                break;
        }
    }

}