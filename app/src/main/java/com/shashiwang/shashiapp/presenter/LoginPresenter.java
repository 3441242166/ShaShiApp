package com.shashiwang.shashiapp.presenter;

import android.content.Context;

import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.view.ILoginView;

public class LoginPresenter extends IBasePresenter<ILoginView>{

    public LoginPresenter(ILoginView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init() {

    }
}
