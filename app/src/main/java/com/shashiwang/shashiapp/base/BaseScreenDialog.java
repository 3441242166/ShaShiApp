package com.shashiwang.shashiapp.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;


public abstract class BaseScreenDialog extends Dialog {

    protected abstract void init();

    protected abstract int getContentView();

    public BaseScreenDialog(Context context) {
        super(context, R.style.style_screen_dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        init();
    }
}
