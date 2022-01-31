package com.example.zatch.zatch_register;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zatch.R;
import com.example.zatch.ViewPagerAdapter;

import java.util.ArrayList;
import me.relex.circleindicator.CircleIndicator3;

public class ZatchRegisterActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ArrayList<Fragment> fragments;
    public View preButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zatch_register);

        viewPager = findViewById(R.id.zatchRegisterViewPager);
        //viewPager swipe gesture 금지
        viewPager.setUserInputEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        fragments = new ArrayList<>();
        fragments.add(new ZatchProductInfoFragment());
        fragments.add(new ZatchWantExchangeFragment());
        fragments.add(new ZatchRegisterPreShowFragment());
        viewPager.setAdapter(new ViewPagerAdapter(this,fragments));

        //fragment indicator
//        CircleIndicator3 indicator = findViewById(R.id.registerActivityIndicator);
//        indicator.setViewPager(viewPager);

        findViewById(R.id.preFragmentButton).setOnClickListener(onClickListener);
        findViewById(R.id.exitFragmentButton).setOnClickListener(onClickListener);

        preButton = findViewById(R.id.preFragmentButton);
        preButton.setVisibility(View.INVISIBLE);


    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.preFragmentButton:
                    Log.e("pre","pre");
                    movePreFragment();
                    break;
                case R.id.exitFragmentButton:
                    exitFragment();
                    Log.e("exit","exit");
                    break;
            }
        }
    };

    public void moveNextFragment(){
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        preButton.setVisibility(View.VISIBLE);
    }

    public void movePreFragment(){

        if (viewPager.getCurrentItem()-1 == 0)
            preButton.setVisibility(View.INVISIBLE);

        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }

    public void exitFragment(){
        this.finish();
    }

}



