package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recyclerviewdemo.adapter.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button addItem;
    private RecycleAdapter adapter;
    private List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRecycle();
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //       添加自带默认动画
                adapter.addData(list.size());

            }
        });
    }

    private void initRecycle() {
        // 纵向滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//   获取数据，向适配器传数据，绑定适配器
        list = initData();
        adapter = new RecycleAdapter(MainActivity.this, list);
        mRecyclerView.setAdapter(adapter);
//   添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void initView() {
        addItem = (Button) findViewById(R.id.add_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    protected ArrayList<String> initData() {
        ArrayList<String> mDatas = new ArrayList<String>();
        for (int i = 0; i < 1; i++) {
            mDatas.add("我是商品" + i);
        }
        return mDatas;
    }
}
