package com.shashiwang.shashiapp.presenter;

import android.content.Context;

import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.view.IMyFragmentView;

public class MyFragmentPresenter extends IBasePresenter<IMyFragmentView> {

    public MyFragmentPresenter(IMyFragmentView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init() {

    }

}
