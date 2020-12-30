package com.example.recyclerviewdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
                Intent intent = new Intent(MainActivity.this,AddCircleActivity.class);
                startActivityForResult(intent,1);
//                adapter.insert(circle, 0);
//                Circle circle = new Circle("dasda");
//                circle.save();
//                initData(mRecyclerView);
            }
        });
    }

    private void initRecycle() {
        // 纵向滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // 获取数据，向适配器传数据，绑定适配器
//        list = initData();
//        list = CircleUtil.findAllCircle();
        adapter = new RecycleAdapter();
//        mRecyclerView.setAdapter(adapter);
        //  添加动画

        initData(mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void initView() {
        addItem = (Button) findViewById(R.id.add_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private List<Circle> initData(RecyclerView view) {

        adapter.setContext(MainActivity.this);
        List<Circle> circles = LitePal.findAll(Circle.class);
        List<Circle> dataList = new ArrayList<>();
//        List circles = LitePal.order("id desc").find(Circle.class);

        for (Circle circle : circles) {
            dataList.add(0, circle);
            adapter.notifyItemInserted(0);
            mRecyclerView.getLayoutManager().scrollToPosition(0);
        }
        adapter.setCircleList(dataList);
        view.setAdapter(adapter);
        return circles;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initData(mRecyclerView);
        }
    }
}
