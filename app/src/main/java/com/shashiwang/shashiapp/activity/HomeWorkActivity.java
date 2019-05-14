package com.shashiwang.shashiapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.HomeworkAdapter;
import com.shashiwang.shashiapp.bean.Homework;

import java.util.ArrayList;
import java.util.List;

public class HomeWorkActivity extends AppCompatActivity {

    HomeworkAdapter adapter = new HomeworkAdapter(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        RecyclerView recyclerView = findViewById(R.id.ac_homeword_recycler);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent  = new Intent(HomeWorkActivity.this, DoHomeworkActivity.class);
            startActivity(intent);
        });


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        initData();
    }

    private void initData() {
        List<Homework> data = new ArrayList<>();

        data.add(new Homework("生活常识"));
        data.add(new Homework("科普常识"));
        data.add(new Homework("历史常识"));
        data.add(new Homework("动物常识"));
        data.add(new Homework("生活常识"));
        data.add(new Homework("科普常识"));
        data.add(new Homework("历史常识"));
        data.add(new Homework("动物常识"));
        data.add(new Homework("生活常识"));
        data.add(new Homework("科普常识"));
        data.add(new Homework("历史常识"));
        data.add(new Homework("动物常识"));
        data.add(new Homework("生活常识"));
        data.add(new Homework("科普常识"));
        data.add(new Homework("历史常识"));
        data.add(new Homework("动物常识"));
        data.add(new Homework("生活常识"));
        data.add(new Homework("科普常识"));
        data.add(new Homework("历史常识"));
        data.add(new Homework("动物常识"));

        adapter.setNewData(data);
    }
}
