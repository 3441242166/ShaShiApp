package com.shashiwang.shashiapp.view;

import android.graphics.Bitmap;

import com.shashiwang.shashiapp.base.IBaseView;

public interface IRegisterView extends IBaseView<String> {
    void showImage(Bitmap bitmap);
    void setCodeText(String str);
}
