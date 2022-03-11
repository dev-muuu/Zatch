package com.example.zatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zatch.zatch_register.ZatchRegisterActivity;

public class MyZatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zatch);

//        findViewById(R.id.moveRegisterFloatingButton).setOnClickListener(onClickListener);
    }

//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.moveRegisterFloatingButton:
//                    moveRegisterActivity();
//                    break;
//            }
//        }
//    };

    void moveRegisterActivity(){
        Intent intent = new Intent(this, ZatchRegisterActivity.class);
        startActivity(intent);
    }
}
