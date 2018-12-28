package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.activity.post.PostCarActivity;
import com.shashiwang.shashiapp.adapter.PhotoAdapter;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.view.IPostCarView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;

public class PostCarPresenter extends BasePresenter<IPostCarView> {
    private static final String TAG = "PostCarPresenter";

    public PostCarPresenter(IPostCarView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public void onSelectImage(Uri uri) {

    }

    @SuppressLint("CheckResult")
    public void psotData(String brand, int carType, String factoryYear, String mileage, String price, String linkman, String phone, String remark, List<PhotoAdapter.PhotoBean> photos){

        if(checkData()){
            RxRetrofitClient.builder()
                    .header(new TokenInterceptor())
                    .url(URL_CAR)
                    .params("brand",brand)
                    .params("category",carType)
                    .params("factory_year",factoryYear)
                    .params("mileage",mileage)
                    .params("price",price)
                    .params("linkman",linkman)
                    .params("phone",phone)
                    .params("remark",remark)
                    .build()
                    .post()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Log.i(TAG, "getList: success " + s);
                        HttpResult<MessageResult<FreightMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType());

                        if(result.isSuccess()){

                            Toast.makeText(mContext,"发布成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mContext,result.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        Log.i(TAG, "getList: error = " + throwable);
                        Toast.makeText(mContext,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private boolean checkData() {

        return true;
    }

}
