package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.bean.User;
import com.shashiwang.shashiapp.util.ConfigUtil;
import com.shashiwang.shashiapp.view.IMyFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.util.SharedPreferencesHelper.*;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_USER_INFO;
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

    @SuppressLint("CheckResult")
    public void getUserMessage(){
        RxRetrofitClient.builder()
                .header(new TokenInterceptor())
                .url(URL_USER_INFO)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<User> result = new Gson().fromJson(s,new TypeToken<HttpResult<User>>(){}.getType());

                    if(result.isSuccess()){
                        ConfigUtil.configJPush(result.getData().getPush_category(),result.getData().getIs_voice());
                    }else {
                        mView.errorMessage(result.getMessage());
                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);
                    mView.errorMessage(throwable.toString());
                });

    }

}
