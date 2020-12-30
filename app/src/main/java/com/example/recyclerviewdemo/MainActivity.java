package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recyclerviewdemo.adapter.RecycleAdapter;
import com.example.recyclerviewdemo.bean.Circle;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button addItem;
    private RecycleAdapter adapter;
    private List<Circle> list = new ArrayList<Circle>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        LitePal.getDatabase();
        initRecycle();
//        initData();
//        List<Circle> circles = DataSupport.findAll(Circle.class);
//        adapter.setNewData(circles);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Circle circle = new Circle("dasda", null);
                adapter.insert(circle, 0);

            }
        });
    }

    private void initRecycle() {
        // 纵向滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//   获取数据，向适配器传数据，绑定适配器
        list = initData();
//        list = CircleUtil.findAllCircle();
        adapter = new RecycleAdapter(MainActivity.this, list);
        mRecyclerView.setAdapter(adapter);
//   添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void initView() {
        addItem = (Button) findViewById(R.id.add_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private List<Circle> initData() {
//        List<Circle> circles = new ArrayList<>();
        List<Circle> circles = LitePal.findAll(Circle.class);
        for (Circle circle : circles) {
            circles.add(circle);
        }

//        circles.add(new Circle("1", null));
//        circles.add(new Circle("2", null));
//        circles.add(new Circle("3", null));
//        circles.add(new Circle("4", null));
//        circles.add(new Circle("5", null));
//        circles.add(new Circle("6", null));
//        circles.add(new Circle("7", null));
//        circles.add(new Circle("8", null));
//        return DataSupport.findAll(Circle.class);
        return circles;
    }
}
