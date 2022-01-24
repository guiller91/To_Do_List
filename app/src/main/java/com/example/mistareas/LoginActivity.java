package com.example.mistareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
    //Notificación en pantalla
    public void crearCuenta(View view){
        Toast toast = Toast.makeText(this, "funcionalidad no implementada",Toast.LENGTH_LONG);
        toast.show();
    }

    public void login(View view){
        TextInputEditText user = (TextInputEditText) findViewById(R.id.cajaUser);
        TextInputEditText pass = (TextInputEditText) findViewById(R.id.cajaPass);

        if(user.getText().toString().equalsIgnoreCase("guiller") & pass.getText().toString().equalsIgnoreCase("1234")){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this,"credenciales invalidas",Toast.LENGTH_LONG);
        }
    }
}