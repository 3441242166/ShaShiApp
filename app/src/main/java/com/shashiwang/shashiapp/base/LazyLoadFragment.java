package com.shashiwang.shashiapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懒加载的Fragment
 */
public abstract class LazyLoadFragment<T extends BasePresenter> extends Fragment {
    protected final String TAG = "LazyLoadFragment";

    private View view;
    private Unbinder mUnbinder;

    public T presenter;

    protected abstract T setPresenter();

    protected abstract int setContentView();

    protected abstract void init();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        init();
        if(presenter!= null){
            presenter.init(savedInstanceState);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = setPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if(presenter!=null){
            presenter.destroy();
            presenter = null;
        }
    }

}