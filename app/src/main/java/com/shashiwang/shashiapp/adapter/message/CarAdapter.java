package com.shashiwang.shashiapp.adapter.message;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.List;

public class CarAdapter extends BaseQuickAdapter<CarMessage,BaseViewHolder> {


    public CarAdapter(@Nullable List<CarMessage> data) {
        super(R.layout.item_msg_car, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarMessage item) {
        helper.addOnClickListener(R.id.iv_phone);

        helper.setText(R.id.tv_title,StringUtil.getFirstChinese(item.getLinkman())+"先生");
        helper.setText(R.id.tv_label,DateUtil.getDifferentString(item.getCreated_at()));

        helper.setText(R.id.tv_name,item.getBrand());
        helper.setText(R.id.tv_price,""+item.getPrice());

        helper.setText(R.id.tv_type,item.getFactory_year());
        helper.setText(R.id.tv_kilometre,""+item.getMileage()+"公里");
        helper.setText(R.id.tv_content,item.getRemark());
    }

}
