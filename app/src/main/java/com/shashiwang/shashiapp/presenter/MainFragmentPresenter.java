package com.shashiwang.shashiapp.presenter;

import android.content.Context;

import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.bean.BannerBean;
import com.shashiwang.shashiapp.fragment.MainFragment;
import com.shashiwang.shashiapp.view.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentPresenter extends IBasePresenter{


    public MainFragmentPresenter(Context context, IMainFragmentView view){
        this.mContext = context;
        this.mView = view;
    }

    public void getBannerData(){
        mView.loadDataSuccess(getData());
    }

    private List<BannerBean> getData(){
        List<BannerBean> list = new ArrayList<>();

        for(int x=0;x<5;x++){
            BannerBean bean = new BannerBean();
            bean.setImgUrl("http://img0.imgtn.bdimg.com/it/u=263005935,3287518053&fm=15&gp=0.jpg");
            bean.setTitle("title"+x);
            list.add(bean);
        }
        return list;
    }

    @Override
    public void destroy() {

    }
}
