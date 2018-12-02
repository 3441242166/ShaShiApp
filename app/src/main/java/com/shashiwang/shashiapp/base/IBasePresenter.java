package com.shashiwang.shashiapp.base;

import android.content.Context;

public abstract class IBasePresenter<T extends IBaseView> {

    protected T mView;
    protected Context mContext;

    protected abstract void init();

    public IBasePresenter(T view,Context context){
        this.mView = view;
        this.mContext = context;
    }

    public void destroy(){
        if(mView != null){
            mView = null;
        }
    }



}
