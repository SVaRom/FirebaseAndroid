package com.svarom.proyectofirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DelProducto extends AppCompatActivity {

    Button btnDelP;
    FirebaseFirestore db;
    Spinner spinner2;
    ArrayList<Productos> productosArrayList;
    String nom_p="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_producto);
        Toolbar bar=findViewById(R.id.toolbar8);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDelP=findViewById(R.id.btnDelP);
        db=FirebaseFirestore.getInstance();
        spinner2=findViewById(R.id.spinner2);
        productosArrayList=new ArrayList<Productos>();
        EventChangeListener();
        btnDelP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("productos").document(nom_p)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Eliminado c:",Toast.LENGTH_LONG).show();
                        Intent admin = new Intent(getBaseContext(), VistaAdmin.class);
                        startActivity(admin);
                    }
                });

            }
        });
    }

    private void EventChangeListener() {
        db.collection("productos").orderBy("nombre", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                productosArrayList.add(dc.getDocument().toObject(Productos.class));
                            }
                        }
                        ArrayAdapter<Productos> arrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,productosArrayList);
                        spinner2.setAdapter(arrayAdapter);
                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                nom_p=adapterView.getItemAtPosition(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menutoolbarreg,menu);
        return true;
    }
}