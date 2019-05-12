package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.Count;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.util.BitmapUtil;
import com.shashiwang.shashiapp.util.CheckUtil;
import com.shashiwang.shashiapp.util.DataUtil;
import com.shashiwang.shashiapp.view.IRegisterView;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_FIND_PASSWORD;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_IMAGE_CODE;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_SMS_CODE;


public class RegisterPresenter extends BasePresenter<IRegisterView> {
    private static final String TAG = "RegisterPresenter";

    private ImageCode imageCode;

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mView.setCodeText(millisUntilFinished/1000 + "秒");
        }

        @Override
        public void onFinish() {
            mView.setCodeText("获取验证码");
        }
    };

    public RegisterPresenter(IRegisterView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getImageCode();
    }

    @SuppressLint("CheckResult")
    public void getImageCode(){

        RxRetrofitClient.builder()
                .url(URL_IMAGE_CODE)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);
                            HttpResult<ImageCode> result = new Gson().fromJson(s,new TypeToken<HttpResult<ImageCode>>(){}.getType());
                            imageCode = result.getData();

                            Bitmap bitmap = BitmapUtil.getByBase64(imageCode.img);
                            mView.showImage(bitmap);
                        }
                        , throwable -> {
                    Log.i(TAG, "accept: "+throwable);
                });
    }

    @SuppressLint("CheckResult")
    public void register(String phone, String password, String code) {
        if(TextUtils.isEmpty(phone)){
            mView.errorMessage("请输入账号");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mView.errorMessage("请输入密码");
            return;
        }

        RxRetrofitClient.builder()
                .url("register")
                .params("count",phone)
                .params("password",password)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);

                            HttpResult<Object> result = new Gson().fromJson(s,new TypeToken<HttpResult<Object>>(){}.getType());

                            if(result.isSuccess()){
                                mView.loadDataSuccess("注册成功");
                            }else {
                                mView.errorMessage(result.getMessage());
                            }

                        }
                        , throwable -> {
                            Log.i(TAG, "accept: "+throwable);
                            mView.errorMessage(throwable.toString());
                        });
    }

    @SuppressLint("CheckResult")
    public void getCode(String imgCode,String phone) {

        if(!CheckUtil.isPhone(phone)){
            mView.errorMessage("请输入正确的手机号");
            return;
        }
        if(TextUtils.isEmpty(imgCode)){
            mView.errorMessage("请输入验证码");
            return;
        }


        RxRetrofitClient.builder()
                .url(URL_SMS_CODE)
                .params("phone",phone)
                .params("captcha",imgCode)
                .params("ckey",imageCode.key)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);
                            HttpResult<Object> result = new Gson().fromJson(s,new TypeToken<HttpResult<Object>>(){}.getType());
                            if(result.isSuccess()){
                                timer.start();
                            }else {
                                mView.errorMessage(result.getMessage());
                            }
                        }
                        , throwable -> {
                            Log.i(TAG, "accept: "+throwable);
                            mView.errorMessage("网络异常");
                        });
    }

    @Override
    public void destroy() {
        super.destroy();
        timer.cancel();
    }

    public static class ImageCode{
        public boolean sensitive;
        public String key;
        public String img;
    }

}
