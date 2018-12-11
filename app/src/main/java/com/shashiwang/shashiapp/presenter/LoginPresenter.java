package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.os.Bundle;

import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.view.ILoginView;

public class LoginPresenter extends BasePresenter<ILoginView> {

    public LoginPresenter(ILoginView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
