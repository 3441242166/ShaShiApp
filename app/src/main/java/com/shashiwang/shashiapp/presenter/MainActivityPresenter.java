package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.post.PostCarMessageTopBarActivity;
import com.shashiwang.shashiapp.activity.post.PostCostTopBarActivity;
import com.shashiwang.shashiapp.activity.post.PostDriverTopBarActivity;
import com.shashiwang.shashiapp.activity.post.PostMaxFactoryTopBarActivity;
import com.shashiwang.shashiapp.activity.post.PostStoneFactoryTopBarActivity;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.contant.IssueType;
import com.shashiwang.shashiapp.view.IMainActivityView;

public class MainActivityPresenter extends IBasePresenter<IMainActivityView>{
    private static final String TAG = "MainActivityPresenter";

    public static String[] data = new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.CHANGE_WIFI_STATE,};

    public MainActivityPresenter(Context context, IMainActivityView view){
        super(view,context);
    }

    @Override
    protected void init() {
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

        sale.setOnClickListener(v -> {
            openActivity(PostCarMessageTopBarActivity.class,IssueType.A);
        });
        stoneFactory.setOnClickListener(v -> {
            openActivity(PostStoneFactoryTopBarActivity.class,IssueType.A);
            //openActivity(LocationTopBarActivity.class,IssueType.A);
        });

        dirver.setOnClickListener(v -> {
            openActivity(PostDriverTopBarActivity.class,IssueType.A);
        });
        freight.setOnClickListener(v -> {
            openActivity(PostCostTopBarActivity.class,IssueType.A);
        });
        mixStation.setOnClickListener(v -> {
            openActivity(PostMaxFactoryTopBarActivity.class,IssueType.A);
        });
    }

    private void openActivity(Class<?> pClass, IssueType type) {
        Intent intent = new Intent(mContext, pClass);
        intent.putExtra("",type);
        mContext.startActivity(intent);
        popupWindow.dismiss();
    }

}
