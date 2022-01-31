package com.example.zatch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView ment = findViewById(R.id.spalshMentText);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                //animate alpha 효과 추가하기.
                ment.setVisibility(View.VISIBLE);
            }
        },1500);

        handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                moveLogInActivity();
            }
        },3000); //3초 뒤에 LogInActivity 실행하도록 함
    }

    private void moveLogInActivity(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}