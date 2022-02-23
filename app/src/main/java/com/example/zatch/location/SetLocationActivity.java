package com.example.zatch.location;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zatch.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import static retrofit2.converter.gson.GsonConverterFactory.create;


public class SetLocationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        findViewById(R.id.textView5).setOnClickListener(onClickListener);
        findViewById(R.id.textView4).setOnClickListener(onClickListener);

        getSupportActionBar().hide();


   }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textView4:
                    Log.e("SetLocationActivity","set My Location button click");
                    moveChatActivity();
                    break;
            }

        }
    };

    private void moveChatActivity(){
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }



}