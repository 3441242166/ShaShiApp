package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.view.IMyFragmentView;

import static com.example.util.SharedPreferencesHelper.*;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.Constant.USER_NAME;

public class MyFragmentPresenter extends BasePresenter<IMyFragmentView> {
    private static final String TAG = "MyFragmentPresenter";

    public MyFragmentPresenter(IMyFragmentView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.i(TAG, "init");
        checkLogin();
    }

    public void checkLogin(){
        String token = (String) getSharedPreference(TOKEN,null);
        if(TextUtils.isEmpty(token)){
            Log.i(TAG, "UnLogin");
            mView.unLogin(true,"先生");
            return;
        }

        String count = (String) getSharedPreference(USER_NAME,null);

        mView.unLogin(false,count);
    }

    public void getUserMessage(){

    }

}
