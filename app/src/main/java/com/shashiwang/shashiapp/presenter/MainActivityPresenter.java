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
import com.shashiwang.shashiapp.activity.LocationActivity;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.activity.postactivity.PostCarMessageActivity;
import com.shashiwang.shashiapp.activity.postactivity.PostCostActivity;
import com.shashiwang.shashiapp.activity.postactivity.PostDriverActivity;
import com.shashiwang.shashiapp.activity.postactivity.PostMaxFactoryActivity;
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
            openActivity(PostCarMessageActivity.class,IssueType.A);
        });
        stoneFactory.setOnClickListener(v -> {
            //openActivity(PostStoneFactoryActivity.class,IssueType.A);
            openActivity(LocationActivity.class,IssueType.A);

        });

        dirver.setOnClickListener(v -> {
            openActivity(PostDriverActivity.class,IssueType.A);
        });
        freight.setOnClickListener(v -> {
            openActivity(PostCostActivity.class,IssueType.A);
        });
        mixStation.setOnClickListener(v -> {
            openActivity(PostMaxFactoryActivity.class,IssueType.A);
        });
    }

    private void openActivity(Class<?> pClass, IssueType type) {
        Intent intent = new Intent(mContext, pClass);
        intent.putExtra("",type);
        mContext.startActivity(intent);
        popupWindow.dismiss();
    }

}
