package com.shashiwang.shashiapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;

import java.util.List;

public class GridAdapter extends BaseQuickAdapter<GridAdapter.GridBean,BaseViewHolder> {

    private Context context;

    public GridAdapter(@Nullable List<GridBean> data, Context context) {
        super(R.layout.item_grid, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GridBean item) {
        helper.setText(R.id.tv_item_title,item.name);
        Glide.with(context).load(item.imgID).into((ImageView) helper.getView(R.id.iv_item_grid));
    }


    public static class GridBean {
        int imgID;
        String name;

        public GridBean(int imgID,String name){
            this.imgID = imgID;
            this.name = name;
        }

    }

}
