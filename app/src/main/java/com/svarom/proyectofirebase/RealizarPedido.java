package com.svarom.proyectofirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
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
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealizarPedido extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etCant;
    Button btnReal;
    FirebaseFirestore db;
    Spinner spinner;
    ArrayList<Productos> productosArrayList;
    String nom_p="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        Toolbar bar=findViewById(R.id.toolbar5);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etCant=findViewById(R.id.etCant);
        btnReal=findViewById(R.id.btnReal);
        db=FirebaseFirestore.getInstance();
        spinner=findViewById(R.id.spinner);
        productosArrayList=new ArrayList<Productos>();
        EventChangeListener();
        btnReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(!etCant.getText().toString().isEmpty()){
                        Map<String,Object> data=new HashMap<>();
                        data.put("user",mAuth.getCurrentUser().getEmail().toString());
                        data.put("nombre_producto",nom_p);
                        data.put("cantidad",Long.parseLong(etCant.getText().toString()));
                        if(Long.parseLong(etCant.getText().toString())>0){
                            db.collection("pedidos").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(getApplicationContext(),"Pedido c:",Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Fail xc",Toast.LENGTH_LONG).show();
                                }
                            });
                        }else Toast.makeText(getApplicationContext(),"Debes pedir al menos un producto",Toast.LENGTH_LONG).show();
                    } else Toast.makeText(getApplicationContext(),"No dejes el campo vacío",Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Solo números enteros",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void EventChangeListener() {
        db.collection("productos").orderBy("nombre",Query.Direction.ASCENDING)
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
                        spinner.setAdapter(arrayAdapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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