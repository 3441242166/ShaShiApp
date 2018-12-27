package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.BannerBean;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.view.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_BANNER;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;

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
                    HttpResult<String[]> result = new Gson().fromJson(s,new TypeToken<HttpResult<String[]>>(){}.getType());

                    if(result.isSuccess()){
                        List<String> list = new ArrayList<>();

                        for(String str:result.getData()){
                            list.add(str);
                        }

                        mView.loadDataSuccess(list);
                    }else {

                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);

                });

    }


}
