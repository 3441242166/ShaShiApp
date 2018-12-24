package com.shashiwang.shashiapp.dialog;


import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.GridAdapter;
import com.shashiwang.shashiapp.adapter.TextAdapter;
import com.shashiwang.shashiapp.base.BaseScreenDialog;
import com.shashiwang.shashiapp.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ChooseBottomDialog extends BaseScreenDialog {
    private static final String TAG = "ChooseBottomDialog";

    @BindView(R.id.im_dialog_back)
    ImageView ivBack;
    @BindView(R.id.tx_dialog_title)
    TextView tvTitle;
    @BindView(R.id.rv_dialog_post)
    ListView rvView;

    private TextAdapter adapter;
    private String title;
    private List<String> data;

    private OnChooseListener onChooseListener;

    public ChooseBottomDialog(Context context,String title,List<String> data) {
        super(context);
        this.title = title;
        this.data = data;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_bottom_choose;
    }

    private void initEvent() {

        rvView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.i(TAG, "click: ");
            if(onChooseListener != null){
                onChooseListener.onChoose(data.get(i),i);
            }
            ChooseBottomDialog.this.cancel();
        });

        ivBack.setOnClickListener(view -> ChooseBottomDialog.this.cancel());
    }

    private void initView() {
        tvTitle.setText(title);

        setCancelable(true);//点击外部不可dismiss
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);

        adapter = new TextAdapter();
        rvView.setAdapter(adapter);

    }

    public void setOnChooseListener(OnChooseListener onChooseListener){
        Log.i(TAG, "setOnChooseListener: ");
        this.onChooseListener = onChooseListener;
    }

    public interface OnChooseListener{
        void onChoose(String str,int pos);
    }

    class  TextAdapter extends BaseAdapter {
         private static final String TAG = "TextAdapter";

         @Override
         public int getCount() {
             return data.size();
         }

         @Override
         public Object getItem(int i) {
             return data.get(i);
         }

         @Override
         public long getItemId(int i) {
             return i;
         }

         @Override
         public View getView(int i, View view, ViewGroup viewGroup) {
             LayoutInflater inflater = LayoutInflater.from(getContext());
             view = inflater.inflate(R.layout.item_text, null, true);

             TextView textView = view.findViewById(R.id.tv);
             textView.setText(data.get(i));

             return view;
         }
     }


}
