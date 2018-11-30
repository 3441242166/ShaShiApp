package com.shashiwang.shashiapp.view;

import android.widget.PopupWindow;

import com.example.zhouwei.library.CustomPopWindow;
import com.shashiwang.shashiapp.base.IBaseView;

public interface IMainActivityView extends IBaseView {

    void openPopupWindow(PopupWindow customPopWindow);

}
