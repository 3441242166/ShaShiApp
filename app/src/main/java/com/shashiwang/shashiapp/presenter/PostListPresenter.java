package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.DriverMessage;
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.bean.StationMessage;
import com.shashiwang.shashiapp.constant.MessageType;
import com.shashiwang.shashiapp.service.LocationService;
import com.shashiwang.shashiapp.view.IPostListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_PUBLISH;
public class PostListPresenter extends BasePresenter<IPostListView> {
    private static final String TAG = "PostListPresenter";

    private BDLocation location;

    private boolean isWrite = false;

    private int type;

    public PostListPresenter(IPostListView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext.startService(new Intent(mContext,LocationService.class));
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLocationData(BDLocation bdLocation) {
        location = bdLocation;
        if(isWrite){
            getList(type);
            isWrite = false;
        }
    }

    @SuppressLint("CheckResult")
    public void getList(int type){
        this.type = type;

        if(location == null){
            isWrite = true;
            return;
        }

        String url = URL_PUBLISH + type;
        Log.i(TAG, "getList:  url = " + url);

        RxRetrofitClient.builder()
                .header(new TokenInterceptor())
                .params("location_lat",location.getLatitude())
                .params("location_lng",location.getLongitude())
                .url(url)
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

    private ResultMessage handleMessage(String str,int type){
        List<MessageBean> data = new ArrayList<>();
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.data = data;

        switch (type){
            case MessageType.FACTORY:
                HttpResult<List<FactoryMessage>> factoryResult = new Gson().fromJson(str ,new TypeToken<HttpResult<List<FactoryMessage>>>(){}.getType());
                if(!factoryResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                Log.i(TAG, "handleMessage: size = " + factoryResult.getData().size());
                for(FactoryMessage message :factoryResult.getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
            case MessageType.STATION:
                HttpResult<List<StationMessage>> stationResult = new Gson().fromJson(str ,new TypeToken<HttpResult<List<StationMessage>>>(){}.getType());
                if(!stationResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(StationMessage message :stationResult.getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
            case MessageType.CAR:
                HttpResult<List<CarMessage>> carResult = new Gson().fromJson(str ,new TypeToken<HttpResult<List<CarMessage>>>(){}.getType());
                if(!carResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(CarMessage message :carResult.getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
            case MessageType.FREIGHT:
                HttpResult<List<FreightMessage>> freightResult = new Gson().fromJson(str ,new TypeToken<HttpResult<List<FreightMessage>>>(){}.getType());
                if(!freightResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(FreightMessage message :freightResult.getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
            case MessageType.DRIVER:
                HttpResult<List<DriverMessage>> driverResult = new Gson().fromJson(str ,new TypeToken<HttpResult<List<DriverMessage>>>(){}.getType());
                if(!driverResult.isSuccess()){
                    resultMessage.isSuccess = false;
                    return resultMessage;
                }
                for(DriverMessage message :driverResult.getData()){
                    data.add(new MessageBean(type,message));
                }
                break;
        }

        return resultMessage;
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        super.destroy();
    }

    static class ResultMessage{
        List<MessageBean> data;
        boolean isSuccess;
        ResultMessage(){
            isSuccess = true;
        }
    }
}
