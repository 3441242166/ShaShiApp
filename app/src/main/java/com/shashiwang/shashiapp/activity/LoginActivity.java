package com.shashiwang.shashiapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static androidx.navigation.Navigation.findNavController;

public class LoginActivity<T extends BasePresenter> extends BaseMvpActivity<T> {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.iv_back)
    ImageView ivBack;

    public static String[] DATA = new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected T setPresenter() {
        return null;
    }

    @SuppressLint("CheckResult")
    private void checkPermissions(){
        if (!EasyPermissions.hasPermissions(this, DATA)) {
            EasyPermissions.requestPermissions(this, "为了您的体验,请允许申请权限",
                    1, DATA);
        }

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        checkPermissions();

        ivBack.setOnClickListener(view -> {
            if(!findNavController(this, R.id.login_fragment).navigateUp()){
                finish();
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: requestCode = "+requestCode + "  resultCode = "+resultCode);

        if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){
            if (!EasyPermissions.hasPermissions(this, DATA)) {
                Toasty.info(this,"为了您的体验,请允许申请权限").show();
                finish();
            }
        }
    }
}
