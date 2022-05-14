package com.svarom.proyectofirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ModProducto extends AppCompatActivity {

    Button btnModP;
    EditText etPrecioN;
    FirebaseFirestore db;
    Spinner spinner3;
    ArrayList<Productos> productosArrayList;
    String nom_p="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_producto);
        Toolbar bar=findViewById(R.id.toolbar9);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnModP=findViewById(R.id.btnMod);
        db=FirebaseFirestore.getInstance();
        spinner3=findViewById(R.id.spinner3);
        etPrecioN=findViewById(R.id.etPrecioM);
        productosArrayList=new ArrayList<Productos>();
        EventChangeListener();
        btnModP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(!etPrecioN.getText().toString().isEmpty()){
                        if(Long.parseLong(etPrecioN.getText().toString())>0){
                            DocumentReference productoRef = db.collection("productos").document(nom_p);
                            productoRef.update("precio",Long.parseLong(etPrecioN.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Modificado c:",Toast.LENGTH_LONG).show();
                                    Intent admin = new Intent(getBaseContext(), VistaAdmin.class);
                                    startActivity(admin);
                                }
                            });
                        }else Toast.makeText(getApplicationContext(),"El precio no puede ser 0",Toast.LENGTH_LONG).show();
                    }else Toast.makeText(getApplicationContext(),"No dejes ningún campo vacío",Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Solo números enteros",Toast.LENGTH_LONG).show();
                }
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
                        spinner3.setAdapter(arrayAdapter);
                        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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