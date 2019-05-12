package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.view.IMainFragmentView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_BANNER;

public class MainFragmentPresenter extends BasePresenter<IMainFragmentView> {
    private static final String TAG = "MainFragmentPresenter";

    public MainFragmentPresenter(IMainFragmentView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public void getBannerData(){
        getData();
    }

    @SuppressLint("CheckResult")
    private void getData(){

        RxRetrofitClient.builder()
                .url(URL_BANNER)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<List<String>> result = new Gson().fromJson(s,new TypeToken<HttpResult<List<String>>>(){}.getType());

                    if(result.isSuccess()){
                        mView.loadDataSuccess(result.getData());
                    }else {

                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);

                });

    }


}
