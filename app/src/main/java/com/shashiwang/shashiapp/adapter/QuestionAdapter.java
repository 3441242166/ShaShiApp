package com.shashiwang.shashiapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.Question;

import java.util.List;

/**
 * Created by wanhao on 2018/4/9.
 */

public class QuestionAdapter extends BaseQuickAdapter<Question, BaseViewHolder> {

    Context context;

    public QuestionAdapter(@Nullable List<Question> data, Context context) {
        super(R.layout.item_qustion,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Question item) {
        helper.setText(R.id.item_qustion_title,item.question);
        helper.setText(R.id.item_qustion_a,item.a);
        helper.setText(R.id.item_qustion_b,item.b);
        helper.setText(R.id.item_qustion_c,item.c);
        helper.setText(R.id.item_qustion_d,item.d);
    }
}
