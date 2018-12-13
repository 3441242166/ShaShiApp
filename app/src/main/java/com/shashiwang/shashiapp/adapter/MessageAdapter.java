package com.shashiwang.shashiapp.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.MessageBean;

import java.util.List;

import static com.shashiwang.shashiapp.constant.MessageType.*;

public class MessageAdapter extends BaseMultiItemQuickAdapter<MessageBean,BaseViewHolder> {
    private static final String TAG = "MessageAdapter";

    public MessageAdapter(@Nullable List<MessageBean> data) {
        super(data);

        addItemType(POST, R.layout.item_msg_post);
        addItemType(CAR, R.layout.item_msg_car);
        addItemType(FACTORY, R.layout.item_msg_factory);
        addItemType(FREIGHT, R.layout.item_msg_freight);
        addItemType(DRIVER, R.layout.item_msg_driver);
        addItemType(STATION, R.layout.item_msg_station);

    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        Log.i(TAG, "convert: item.bean "+ (item.getBean() instanceof FreightMessage));
        Log.i(TAG, "convert: item.bean "+ (item.getBean() instanceof FreightMessage));
        Log.i(TAG, "convert: item.bean "+ (item.getBean() instanceof FreightMessage));

        switch (item.getItemType()){
            case POST:

                break;
            case CAR:

                break;
            case FREIGHT:
                FreightMessage message = (FreightMessage) item.getBean();
                if(message == null){
                    return;
                }
//                helper.setText(R.id.tv_title,message.getUser_id());
//                helper.setText(R.id.tv_time,message.getCreated_at());
//                helper.setText(R.id.tv_name_1,message.getCargo_name());
//                helper.setText(R.id.tv_price,message.getPrice());
//                helper.setText(R.id.tv_special,message.getCar_category());

                //helper.setText(R.id.tv_content,message.getRemark());
                break;
            case DRIVER:

                break;
            case FACTORY:

                break;
            case STATION:

                break;
        }
    }



}

