package com.shashiwang.shashiapp.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
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
import com.shashiwang.shashiapp.util.ConfigUtil;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CONFIG;
import static com.shashiwang.shashiapp.constant.MessageType.*;

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

        initView();

        swAll.setOnSwitchListener(is -> {
            changeAll(is);
        });
        setTopRightButton(R.drawable.icon_certain, this::saveConfig);
    }

    private void initView() {
        boolean isCar = (boolean) SharedPreferencesHelper.getSharedPreference(KEY_CAR,false);
        boolean isFactory = (boolean) SharedPreferencesHelper.getSharedPreference(KEY_FACTORY,false);
        boolean isFreight = (boolean) SharedPreferencesHelper.getSharedPreference(KEY_FREIGHT,false);
        boolean isDriver = (boolean) SharedPreferencesHelper.getSharedPreference(KEY_DRIVER,false);
        boolean isStation = (boolean) SharedPreferencesHelper.getSharedPreference(KEY_STATION,false);

        if(isCar && isFactory && isFreight && isDriver && isStation){
            swAll.setChecked(true);
            changeAll(true);
        }else {
            scCar.setChecked(isCar);
            scFactory.setChecked(isFactory);
            scFright.setChecked(isFreight);
            scDriver.setChecked(isDriver);
            scStation.setChecked(isStation);
        }

    }

    private void changeAll(boolean is){
        if(is){
            scCar.setChecked(true);
            scDriver.setChecked(true);
            scFright.setChecked(true);
            scFactory.setChecked(true);
            scStation.setChecked(true);
        }else {
            scCar.setChecked(false);
            scDriver.setChecked(false);
            scFright.setChecked(false);
            scFactory.setChecked(false);
            scStation.setChecked(false);
        }
        scCar.setCheckable(!is);
        scDriver.setCheckable(!is);
        scFright.setCheckable(!is);
        scFactory.setCheckable(!is);
        scStation.setCheckable(!is);
    }

    @SuppressLint("CheckResult")
    private void saveConfig(){
        StringBuilder category = new StringBuilder();
        if(scCar.isChoose()){
            category.append(",");
            category.append(CAR);
        }
        if(scDriver.isChoose()){
            category.append(",");
            category.append(DRIVER);
        }
        if(scFright.isChoose()){
            category.append(",");
            category.append(FREIGHT);
        }
        if(scFactory.isChoose()){
            category.append(",");
            category.append(FACTORY);
        }
        if(scStation.isChoose()){
            category.append(",");
            category.append(STATION);
        }
        if(category.indexOf(",") == 0){
            category.replace(0,1,"");
        }

        Log.i(TAG, "saveConfig: " + category.toString());

        int isVoice = swSpeak.isChecked()? 1 : 0;
        if(TextUtils.isEmpty(category.toString())){
            category.append(0);
        }

        RxRetrofitClient.builder()
                .url(URL_CONFIG)
                .header(new TokenInterceptor())
                .params("category",category.toString())
                .params("is_voice",isVoice)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<MessageResult<CarMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<CarMessage>>>(){}.getType());

                    if(result.isSuccess()){
                        ConfigUtil.configJPush(category.toString(),isVoice);
                        finish();
                    }else {

                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);

                });
    }

}
