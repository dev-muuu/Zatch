package com.example.zatch.navigation.my_zatch;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zatch.R;

public class my_gatch_detail extends Activity {
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    private int[] images = new int[]{
            R.drawable.img1, R.drawable.img2
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_gatch_detail);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        sliderViewPager = (ViewPager2) findViewById(R.id.detail_img);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) sliderViewPager.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.widthPixels;
        sliderViewPager.setLayoutParams(params);

//        layoutIndicator = findViewById(R.id.layoutIndicators);
//        sliderViewPager.setOffscreenPageLimit(1);
//        sliderViewPager.setAdapter(new img_slider_adapter(this, images));
//
//        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                setCurrentIndicator(position);
//            }
//        });
//        setupIndicators(images.length);
    }
//    private void setupIndicators(int count) {
//        ImageView[] indicators = new ImageView[count];
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        params.setMargins(16, 8, 16, 8);
//
//        for (int i = 0; i < indicators.length; i++) {
//            indicators[i] = new ImageView(this);
//            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
//                    R.drawable.indicator_inactive));
//            indicators[i].setLayoutParams(params);
//            layoutIndicator.addView(indicators[i]);
//        }
//        setCurrentIndicator(0);
//    }
//
//    private void setCurrentIndicator(int position) {
//        int childCount = layoutIndicator.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
//            if (i == position) {
//                imageView.setImageDrawable(ContextCompat.getDrawable(
//                        this,
//                        R.drawable.indicator_active
//                ));
//            } else {
//                imageView.setImageDrawable(ContextCompat.getDrawable(
//                        this,
//                        R.drawable.indicator_inactive
//                ));
//            }
//        }
//    }

}
