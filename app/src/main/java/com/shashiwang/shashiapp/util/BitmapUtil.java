package com.shashiwang.shashiapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BitmapUtil {

    /**
     * 通过Base64获取Bitmap
     * @param data
     * @return
     */
    public static Bitmap getByBase64(String data){
        String imgData  = data.replace("data:image/png;base64,", "");
        byte[] decodedString = Base64.decode(imgData, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}
