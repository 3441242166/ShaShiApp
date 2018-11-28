package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.zhouwei.library.CustomPopWindow;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.adapter.GridAdapter;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.view.IMainActivityView;
import com.shashiwang.shashiapp.view.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter extends IBasePresenter<IMainActivityView>{
    private static final String TAG = "MainActivityPresenter";

    public MainActivityPresenter(Context context, IMainActivityView view){
        this.mContext = context;
        this.mView = view;
    }

    private static final String[] DIALOG_TITLE =
            {"留言版", "课后作业", "课堂文件",
                    "公告", "课程信息"};
    private static final int[] DIALOG_IMG =
            {R.mipmap.gv_animation, R.mipmap.gv_multipleltem, R.mipmap.gv_header_and_footer,
                    R.mipmap.gv_pulltorefresh, R.mipmap.gv_section};
    private static final Class[] CLASSES =
            {MainActivity.class,MainActivity.class,MainActivity.class,
                    MainActivity.class, MainActivity.class};

    public void openMorePopupWindow(){
        Log.i(TAG, "openMorePopupWindow");
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_main, null);

        RecyclerView recyclerView = view.findViewById(R.id.dialog_main_recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        List<GridAdapter.GridBean> list = new ArrayList<>(DIALOG_TITLE.length);
        for( int x=0;x<DIALOG_TITLE.length;x++){
            list.add(new GridAdapter.GridBean(DIALOG_IMG[x],DIALOG_TITLE[x]));
        }
        GridAdapter adapter = new GridAdapter(list,mContext);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            mContext.startActivity(new Intent(mContext,CLASSES[position]));
        });



        CustomPopWindow popupWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(view)
                .size(WindowManager.LayoutParams.MATCH_PARENT,220)
                .setClippingEnable(true)
                .setFocusable(true)
                .create();

        mView.openPopupWindow(popupWindow);
    }

}
