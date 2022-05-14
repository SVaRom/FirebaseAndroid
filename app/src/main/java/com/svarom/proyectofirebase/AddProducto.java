package com.svarom.proyectofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddProducto extends AppCompatActivity {
    EditText etNomPA, etPrecioPA;
    Button btnAddP;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);
        Toolbar bar=findViewById(R.id.toolbar7);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etNomPA=findViewById(R.id.etNomPA);
        etPrecioPA=findViewById(R.id.etPrecio);
        btnAddP=findViewById(R.id.btnAddP);
        db=FirebaseFirestore.getInstance();
        btnAddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(!etNomPA.getText().toString().isEmpty() && !etPrecioPA.getText().toString().isEmpty()){
                        Map<String,Object> data=new HashMap<>();
                        data.put("nombre",etNomPA.getText().toString());
                        data.put("precio",Long.parseLong(etPrecioPA.getText().toString()));
                        db.collection("productos").document(etNomPA.getText().toString()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Agregado c:",Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        });
                    }else Toast.makeText(getApplicationContext(),"No dejes campos vacíos",Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Solo números enteros",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutoolbarreg, menu);
        return true;
    }

}