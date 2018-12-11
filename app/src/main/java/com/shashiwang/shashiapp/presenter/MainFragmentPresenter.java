package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.os.Bundle;

import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.BannerBean;
import com.shashiwang.shashiapp.view.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentPresenter extends BasePresenter<IMainFragmentView> {


    public MainFragmentPresenter(IMainFragmentView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public void getBannerData(){
        mView.loadDataSuccess(getData());
    }

    private List<BannerBean> getData(){
        List<BannerBean> list = new ArrayList<>();

        for(int x=0 ;x<5 ;x++){
            BannerBean bean = new BannerBean();
            bean.setImgUrl("http://img0.imgtn.bdimg.com/it/u=263005935,3287518053&fm=15&gp=0.jpg");
            bean.setTitle("title" + x );
            list.add(bean);
        }
        return list;
    }

}
