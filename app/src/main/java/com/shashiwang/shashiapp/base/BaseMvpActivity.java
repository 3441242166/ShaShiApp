package com.shashiwang.shashiapp.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.shashiwang.shashiapp.util.ActivityCollector;

import butterknife.ButterKnife;


public abstract class BaseMvpActivity<T extends IBasePresenter> extends AppCompatActivity {

    public T presenter;

    protected abstract T setPresenter();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract @LayoutRes int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ActivityCollector.addActivity(this);
        presenter = setPresenter();
        init(savedInstanceState);
        presenter.init();
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
