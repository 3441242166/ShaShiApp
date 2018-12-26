package com.shashiwang.shashiapp.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.customizeview.SettingChooseLayout;
import com.shashiwang.shashiapp.customizeview.SettingSwitchLayout;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CONFIG;

public class SettingBroadcastActivity extends BaseTopBarActivity {
    private static final String TAG = "BroadcastActivity";

    @BindView(R.id.sw_all)
    SettingSwitchLayout swAll;
    @BindView(R.id.sw_speak)
    SettingSwitchLayout swSpeak;
    @BindView(R.id.sc_car)
    SettingChooseLayout scCar;
    @BindView(R.id.sc_driver)
    SettingChooseLayout scDriver;
    @BindView(R.id.sc_fright)
    SettingChooseLayout scFright;
    @BindView(R.id.sc_factory)
    SettingChooseLayout scFactory;
    @BindView(R.id.sc_station)
    SettingChooseLayout scStation;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_setting_broadcast;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("消息推送设置");

        swAll.setOnSwitchListener(is -> {
            scCar.setCheckable(!is);
            scDriver.setCheckable(!is);
            scFright.setCheckable(!is);
            scFactory.setCheckable(!is);
            scStation.setCheckable(!is);
        });

        setTopRightButton(R.drawable.icon_certain, this::saveConfig);
    }

    @SuppressLint("CheckResult")
    private void saveConfig(){

        String category = "";
        String isVoice = "";

        RxRetrofitClient.builder()
                .url(URL_CONFIG)
                .header(new TokenInterceptor())
                .params("category","1,2,3")
                .params("is_voice",1)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<MessageResult<CarMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<CarMessage>>>(){}.getType());

                    if(result.isSuccess()){

                    }else {

                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);

                });
    }

}
