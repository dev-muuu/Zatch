package com.example.zatch;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ReturnPx {

    private float dp;
    private Activity activity;

    public ReturnPx(int dp, Activity activity) {
        this.dp = dp;
        this.activity = activity;
    }

    public float returnPx(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;

        return dp * density + 0.5f;
    }
}
