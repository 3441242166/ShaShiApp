package com.example.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.config.Config;

public class DimenUtil {

    public static int getScreenWidth(){
        final Resources resources = Config.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Config.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.heightPixels;
    }

}
