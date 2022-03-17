package com.example.zatch.search;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;

import com.example.zatch.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FragmentContainerView searchFragment = findViewById(R.id.searchFragment);

        findViewById(R.id.searchBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Navigation.findNavController(searchFragment).getCurrentDestination().getLabel().equals("SearchFirstFragment"))
                    finish();
                else
                    Navigation.findNavController(searchFragment).popBackStack();
            }
        });

    }
}
