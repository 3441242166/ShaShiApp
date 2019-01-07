package com.shashiwang.shashiapp.view;

import android.graphics.Bitmap;

import com.shashiwang.shashiapp.base.IBaseView;

public interface IForgetView extends IBaseView<String> {
    void showImage(Bitmap bitmap);

    void setCodeText(String str);
}
