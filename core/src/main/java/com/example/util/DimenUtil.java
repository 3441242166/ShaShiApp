package com.example.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.config.Config;

public class DimenUtil {

    public static int getScreenWidth(Context context){
        final Resources resources = context.getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context){
        final Resources resources = context.getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.heightPixels;
    }

}
