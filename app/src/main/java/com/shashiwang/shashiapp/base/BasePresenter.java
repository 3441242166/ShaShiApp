package com.shashiwang.shashiapp.base;

import android.content.Context;
import android.os.Bundle;

public abstract class BasePresenter<T extends IBaseView> {

    protected T mView;
    protected Context mContext;

    protected abstract void init(Bundle savedInstanceState);

    public BasePresenter(T view, Context context){
        this.mView = view;
        this.mContext = context;
    }

    public void destroy(){
        if(mView != null){
            mView = null;
        }
    }
}
