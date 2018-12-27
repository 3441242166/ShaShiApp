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


    }

}
