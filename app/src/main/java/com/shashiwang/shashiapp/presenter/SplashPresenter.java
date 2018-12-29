package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.os.Bundle;

import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.IBaseView;
import com.shashiwang.shashiapp.view.ISplashView;

public class SplashPresenter extends BasePresenter<ISplashView> {

    public SplashPresenter(ISplashView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public void checkVersion() {



    }

}
