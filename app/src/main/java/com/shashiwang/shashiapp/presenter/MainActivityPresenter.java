package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.post.PostCarActivity;
import com.shashiwang.shashiapp.activity.post.PostFreightActivity;
import com.shashiwang.shashiapp.activity.post.PostDriverActivity;
import com.shashiwang.shashiapp.activity.post.PostStationActivity;
import com.shashiwang.shashiapp.activity.post.PostFactoryActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.constant.IssueType;
import com.shashiwang.shashiapp.view.IMainActivityView;

public class MainActivityPresenter extends BasePresenter<IMainActivityView> {
    private static final String TAG = "MainActivityPresenter";

    public MainActivityPresenter(Context context, IMainActivityView view){
        super(view,context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initPopupWindow();
    }

    public void openMorePopupWindow(){
        Log.i(TAG, "openMorePopupWindow");
        mView.openPopupWindow(popupWindow);
    }



    private PopupWindow popupWindow;

    private void initPopupWindow() {
        View popView = LayoutInflater.from(mContext).inflate(R.layout.pop_main_post, null);
        popupWindow = new PopupWindow(popView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        ColorDrawable dw = new ColorDrawable(90000000);
        popupWindow.setBackgroundDrawable(dw);
        RelativeLayout stoneFactory =  popView.findViewById(R.id.rl_pop_stone_factory);
        RelativeLayout mixStation = popView.findViewById(R.id.rl_pop_mix_station);

        RelativeLayout sale = popView.findViewById(R.id.rl_pop_sale);
        RelativeLayout dirver = popView.findViewById(R.id.rl_pop_dirver);
        RelativeLayout freight = popView.findViewById(R.id.rl_pop_freight);

        ImageView ivBack = popView.findViewById(R.id.iv_logo);

        ivBack.setOnClickListener(view -> popupWindow.dismiss());

        sale.setOnClickListener(v -> {
            openActivity(PostCarActivity.class,IssueType.A);
        });
        stoneFactory.setOnClickListener(v -> {
            openActivity(PostFactoryActivity.class,IssueType.A);
            //openActivity(LocationActivity.class,IssueType.A);
        });

        dirver.setOnClickListener(v -> {
            openActivity(PostDriverActivity.class,IssueType.A);
        });
        freight.setOnClickListener(v -> {
            openActivity(PostFreightActivity.class,IssueType.A);
        });
        mixStation.setOnClickListener(v -> {
            openActivity(PostStationActivity.class,IssueType.A);
        });
    }

    private void openActivity(Class<?> pClass, IssueType type) {
        Intent intent = new Intent(mContext, pClass);
        intent.putExtra("",type);
        mContext.startActivity(intent);
        popupWindow.dismiss();
    }

}
