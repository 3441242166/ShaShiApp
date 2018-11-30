package com.shashiwang.shashiapp.presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.IssueActivity;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.activity.PostStoneFactoryActivity;
import com.shashiwang.shashiapp.adapter.GridAdapter;
import com.shashiwang.shashiapp.adapter.TextAdapter;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.contant.IssueType;
import com.shashiwang.shashiapp.dialog.ChooseBottomDialog;
import com.shashiwang.shashiapp.view.IMainActivityView;
import com.shashiwang.shashiapp.view.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter extends IBasePresenter<IMainActivityView>{
    private static final String TAG = "MainActivityPresenter";

    public MainActivityPresenter(Context context, IMainActivityView view){
        this.mContext = context;
        this.mView = view;
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
            //openActivity(IssueActivity.class,IssueType.A);
            View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_bottom_choose, null);

            TextView txTitle = dialogView.findViewById(R.id.tx_dialog_title);
            RecyclerView rvView = dialogView.findViewById(R.id.rv_dialog_post);
            List<TextAdapter.TextBean> textBeanList = new ArrayList<>();
            for(int x=0;x<20;x++){
                textBeanList.add(new TextAdapter.TextBean(0,"aaaaaa"));
            }
            txTitle.setText("LaLaLa");
            rvView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
            TextAdapter textAdapter = new TextAdapter(textBeanList,mContext);
            rvView.setAdapter(textAdapter);

            ChooseBottomDialog dialog = new ChooseBottomDialog(mContext);
            dialog.show();
        });
        stoneFactory.setOnClickListener(v -> {
            openActivity(PostStoneFactoryActivity.class,IssueType.A);
        });

        dirver.setOnClickListener(v -> {
            openActivity(IssueActivity.class,IssueType.A);
        });
        freight.setOnClickListener(v -> {
            openActivity(IssueActivity.class,IssueType.A);
        });
        mixStation.setOnClickListener(v -> {
            openActivity(IssueActivity.class,IssueType.A);
        });
    }

    private void openActivity(Class<?> pClass, IssueType type) {
        Intent intent = new Intent(mContext, pClass);
        intent.putExtra("",type);
        mContext.startActivity(intent);
        popupWindow.dismiss();
    }
}
