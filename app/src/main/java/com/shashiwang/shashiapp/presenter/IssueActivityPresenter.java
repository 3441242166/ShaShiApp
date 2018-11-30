package com.shashiwang.shashiapp.presenter;

import android.content.Context;

import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.view.IIssueActivityView;

public class IssueActivityPresenter extends IBasePresenter<IIssueActivityView> {

    public IssueActivityPresenter(Context context,IIssueActivityView view) {
        mContext = context;
        mView = view;
    }

    public void postData(){

    }

}
