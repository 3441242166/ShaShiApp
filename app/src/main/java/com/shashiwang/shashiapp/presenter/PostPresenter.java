package com.shashiwang.shashiapp.presenter;

import android.content.Context;

import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.view.PostDataView;

import java.util.Map;

public class PostPresenter extends BasePresenter<PostDataView> {


    public PostPresenter(PostDataView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init() {

    }

    public void postData(Map<String, String> map){

    }


}
