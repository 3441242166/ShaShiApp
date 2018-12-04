package com.shashiwang.shashiapp.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseLoginActivity;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.presenter.LoginPresenter;
import com.shashiwang.shashiapp.view.ILocationView;
import com.shashiwang.shashiapp.view.ILoginView;

import java.util.List;

public class LoginActivity extends BaseLoginActivity<LoginPresenter> implements ILoginView{


    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_base_login;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object data) {

    }

    @Override
    public void errorMessage(String throwable) {

    }
}
