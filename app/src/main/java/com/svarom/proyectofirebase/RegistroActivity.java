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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class RegistroActivity extends AppCompatActivity {
     EditText etPass2,etEmail2;
     Button btnSign;
     private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar bar=findViewById(R.id.toolbar2);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etPass2=findViewById(R.id.etPass2);
        etEmail2=findViewById(R.id.etEmail2);
        btnSign=findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail2.getText().length()>0 && etEmail2.getText().toString().contains("@")&& etEmail2.getText().toString().endsWith(".com")){
                    if(etPass2.getText().length()>=8){
                        mAuth=FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(etEmail2.getText().toString(),etPass2.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isComplete()){
                                    FirebaseUser user=mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(),"You got an account",Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"Fail x pass",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Fail x email",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
     public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.menutoolbarreg,menu);
         return true;
     }
}