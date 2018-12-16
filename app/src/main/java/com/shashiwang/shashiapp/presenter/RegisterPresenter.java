package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.TextureView;

import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.view.IRegisterView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
                .url("api/user/captcha")
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);
                            HttpResult<ImageCode> result = new Gson().fromJson(s,new TypeToken<HttpResult<ImageCode>>(){}.getType());
                            imageCode = result.getData();
                            String imgData  = imageCode.img.replace("data:image/png;base64,", "");
                            byte[] decodedString = Base64.decode(imgData, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            mView.showImage(bitmap);
                        }
                        , throwable -> {
                    Log.i(TAG, "accept: "+throwable);
                });
    }

    @SuppressLint("CheckResult")
    public void register(String phone, String password, String code, String imgCode) {
        //HttpResult<String> result = new Gson().fromJson("",new TypeToken<HttpResult<String>>(){}.getType());

        RxRetrofitClient.builder()
                .url("/api/user/register")
                .params("phone",phone)
                .params("password",password)
                .params("c_password",password)
                .params("code",code)
                .params("role",1)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);

                            HttpResult<String> result = new Gson().fromJson(s,new TypeToken<HttpResult<String>>(){}.getType());

                            if(result.isSuccess()){
                                mView.loadDataSuccess(null);
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
        if(TextUtils.isEmpty(imgCode)){
            mView.errorMessage("请输入验证码");
            return;
        }


        RxRetrofitClient.builder()
                .url("/api/user/send/sms")
                .params("phone",phone)
                .params("captcha",imgCode)
                .params("ckey",imageCode.key)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);
                            timer.start();
                        }
                        , throwable -> {
                            Log.i(TAG, "accept: "+throwable);
                        });
    }

    @Override
    public void destroy() {
        super.destroy();
        timer.cancel();
    }

    private static class ImageCode{
        public boolean sensitive;
        public String key;
        public String img;
    }

    private static class MsgCode{
        public boolean sensitive;
        public String key;
        public String img;
    }

}
