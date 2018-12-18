package com.shashiwang.shashiapp.adapter.message;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.util.DateUtil;

import java.util.List;

public class FreightAdapter extends BaseQuickAdapter<FreightMessage,BaseViewHolder> {


    public FreightAdapter(@Nullable List<FreightMessage> data) {
        super(R.layout.item_msg_freight, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FreightMessage item) {
        helper.addOnClickListener(R.id.iv_phone);

        helper.setText(R.id.tv_title,""+item.getUser_id());
        helper.setText(R.id.tv_label,DateUtil.getDifferentString(item.getCreated_at()));

        helper.setText(R.id.tv_name,item.getCargo_name());
        helper.setText(R.id.tv_price,""+item.getPrice());

        helper.setText(R.id.tv_start,item.getStart_location());
        helper.setText(R.id.tv_end,item.getEnd_location());

        helper.setText(R.id.tv_car,item.getCar_category());
        helper.setText(R.id.tv_distance,""+item.getDistance());

        helper.setText(R.id.tv_content,item.getRemark());
    }


}
