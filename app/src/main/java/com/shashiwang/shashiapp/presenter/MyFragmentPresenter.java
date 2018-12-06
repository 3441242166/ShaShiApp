package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.view.IMyFragmentView;

import static com.example.util.SharedPreferencesHelper.*;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;

public class MyFragmentPresenter extends BasePresenter<IMyFragmentView> {
    private static final String TAG = "MyFragmentPresenter";

    public MyFragmentPresenter(IMyFragmentView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init() {
        Log.i(TAG, "init");

        String token = (String) getSharedPreference(TOKEN,null);
        if(TextUtils.isEmpty(token)){
            Log.i(TAG, "UnLogin");
            mView.unLogin();
            return;
        }


    }

}