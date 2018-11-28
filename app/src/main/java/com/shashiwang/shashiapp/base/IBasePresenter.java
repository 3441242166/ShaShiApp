package com.shashiwang.shashiapp.base;

import android.content.Context;

public abstract class IBasePresenter<T extends IBaseView> {

    protected T mView;
    protected Context mContext;

    public void destroy(){
        if(mView != null){
            mView = null;
        }
    }


}
