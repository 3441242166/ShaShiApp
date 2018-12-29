package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.shashiwang.shashiapp.util.ProgressRequestBody;
import com.shashiwang.shashiapp.view.IPostCarView;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR_UPLOAD;
import static com.shashiwang.shashiapp.util.FileUtil.getImagePath;

public class PostCarPresenter extends BasePresenter<IPostCarView> {
    private static final String TAG = "PostCarPresenter";

    private OnUploadListener uploadListener;

    public PostCarPresenter(IPostCarView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    public void postData(String brand, int carType, String factoryYear, String mileage, String price, String linkman, String phone, String remark, List<String> photos){
        mView.showProgress();
        List<String> photoStrings = new ArrayList<>(photos.size());

        uploadListener = () -> {
            if(photos.size() != photoStrings.size()){
                Log.i(TAG, "postData: error size is not same ");
                mView.dismissProgress();
                return;
            }

            StringBuilder imageStr = new StringBuilder();

            for(String str:photoStrings){
                imageStr.append(",");
                imageStr.append(str);
            }

            if(imageStr.length()>0){
                imageStr.replace(0,1,"");
            }
            Log.i(TAG, "postData: imageStr = " + imageStr.toString());

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
                        .params("image",imageStr.toString())
                        .build()
                        .post()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            Log.i(TAG, "getList: success " + s);
                            HttpResult<MessageResult<FreightMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType());

                            if(result.isSuccess()){
                                mView.loadDataSuccess(null);
                            }else {
                                Toast.makeText(mContext,result.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            mView.dismissProgress();
                        }, throwable -> {
                            Log.i(TAG, "getList: error = " + throwable);
                            Toast.makeText(mContext,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                            mView.dismissProgress();
                        });
            }
        };

        uploadImage(photos,photoStrings);


    }

    private boolean checkData() {

        return true;
    }

    @SuppressLint("CheckResult")
    private void uploadImage(List<String> photos,List<String> photoStrings){
        try {
            upload(Luban.with(mContext).load(photos).get(),photoStrings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CheckResult")
    private void upload(List<File> files, List<String> photoStrings) {
        for(File file:files){
            ProgressRequestBody requestFile = new ProgressRequestBody(file, "image/*", percentage -> {
                Log.i(TAG, "onProgressUpdate: " + percentage);
            });

            MultipartBody.Part body = MultipartBody.Part.createFormData("image[]", file.getName(), requestFile);

            RxRetrofitClient.builder()
                    .header(new TokenInterceptor())
                    .body(body)
                    .url(URL_CAR_UPLOAD)
                    .build()
                    .upload()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Log.i(TAG, "getList: success " + s);
                        HttpResult<List<String>> result = new Gson().fromJson(s,new TypeToken<HttpResult<List<String>>>(){}.getType());
                        String url = result.getData().get(0);
                        Log.i(TAG, "uploadImage: url = "+url);
                        photoStrings.add(url);

                        if(uploadListener != null){
                            uploadListener.onUpload();
                        }

                    }, throwable -> {
                        Log.i(TAG, "uploadImage: " + throwable);
                    });
        }

    }

    interface OnUploadListener{
        void onUpload();
    }
}
