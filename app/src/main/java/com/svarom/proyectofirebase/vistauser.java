package com.svarom.proyectofirebase;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class vistauser extends AppCompatActivity {

   private FirebaseAuth mAuth;
   RecyclerView vistaProductos;
   ArrayList<Productos> productosArrayList;
   ProductosAdapter productosAdapter;
   FirebaseFirestore db;
   ProgressDialog processDialog;
   FloatingActionButton fabtnPedir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistauser);
        processDialog=new ProgressDialog(this);
        processDialog.setCancelable(false);
        processDialog.setMessage("Fetching data...");
        processDialog.show();
        Toolbar bar=findViewById(R.id.toolbar3);
        setSupportActionBar(bar);
        vistaProductos=findViewById(R.id.rvLista);
        vistaProductos.setHasFixedSize(true);
        vistaProductos.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        productosArrayList=new ArrayList<Productos>();
        productosAdapter=new ProductosAdapter(vistauser.this,productosArrayList);

        vistaProductos.setAdapter(productosAdapter);

        EventChangeListener();

        fabtnPedir=findViewById(R.id.fabtnPedir);
        fabtnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent realiza=new Intent(getBaseContext(),RealizarPedido.class);
                startActivity(realiza);
            }
        });

    }

    private void EventChangeListener() {
        db.collection("productos").orderBy("nombre",Query.Direction.ASCENDING)
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
        getMenuInflater().inflate(R.menu.menulogout,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.btnLogout:
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
                        mAuth.signOut();
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
            case R.id.btnPed:
                Intent ped=new Intent(getBaseContext(),MostrarPedidos.class);
                startActivity(ped);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}