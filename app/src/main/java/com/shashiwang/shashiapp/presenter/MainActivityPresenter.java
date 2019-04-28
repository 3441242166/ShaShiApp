package com.shashiwang.shashiapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.DoHomeworkActivity;
import com.shashiwang.shashiapp.activity.MessageListActivity;
import com.shashiwang.shashiapp.activity.VRImagectivity;
import com.shashiwang.shashiapp.activity.post.PostCarActivity;
import com.shashiwang.shashiapp.activity.post.PostFreightActivity;
import com.shashiwang.shashiapp.activity.post.PostDriverActivity;
import com.shashiwang.shashiapp.activity.post.PostStationActivity;
import com.shashiwang.shashiapp.activity.post.PostFactoryActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.constant.IssueType;
import com.shashiwang.shashiapp.view.IMainActivityView;

import static com.shashiwang.shashiapp.constant.Constant.TITLE;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.Constant.TYPE;
import static com.shashiwang.shashiapp.constant.MessageType.DRIVER;
import static com.shashiwang.shashiapp.constant.MessageType.FACTORY;

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
            Intent intent = new Intent(mContext, MessageListActivity.class);
            intent.putExtra(TYPE,DRIVER);
            intent.putExtra(TITLE,"艺术欣赏");
            mContext.startActivity(intent);
        });
        stoneFactory.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DoHomeworkActivity.class);
            intent.putExtra(TYPE,FACTORY);
            intent.putExtra(TITLE,"体验VR");
            mContext.startActivity(intent);
        });

        dirver.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MessageListActivity.class);
            intent.putExtra(TYPE,FACTORY);
            intent.putExtra(TITLE,"小游戏");
            mContext.startActivity(intent);
        });
        freight.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MessageListActivity.class);
            intent.putExtra(TYPE,DRIVER);
            intent.putExtra(TITLE,"艺术知识");
            mContext.startActivity(intent);
        });
        mixStation.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, VRImagectivity.class);
            intent.putExtra(TYPE,FACTORY);
            intent.putExtra(TITLE,"体验VR");
            mContext.startActivity(intent);
        });
    }

    private boolean isLogin(){
        String token = (String) SharedPreferencesHelper.getSharedPreference(TOKEN,"");

//        if(TextUtils.isEmpty(token)){
//            mView.errorMessage("请先登录");
//            return false;
//        }
        return true;
    }

    private void openActivity(Class<?> pClass, IssueType type) {
        Intent intent = new Intent(mContext, pClass);
        intent.putExtra("",type);
        mContext.startActivity(intent);
        popupWindow.dismiss();
    }

}
