package com.shashiwang.shashiapp.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.shashiwang.shashiapp.util.ActivityCollector;

import butterknife.ButterKnife;


public abstract class BaseMvpActivity<T extends IBasePresenter> extends AppCompatActivity {

    public T presenter;

    protected abstract T setPresenter();

    protected abstract @LayoutRes int getContentView();

    protected abstract void init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        presenter = setPresenter();
        init();
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
}
