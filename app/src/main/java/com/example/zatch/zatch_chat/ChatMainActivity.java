package com.example.zatch.zatch_chat;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zatch.R;
import com.example.zatch.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ChatMainActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zatch_chat_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ViewPager2 viewPager = findViewById(R.id.chatViewPager);
        TabLayout pagerTab = findViewById(R.id.chatTab);

        //viewpager adapter
        fragments = new ArrayList<>();
        fragments.add(new ChatZatchFragment());
        viewPager.setAdapter(new ViewPagerAdapter(this, fragments));

        String[] tabTitle = {"재치"};

        new TabLayoutMediator(pagerTab, viewPager,
                (tab, position) -> tab.setText(tabTitle[position])
        ).attach();


    }

}
