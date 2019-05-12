package com.shashiwang.shashiapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.Count;

import java.util.List;

public class MapAdapter extends BaseQuickAdapter<Count, BaseViewHolder> {


    public MapAdapter(@Nullable List<Count> data) {
        super(R.layout.item_map, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Count item) {
        helper.setText(R.id.count,item.count);
        helper.setText(R.id.x,String.valueOf(item.lat));
        helper.setText(R.id.y,String.valueOf(item.lng));
    }

}