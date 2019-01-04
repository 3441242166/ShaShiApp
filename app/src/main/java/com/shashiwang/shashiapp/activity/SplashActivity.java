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

        rxPermissions = new RxPermissions(this);
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
