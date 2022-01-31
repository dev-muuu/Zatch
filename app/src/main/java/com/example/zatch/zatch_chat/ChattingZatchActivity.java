package com.example.zatch.zatch_chat;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zatch.R;

public class ChattingZatchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zatch_chat_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }

}