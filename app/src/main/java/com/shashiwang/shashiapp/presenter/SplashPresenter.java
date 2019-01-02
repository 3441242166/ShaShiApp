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
import static com.shashiwang.shashiapp.constant.Constant.REGISTRATION_ID;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.Constant.USER_NAME;
import static com.shashiwang.shashiapp.util.FileUtil.isFolderExists;

public class SplashPresenter extends BasePresenter<ISplashView> {
    private static final String TAG = "SplashPresenter";


    public SplashPresenter(ISplashView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    public void checkVersion() {

        RxRetrofitClient.builder()
                .url(URL_LOGIN)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "login: success " + s);
                    HttpResult<String> result = new Gson().fromJson(s,new TypeToken<HttpResult<String>>(){}.getType());

                    String nowVersion = (String) SharedPreferencesHelper.getSharedPreference("version","");

                    if(result.isSuccess()){
                        if(!result.getData().equals(nowVersion)){

                        }
                    }

                }, throwable -> {
                    Log.i(TAG, "login: error = " + throwable);
                });

    }

    public void downloadApk(){

        String path = FileUtil.getApkDocumentPath();
        Log.i(TAG, "startDownload: path = "+path);

        if(!isFolderExists(path)){
            Log.i(TAG, "startDownload: 创建目录失败");
            return;
        }

        FileDownloader.getImpl().create("url")
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "pending: ");

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "progress: soFarByt = "+soFarBytes + " totalBytes = "+totalBytes + " taskID = "+task.getId());
                        mView.downloadProgress("");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.i(TAG, "completed: ");
                        installApk(path);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "paused: ");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.i(TAG, "error: " + e);

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.i(TAG, "warn: ");

                    }
                }).start();

    }

    private void installApk(String path) {
        File apkfile = new File(path);

        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileprovider", apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            //兼容8.0
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    //请求安装未知应用来源的权限
                    ActivityCompat.requestPermissions( (Activity) mContext, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 6666);
                }
            }
        } else {
            // 通过Intent安装APK文件
            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                    "application/vnd.android.package-archive");
        }
        if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            mContext.startActivity(intent);
        }
    }

}
