package com.shashiwang.shashiapp.base;

import android.content.Context;

public abstract class IBasePresenter {

    protected IBaseView mView;
    protected Context mContext;

    public abstract void destroy();


}
