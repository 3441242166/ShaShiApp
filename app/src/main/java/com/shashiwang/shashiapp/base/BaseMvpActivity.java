package com.shashiwang.shashiapp.base;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.config.Config;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.presenter.SplashPresenter;
import com.shashiwang.shashiapp.util.ActivityCollector;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_VERSION;
import static com.shashiwang.shashiapp.util.FileUtil.getApkDocumentPath;
import static com.shashiwang.shashiapp.util.FileUtil.isFolderExists;


public abstract class BaseMvpActivity<T extends BasePresenter> extends AppCompatActivity {
    private static final String TAG = "BaseMvpActivity";

    public T presenter;
    public boolean isForeground = false;

    protected abstract T setPresenter();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract @LayoutRes int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contentView = getContentView();
        if(contentView != -1){
            setContentView(contentView);
        }else {
            Log.e(TAG, "onCreate: no contentView");
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.addActivity(this);
        presenter = setPresenter();
        init(savedInstanceState);

        if(presenter != null){
            presenter.init(savedInstanceState);
        }
    }


    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        boolean update = (boolean) SharedPreferencesHelper.getSharedPreference("isUpdate",false);
        if(update){
            checkVersion();
        }

        if((Boolean) Config.getConfigurator().getConfigs("isFirst")){
            Config.getConfigurator().withApiHost("isFirst",false);
            checkVersion();
        }

        if(!isForeground){
            isForeground = true;
        }else {
            //如果APP从后台进入
            checkVersion();
        }
        super.onResume();
    }
    @Override
    protected void onPause() {
        if(!isAppOnForeground()){
            isForeground = false;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        if(presenter!=null){
            presenter.destroy();
            presenter = null;
        }

    }

    /**
     * 判断app是否处于前台
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    static class VersionBean{
        String version;
        String downloadUurl;
    }

    //-------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("CheckResult")
    private void checkVersion() {
        boolean update = (boolean) SharedPreferencesHelper.getSharedPreference("isUpdate",false);
        String downloadUrl = (String) SharedPreferencesHelper.getSharedPreference("downloadUrl","");
        Config.getConfigurator().withApiHost("isUpdate",update);

        Log.i(TAG, "checkVersion: upadte = " + update);
        if(update){
            showUpdateDialog(downloadUrl);
            return;
        }

        RxRetrofitClient.builder()
                .url(URL_VERSION)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "login: success " + s);
                    HttpResult<VersionBean> result = new Gson().fromJson(s,new TypeToken<HttpResult<VersionBean>>(){}.getType());

                    String nowVersion = getString(R.string.version);
                    Log.i(TAG, "checkVersion: nowVersion = " + nowVersion);

                    if(result.isSuccess()){
                        VersionBean bean = result.getData();
                        Log.i(TAG, "checkVersion: bean version = " + bean.version + " nowVersion = " +nowVersion);
                        if(!bean.version.equals(nowVersion)){
                            SharedPreferencesHelper.put("isUpdate",true);
                            SharedPreferencesHelper.put("downloadUrl",bean.downloadUurl);
                            showUpdateDialog(bean.downloadUurl);
                        }
                       // showUpdateDialog();
                    }

                }, throwable -> {

                    Log.i(TAG, "login: error = " + throwable);
                });
    }

    private void showUpdateDialog(String downloadUrl){
        MaterialDialog updateDialog;

        updateDialog = new MaterialDialog.Builder(this)
                .title("更新")
                .content("有新版本了")
                .cancelable(false)
                .positiveText("更新")
                .negativeText("退出")
                .onPositive((dialog, which) -> {
                    downloadApk(downloadUrl);
                })
                .onNegative((dialog, which) -> {
                    ActivityCollector.finishAll();
                })
                .cancelable(false)
                .build();

        updateDialog.show();
    }

    public void downloadApk(String downloadUrl){

        String path = getApkDocumentPath();
        Log.i(TAG, "startDownload: path = "+path);

        if(!isFolderExists(path)){
            Log.i(TAG, "startDownload: 创建目录失败");
            return;
        }

        MaterialDialog processDialog = new MaterialDialog.Builder(this)
                .title("Zzz...")
                .content("加载中...")
                .cancelable(false)
                .progress(false,100,true)
                .build();


        FileDownloader.getImpl().create(downloadUrl)
                .setPath(path+"app.apk")
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "pending: ");
                        processDialog.show();
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "progress: soFarByt = "+soFarBytes + " totalBytes = "+totalBytes + " taskID = "+task.getId());
                        processDialog.setProgress(soFarBytes*100/totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.i(TAG, "completed: ");
                        processDialog.dismiss();
                        install(getApkDocumentPath()+"app.apk");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "paused: ");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.i(TAG, "error: " + e);
                        processDialog.dismiss();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.i(TAG, "warn: ");

                    }
                }).start();

    }

    private void install(String filePath) {
        Log.i(TAG, "开始执行安装: " + filePath);
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkUri = FileProvider.getUriForFile(this, "com.shashiwang.shashiapp", apkFile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Log.w(TAG, "版本大于 N ，开始使用 fileProvider 进行安装");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    this
                    , "com.shashiwang.shashiapp"
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Log.w(TAG, "正常进行安装");
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

}
