package com.shashiwang.shashiapp.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.shashiwang.shashiapp.BuildConfig;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.IBaseView;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.util.FileUtil;
import com.shashiwang.shashiapp.util.ProgressRequestBody;
import com.shashiwang.shashiapp.view.ISplashView;

import java.io.File;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR_UPLOAD;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_LOGIN;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_VERSION;
import static com.shashiwang.shashiapp.constant.Constant.REGISTRATION_ID;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.Constant.USER_NAME;
import static com.shashiwang.shashiapp.util.FileUtil.getApkDocumentPath;
import static com.shashiwang.shashiapp.util.FileUtil.isFolderExists;

public class SplashPresenter extends BasePresenter<ISplashView> {
    private static final String TAG = "SplashPresenter";

    public SplashPresenter(ISplashView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

}
