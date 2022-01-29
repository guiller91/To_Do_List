package com.example.mistareas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //obtener vistas
        ImageView logo = (ImageView) findViewById(R.id.logo_splash);
        TextView textTo = (TextView)findViewById(R.id.text_to);
        TextView textDo = (TextView)findViewById(R.id.text_do);
        TextView textList = (TextView)findViewById(R.id.text_list);

        //animacion
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.animation_logo);
        Animation anim2 = AnimationUtils.loadAnimation(this,R.anim.animation_do);
        Animation anim3 = AnimationUtils.loadAnimation(this,R.anim.animacion_list);
        Animation anim4 = AnimationUtils.loadAnimation(this,R.anim.animation_to);
        textTo.startAnimation(anim4);
        textDo.startAnimation(anim2);
        textList.startAnimation(anim3);
        logo.startAnimation(anim);
        anim3.setAnimationListener(this);


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent,b);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}