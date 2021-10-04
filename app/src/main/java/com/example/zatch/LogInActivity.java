package com.example.zatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.findPasswordButton).setOnClickListener(onClickListener);
        findViewById(R.id.signupButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginButton:
                    Log.e("LogInActivity","tryLogin");
                    moveActivity();
                    break;
                case R.id.findPasswordButton:
                    Log.e("LogInActivity","go find password");
                    break;
                case R.id.signupButton:
                    Log.e("LogInActivity","go signup activity");
                    break;
            }
        }
    };

    private void moveActivity(){
        Intent intent = new Intent(this, FirstLocationActivity.class);
        startActivity(intent);
    }
}
