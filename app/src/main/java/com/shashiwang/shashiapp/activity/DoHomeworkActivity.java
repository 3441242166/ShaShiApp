package com.shashiwang.shashiapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.QuestionAdapter;
import com.shashiwang.shashiapp.bean.Question;

import java.util.ArrayList;
import java.util.List;

public class DoHomeworkActivity extends AppCompatActivity {

    private QuestionAdapter adapter = new QuestionAdapter(null,this);
    private PagerSnapHelper scrollHelper = new PagerSnapHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_homework);
        RecyclerView recyclerView = findViewById(R.id.ac_dohomework_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        scrollHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        initData();
    }

    private void initData() {
        List<Question> data = new ArrayList<>();
        data.add(new Question("在人的一生中，脑发育的最关键时期是",
                "胎儿期和婴儿期","婴儿期和儿童期",
                "儿童期和青春期","儿童期和青春期"));
        data.add(new Question("绿色食品、有机食品、无公害农产品标准对产品的要求由高到低依次排列为",
                "绿色食品、有机食品、无公害食品","有机食品、绿色食品、无公害食品",
                "绿色食品、无公害食品、有机食品","无公害食品、有机食品、绿色食品"));
        data.add(new Question("在人的一生中，脑发育的最关键时期是",
                "胎儿期和婴儿期","婴儿期和儿童期",
                "儿童期和青春期","儿童期和青春期"));
        data.add(new Question("绿色食品、有机食品、无公害农产品标准对产品的要求由高到低依次排列为",
                "绿色食品、有机食品、无公害食品","有机食品、绿色食品、无公害食品",
                "绿色食品、无公害食品、有机食品","无公害食品、有机食品、绿色食品"));
        data.add(new Question("在人的一生中，脑发育的最关键时期是",
                "胎儿期和婴儿期","婴儿期和儿童期",
                "儿童期和青春期","儿童期和青春期"));
        data.add(new Question("绿色食品、有机食品、无公害农产品标准对产品的要求由高到低依次排列为",
                "绿色食品、有机食品、无公害食品","有机食品、绿色食品、无公害食品",
                "绿色食品、无公害食品、有机食品","无公害食品、有机食品、绿色食品"));
        adapter.setNewData(data);
    }
}
