package com.shashiwang.shashiapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;

import java.util.List;

public class LocationAdapter extends BaseQuickAdapter<LocationAdapter.PoiBean,BaseViewHolder> {

    private Context context;

    public LocationAdapter(@Nullable List<PoiBean> data, Context context) {
        super(R.layout.item_location, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiBean item) {
        helper.setText(R.id.tx_loction_title,item.info.name);
        helper.setText(R.id.tx_loction_explain,item.info.address);
    }


    public static class PoiBean {
        boolean isSelect;
        PoiInfo info;

        public PoiBean(PoiInfo info){
            this.isSelect = false;
            this.info = info;
        }
    }

}
