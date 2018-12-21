package com.shashiwang.shashiapp.adapter.message;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.StationMessage;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.List;

public class StationAdapter extends BaseQuickAdapter<StationMessage,BaseViewHolder> {


    public StationAdapter(@Nullable List<StationMessage> data) {
        super(R.layout.item_msg_station, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StationMessage item) {
        helper.addOnClickListener(R.id.iv_phone);

        helper.setText(R.id.tv_title,StringUtil.getFirstChinese(item.getLinkman())+"先生");
        helper.setText(R.id.tv_label,DateUtil.getDifferentString(item.getCreated_at()));

        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_price,""+item.getCategory_price());

        helper.setText(R.id.tv_address,item.getLocation_lat());

        helper.setText(R.id.tv_content,item.getRemark());
    }

}