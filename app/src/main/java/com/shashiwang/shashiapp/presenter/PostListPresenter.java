package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.config.Config;
import com.example.net.interceptors.BaseInterceptor;
import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.IBaseView;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.view.PostListView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.MessageType.CAR;
import static com.shashiwang.shashiapp.constant.MessageType.DRIVER;
import static com.shashiwang.shashiapp.constant.MessageType.FACTORY;
import static com.shashiwang.shashiapp.constant.MessageType.FREIGHT;
import static com.shashiwang.shashiapp.constant.MessageType.POST;
import static com.shashiwang.shashiapp.constant.MessageType.STATION;

public class PostListPresenter extends BasePresenter<PostListView> {
    private static final String TAG = "PostListPresenter";

    public PostListPresenter(PostListView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    public void getList(String url,int type){
        Log.i(TAG, "getList: url = "+url + " type = "+type);

        RxRetrofitClient.builder()
                .header(new TokenInterceptor())
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<MessageResult> result = new Gson().fromJson(s,getType(type));
                    //Log.i(TAG, "getList: test " + ((FreightMessage)result.getData().getData().get(0)).getCargo_name());

                    if(result.isSuccess()){
                        List data = result.getData().getData();
                        mView.loadDataSuccess(data);
                    }else {
                         mView.errorMessage(result.getMessage());
                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);
                    mView.errorMessage(throwable.toString());
                });
    }

    private Type getType(int type){

        switch (type){
            case POST:
                return new TypeToken<HttpResult<MessageResult>>(){}.getType();
            case CAR:
                return new TypeToken<HttpResult<MessageResult>>(){}.getType();
            case FREIGHT:
                return new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType();
            case DRIVER:
                return new TypeToken<HttpResult<MessageResult>>(){}.getType();
            case FACTORY:
                return new TypeToken<HttpResult<MessageResult>>(){}.getType();
            case STATION:
                return new TypeToken<HttpResult<MessageResult>>(){}.getType();
        }
        return null;
    }

}
