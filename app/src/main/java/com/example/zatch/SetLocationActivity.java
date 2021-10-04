package com.example.zatch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SetLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        findViewById(R.id.setMyLocationButton).setOnClickListener(onClickListener);
        findViewById(R.id.searchPlaceButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setMyLocationButton:
                    Log.e("SetLocationActivity","set My Location button click");
                    break;
                case R.id.searchPlaceButton:
                    Log.e("SetLocationActivity","search place button click");
                    break;
            }

        }
    };
}