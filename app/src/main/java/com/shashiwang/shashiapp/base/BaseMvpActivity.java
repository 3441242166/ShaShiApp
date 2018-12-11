package com.shashiwang.shashiapp.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shashiwang.shashiapp.util.ActivityCollector;


public abstract class BaseMvpActivity<T extends BasePresenter> extends AppCompatActivity {
    private static final String TAG = "BaseMvpActivity";

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

        if(presenter != null){
            Log.i(TAG, "presenter.init");
            presenter.init(savedInstanceState);
        }
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
