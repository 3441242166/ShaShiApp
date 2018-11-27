package com.shashiwang.shashiapp.base;

/**
 * Created by wanhao on 2018/1/21.
 */

public interface IBaseView<T> {

    void showProgress();

    void dismissProgress();

    void loadDataSuccess(T data);

    void errorMessage(String throwable);

}

