package com.shashiwang.shashiapp.adapter.message;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.FreightMessage;

import java.util.List;

public class FreightAdapter extends BaseQuickAdapter<FreightMessage,BaseViewHolder> {


    public FreightAdapter(@Nullable List<FreightMessage> data) {
        super(R.layout.item_msg_freight, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FreightMessage item) {
                helper.setText(R.id.tv_title,""+item.getUser_id());
                helper.setText(R.id.tv_label,item.getCreated_at());
                helper.setText(R.id.tv_name_1,item.getCargo_name());
                helper.setText(R.id.tv_price,""+item.getPrice());
                helper.setText(R.id.tv_special,item.getCar_category());

        helper.setText(R.id.tv_content,item.getRemark());
    }


}
