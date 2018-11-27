package com.shashiwang.shashiapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懒加载的Fragment
 */
public abstract class LazyLoadFragment<T extends IBasePresenter> extends Fragment {
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
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
        presenter = setPresenter();
        isInit = true;
        /**初始化的时候去加载数据**/
        init();
        return view;
    }

    protected View getContentView() {
        return view;
    }

    protected <T extends View>  T findViewById(int id) {
        return getContentView().findViewById(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if(presenter!=null){
            presenter.destroy();
        }
    }

}