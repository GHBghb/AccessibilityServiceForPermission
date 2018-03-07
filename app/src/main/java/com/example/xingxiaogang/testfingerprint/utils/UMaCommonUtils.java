package com.example.xingxiaogang.testfingerprint.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class UMaCommonUtils {
    private static final String TAG = "uma.utils.common";

    public UMaCommonUtils() {
    }

    public static int dip2px(@NonNull Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int sp2px(@NonNull Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * scale + 0.5F);
    }

    public static int px2dip(@NonNull Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int getScreenWidth(@NonNull Context context) {
        Display display = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(@NonNull Context context) {
        Display display = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
}