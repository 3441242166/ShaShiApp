package com.shashiwang.shashiapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.presenter.SplashPresenter;
import com.shashiwang.shashiapp.view.ISplashView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import es.dmoral.toasty.Toasty;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;

public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements ISplashView,EasyPermissions.PermissionCallbacks {
    private static final String TAG = "SplashActivity";

    public static String[] DATA = new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.REQUEST_INSTALL_PACKAGES,
            android.Manifest.permission.CHANGE_WIFI_STATE};

    private MaterialDialog dialog;
    private MaterialDialog updateDialog;
    private MaterialDialog processDialog;

    private RxPermissions rxPermissions ;

    @Override
    protected SplashPresenter setPresenter() {
        return new SplashPresenter(this,this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        checkPermissions();

        presenter.checkVersion();

        findViewById(R.id.bck).setOnClickListener(view -> loadDataSuccess(null));
    }

    private void initView(){

        dialog = new MaterialDialog.Builder(this)
                .title("警告")
                .content("为了您的使用体验,请打开通知权限")
                .cancelable(false)
                .positiveText("开启")
                .negativeText("忽略")
                .onPositive((dialog, which) -> {
                    openSetting();
                })
                .onNegative((dialog, which) -> {
                    dialog.cancel();
                })
                .build();

        updateDialog = new MaterialDialog.Builder(this)
                .title("更新")
                .content("有新版本了")
                .cancelable(false)
                .positiveText("更新")
                .negativeText("退出")
                .onPositive((dialog, which) -> {
                    presenter.downloadApk();
                    showProgress();
                })
                .onNegative((dialog, which) -> {
                    finish();
                })
                .cancelable(false)
                .build();

        processDialog = new MaterialDialog.Builder(this)
                .title("Zzz...")
                .content("加载中...")
                .cancelable(false)
                .progress(false,100,true)
                .build();
    }

    @SuppressLint("CheckResult")
    private void checkPermissions(){
        if (!EasyPermissions.hasPermissions(this, DATA)) {
            EasyPermissions.requestPermissions(this, "为了您的体验,请允许申请权限",
                    1, DATA);
        }

        boolean is = NotificationManagerCompat.from(this).areNotificationsEnabled();
        Log.i(TAG, "init:  isOpen Notification " + is);
        if(!is){
            dialog.show();
        }

        rxPermissions = new RxPermissions(this);

//        rxPermissions
//                .request(DATA)
//                .subscribe(granted -> {
//                    if (granted) {
//                        // Always true pre-M
//                        // I can control the camera now
//
//                    } else {
//
//                        // Oups permission denied
//                    }
//                });

    }

    public void openSetting(){
        // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
        intent.putExtra(EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(EXTRA_CHANNEL_ID, getApplicationInfo().uid);

        //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
        intent.putExtra("app_package", getPackageName());
        intent.putExtra("app_uid", getApplicationInfo().uid);

        // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
        //  if ("MI 6".equals(Build.MODEL)) {
        //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        //      Uri uri = Uri.fromParts("package", getPackageName(), null);
        //      intent.setData(uri);
        //      // intent.setAction("com.android.settings/.SubSettings");
        //  }
        startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void showProgress() {
        processDialog.show();
    }

    @Override
    public void dismissProgress() {
        processDialog.dismiss();
    }

    @Override
    public void loadDataSuccess(Object data) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void errorMessage(String throwable) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){
            if (!EasyPermissions.hasPermissions(this, DATA)) {
                Toasty.info(this,"为了您的体验,请允许申请权限").show();
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .setTitle("为了您的体验,请允许申请权限")
                    .setThemeResId(R.style.AppTheme)
                    .build()
                    .show();
        }
    }

    @Override
    public void downloadProgress(int str) {
        processDialog.setProgress(str);
    }

    @Override
    public void showVersionDialog() {
        updateDialog.show();
    }


}
