package com.example.mistareas;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.mistareas.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity{

    RelativeLayout container;
    ControladorDB controladorDB;
    Animation slide_up;
    boolean flag = true;
    VideoView videoBackground;
    TextInputLayout textInputLayoutUsername,textInputLayoutPassword;
    TextInputEditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        controladorDB= new ControladorDB(this);
        readyUI();
    }

    //Notificación en pantalla
    public void crearCuenta(View view){
        String usuario = user.getText().toString();
        String contraseña = pass.getText().toString();
        if(usuario.isEmpty() || contraseña.isEmpty()){
            Toast toast = Toast.makeText(this,"Introduzca los datos",Toast.LENGTH_LONG);
            toast.show();
        }else if(controladorDB.checkUserName(user.getText().toString())){
            Toast toast = Toast.makeText(this,"Usuario ya registrado",Toast.LENGTH_LONG);
            toast.show();
        }else{
            controladorDB.insertUser(user.getText().toString(),pass.getText().toString());
            Toast toast = Toast.makeText(this,"Usuario creado",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void login(View view){

        String usuario = user.getText().toString();
        String contraseña = pass.getText().toString();
        if(usuario.isEmpty() || contraseña.isEmpty()){
            Toast toast = Toast.makeText(this,"Introduzca los datos",Toast.LENGTH_LONG);
            toast.show();

        }else if(controladorDB.checkUserLogin(usuario, contraseña)){
            Toast toast = Toast.makeText(this,"Credenciales Invalidas",Toast.LENGTH_LONG);
            toast.show();
        }else{
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("usuario",usuario);
            startActivity(intent);
            finish();
        }
    }
    public void init(){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)container.getLayoutParams();
        layoutParams.height = 200;
        container.setLayoutParams(layoutParams);
        container.startAnimation(slide_up);

    }

   public void slideUpLogin(View view){
        if(flag){
            textInputLayoutUsername.setVisibility(View.VISIBLE);
            textInputLayoutPassword.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)container.getLayoutParams();
            layoutParams.height = 1300;
            container.setLayoutParams(layoutParams);
            container.startAnimation(slide_up);
            flag = false;
        }else{
            flag = true;
            init();
        }
   }
   //Preparar ui
   public void readyUI(){
       videoBackground = findViewById(R.id.video_background);
       container = findViewById(R.id.relative_container);
       textInputLayoutUsername = findViewById(R.id.input_layout_username);
       textInputLayoutPassword = findViewById(R.id.input_layout_password);
       user = (TextInputEditText) findViewById(R.id.cajaUser);
       pass = (TextInputEditText) findViewById(R.id.cajaPass);
       slide_up = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
       init();
       videoBackground.setMediaController(null);
       videoBackground.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.splash);
       videoBackground.start();
       videoBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
               mp.setLooping(true);
           }
       });
   }



}