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
import android.view.KeyEvent;
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
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;

public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements ISplashView {
    private static final String TAG = "SplashActivity";


    @Override
    protected SplashPresenter setPresenter() {
        return new SplashPresenter(this,this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object data) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void errorMessage(String throwable) {

    }

    //防止退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
