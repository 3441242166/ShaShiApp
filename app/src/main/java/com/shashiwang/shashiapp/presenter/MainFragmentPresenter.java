package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.adapter.GridAdapter;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.bean.BannerBean;
import com.shashiwang.shashiapp.fragment.MainFragment;
import com.shashiwang.shashiapp.view.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentPresenter extends IBasePresenter<IMainFragmentView>{


    public MainFragmentPresenter(IMainFragmentView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init() {

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
