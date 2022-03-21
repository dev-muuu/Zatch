package com.example.zatch;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

public class ReturnPx {

    private float dp;
    private Activity activity;
    private static DisplayMetrics displayMetrics = new DisplayMetrics();

    public ReturnPx(Activity activity){
        this.activity = activity;
    }

    public ReturnPx(int dp, Activity activity) {
        this.dp = dp;
        this.activity = activity;
    }

    public float returnPx(){
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        return dp * density + 0.5f;
    }

    public float returnPx(int dpValue){
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        return dpValue * density + 0.5f;
    }

    public int returnDisplayHeight(){
//        Display display = activity.getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getRealSize(size);
//        return size.y;
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        int deviceHeight = displayMetrics.heightPixels;
        return deviceHeight;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public int getNavigationBarHeight(){
        int navigationBarHeight = 0;
        int height = activity.getResources().getIdentifier("navigation_bar_height","dimen","android");
        if (height > 0)
            navigationBarHeight = activity.getResources().getDimensionPixelSize(height);

        return navigationBarHeight;
    }

    public int getStatusBarHeight(){
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        return statusBarHeight;
    }
}
