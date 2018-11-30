package com.shashiwang.shashiapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.TextAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseBottomDialog extends Dialog {

    @BindView(R.id.im_dialog_back)
    ImageView ivBack;
    @BindView(R.id.tx_dialog_title)
    TextView tvTitle;
    @BindView(R.id.rv_dialog_post)
    RecyclerView rvView;

    private Context context;

    public ChooseBottomDialog(Context context) {
        super(context, R.style.style_select_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_choose);//这行一定要写在前面
        ButterKnife.bind(this);

        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        setCancelable(true);//点击外部不可dismiss
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        rvView.setLayoutManager(new LinearLayoutManager(context));

        TextAdapter adapter = new TextAdapter(null,context);
        rvView.setAdapter(adapter);

    }


}
