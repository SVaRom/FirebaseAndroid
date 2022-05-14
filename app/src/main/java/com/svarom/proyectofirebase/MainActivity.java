package com.svarom.proyectofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etPass,etEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPass);
        btnLogin=findViewById(R.id.btnLogin);
        Toolbar bar=findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        mAuth=FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().length()>0){
                    if(etPass.getText().length()>0){
                        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPass.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(),"Logged in as "+user.getEmail(),Toast.LENGTH_LONG).show();
                                    Intent reg=new Intent(getBaseContext(),vistauser.class);
                                    startActivity(reg);
                                }else if(etEmail.getText().toString().equals("admin")&&etPass.getText().toString().equals("root")) {
                                    Toast.makeText(getApplicationContext(),"Logged in as admin",Toast.LENGTH_LONG).show();
                                    Intent admin = new Intent(getBaseContext(), VistaAdmin.class);
                                    startActivity(admin);
                                }else Toast.makeText(getApplicationContext(),"Wrong data",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else Toast.makeText(getApplicationContext(),"Fail x pass",Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(),"Fail x email",Toast.LENGTH_LONG).show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menutoolbarlogin,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.btnReg:
                Intent reg=new Intent(this,RegistroActivity.class);
                startActivity(reg);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent reg=new Intent(getBaseContext(),vistauser.class);
            startActivity(reg);
        }
    }


}