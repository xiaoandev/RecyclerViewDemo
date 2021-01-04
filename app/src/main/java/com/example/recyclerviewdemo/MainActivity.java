package com.example.recyclerviewdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerviewdemo.adapter.RecycleAdapter;
import com.example.recyclerviewdemo.bean.Circle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addItem;
    private RecycleAdapter adapter;
    private List<Circle> list = new ArrayList<Circle>();
    private RecyclerView mRecyclerView;
    private EditText inputSearch;
    private ImageView searchCircle;
    private TextView noData;


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
            }
        });

        searchCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHasData = searchData(mRecyclerView);
                if (isHasData) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
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
        addItem = (FloatingActionButton) findViewById(R.id.add_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        inputSearch = (EditText) findViewById(R.id.input_search);
        searchCircle = (ImageView) findViewById(R.id.search_circle);
        noData = (TextView) findViewById(R.id.no_data);
    }

    /**
     * 初始化数据
     * @param view
     */
    private void initData(RecyclerView view) {

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
    }

    /**
     * 搜索满足条件的数据
     * @param view
     */
    private boolean searchData(RecyclerView view) {

        String searchText = inputSearch.getText().toString().trim();
        if (!searchText.equals(null)) {
            List<Circle> circles = LitePal.where("content like ?", "%" + searchText + "%").find(Circle.class);
//        List<Circle> circles = LitePal.findAll(Circle.class);
            if (circles.size() == 0) {
                return false;
            }

            List<Circle> searchDataList = new ArrayList<>();

            for (Circle circle : circles) {
                searchDataList.add(0, circle);
                adapter.notifyItemInserted(0);
                mRecyclerView.getLayoutManager().scrollToPosition(0);
            }
            adapter.setCircleList(searchDataList);
            view.setAdapter(adapter);
        } else
            Toast.makeText(MainActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initData(mRecyclerView);
        }
    }
}
