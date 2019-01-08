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
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected final String TAG = "BaseFragment";

    private Bundle savedInstanceState;
    private boolean canInit = false;
    private boolean isInit = false;
    private View view;
    private Unbinder mUnbinder;

    public T presenter;

    protected abstract T setPresenter();

    protected abstract int setContentView();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        presenter = setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        canInit = true;
        isCanLoadData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    private void isCanLoadData(){
        // 可见 && 可以初始化 && 未初始化过
        if(getUserVisibleHint() && canInit && !isInit){
            Log.i(TAG, "IIIIIIIIIIInit: ");
            isInit = true;
            init(savedInstanceState);
            if(presenter!= null){
                presenter.init(savedInstanceState);
            }
        }
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