package com.shashiwang.shashiapp.adapter.message;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.DriverMessage;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.List;

public class DriverAdapter extends BaseQuickAdapter<DriverMessage,BaseViewHolder> {


    public DriverAdapter(@Nullable List<DriverMessage> data) {
        super(R.layout.item_msg_driver, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DriverMessage item) {
        helper.addOnClickListener(R.id.iv_phone);

        helper.setText(R.id.tv_title,StringUtil.getFirstChinese(item.getLinkman())+"先生");
        helper.setText(R.id.tv_label,DateUtil.getDifferentString(item.getCreated_at()));

        helper.setText(R.id.tv_years,""+item.getWork_year()+"年");
        helper.setText(R.id.tv_salary,""+item.getSalary());

        helper.setText(R.id.tv_address,item.getWork_address());

        helper.setText(R.id.tv_content,item.getJob_desc());
    }

}
