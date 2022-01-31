package com.example.zatch.location;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zatch.R;

public class StartLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_location);
        findViewById(R.id.goSetLocationButton).setOnClickListener(onClickListener);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.goSetLocationButton:
                    Log.e("FirstLocationActivity","move Location set activity");
                    moveActivity();
                    break;
            }
        }
    };

    private void moveActivity(){
//        Intent intent = new Intent(this, SetLocationActivity.class);
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }
}
