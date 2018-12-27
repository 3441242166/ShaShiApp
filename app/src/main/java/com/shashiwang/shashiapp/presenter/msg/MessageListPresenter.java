package com.shashiwang.shashiapp.presenter.msg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.activity.message.CarMessageActivity;
import com.shashiwang.shashiapp.activity.message.DriverMessageActivity;
import com.shashiwang.shashiapp.activity.message.FactoryMessageActivity;
import com.shashiwang.shashiapp.activity.message.FreightMessageActivity;
import com.shashiwang.shashiapp.activity.message.StationMessageActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.DriverMessage;
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.bean.StationMessage;
import com.shashiwang.shashiapp.constant.MessageType;
import com.shashiwang.shashiapp.presenter.PostListPresenter;
import com.shashiwang.shashiapp.view.IMessageListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_DRIVER;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_FACTORY;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_FREIGHT;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_PUBLISH;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_STATION;

public class MessageListPresenter extends BasePresenter<IMessageListView> {
    private static final String TAG = "MessageListPresenter";

    private int page = 1;

    public MessageListPresenter(IMessageListView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    public void getList(int type,boolean isFirst){
        String url = getUrl(type);
        Log.i(TAG, "getList:  url = " + url);
        if(isFirst) {
            page = 1;
        }else {
            page++;
        }
        RxRetrofitClient.builder()
                .header(new TokenInterceptor())
                .url(url)
                .params("page",page)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    ResultMessage result = handleMessage(s,type);
                    if(result.isSuccess){
                        mView.loadDataSuccess(result.data);
                    }else {

                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);

                });
    }

    private ResultMessage handleMessage(String str, int type){
        List<MessageBean> data = new ArrayList<>();
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.data = data;

        switch (type){
            case MessageType.FACTORY:
                HttpResult<MessageResult<FactoryMessage>> factoryResult = new Gson().fromJson(str ,new TypeToken<HttpResult<MessageResult<FactoryMessage>>>(){}.getType());
                if(!factoryResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(FactoryMessage message :factoryResult.getData().getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
            case MessageType.STATION:
                HttpResult<MessageResult<StationMessage>> stationResult = new Gson().fromJson(str ,new TypeToken<HttpResult<MessageResult<StationMessage>>>(){}.getType());
                if(!stationResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(StationMessage message :stationResult.getData().getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
            case MessageType.CAR:
                HttpResult<MessageResult<CarMessage>> carResult = new Gson().fromJson(str ,new TypeToken<HttpResult<MessageResult<CarMessage>>>(){}.getType());
                if(!carResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(CarMessage message :carResult.getData().getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
            case MessageType.FREIGHT:
                HttpResult<MessageResult<FreightMessage>> freightResult = new Gson().fromJson(str ,new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType());
                if(!freightResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(FreightMessage message :freightResult.getData().getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
            case MessageType.DRIVER:
                HttpResult<MessageResult<DriverMessage>> driverResult = new Gson().fromJson(str ,new TypeToken<HttpResult<MessageResult<DriverMessage>>>(){}.getType());
                if(!driverResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(DriverMessage message :driverResult.getData().getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
        }

        return resultMessage;
    }

    private String getUrl(int type){
        switch (type){
            case MessageType.FACTORY:
                return URL_FACTORY;
            case MessageType.STATION:
                return URL_STATION;
            case MessageType.CAR:
                return URL_CAR;
            case MessageType.FREIGHT:
                return URL_FREIGHT;
            case MessageType.DRIVER:
                return URL_DRIVER;
        }
        return null;
    }

    static class ResultMessage{
        List<MessageBean> data;
        boolean isSuccess;
        ResultMessage(){
            isSuccess = true;
        }
    }
}
