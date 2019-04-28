package com.shashiwang.shashiapp.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by wanhao on 2018/1/11.
 */
public class ImageLoader extends com.youth.banner.loader.ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

}