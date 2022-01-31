package com.example.zatch.location;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zatch.R;

public class SetLocationActivity extends AppCompatActivity {

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
//                case R.id.textView4:
//                    Log.e("SetLocationActivity","search place button click");
//                    break;
            }

        }
    };

    private void moveChatActivity(){
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }
//
//    private void moveActivity(){
//        Intent intent = new Intent(this, MyZatchActivity.class);
//        startActivity(intent);
//    }
}