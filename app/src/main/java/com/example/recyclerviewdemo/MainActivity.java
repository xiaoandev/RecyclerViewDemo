package com.example.recyclerviewdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerviewdemo.activity.MomentAddActivity;
import com.example.recyclerviewdemo.adapter.RecycleAdapter;
import com.example.recyclerviewdemo.bean.Circle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

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
//                Intent intent = new Intent(MainActivity.this, MomentAddActivity.class);
                Intent intent = new Intent(MainActivity.this,AddCircleActivity.class);
                startActivityForResult(intent,1);
            }
        });

        searchCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHasData = searchData(mRecyclerView);
                if (!isHasData) {
                    mRecyclerView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
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

    private String calculatePublishTime(long publishTime) {

        long currentTime = System.currentTimeMillis();
        long timeCha = (currentTime - publishTime) / 1000; // 将毫秒转换成秒
        Log.d(TAG, "timeCha is ---- " + timeCha + " 秒");

        if (timeCha >= 0 & timeCha < 60) {
            return "刚刚";
        } else if (timeCha >= 60 && timeCha < 3600) {
            return (timeCha / 60) + "分钟前";
        } else if (timeCha >= 3600 && timeCha < 3600 * 24) {
            return (timeCha / 3600) + "小时前";
        } else if (timeCha >= 3600 * 1 * 24 && timeCha < 3600 * 2 * 24) {
            return "1天前";
        } else if (timeCha >= 3600 * 2 * 24 && timeCha < 3600 * 3 * 24) {
            return "2天前";
        } else if (timeCha >= 3600 * 3 * 24 && timeCha < 3600 * 4 * 24) {
            return "3天前";
        } else {
//            System.setProperty("user.timezone", "Asia/Shanghai");
//            TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
//            TimeZone.setDefault(timeZone);
////            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date currentDate = new Date(publishTime);
//            String dataStr = simpleDateFormat.format(currentDate);
//            System.out.println("currentTime="+currentTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dataStr = dateFormat.format(publishTime);
            Log.d(TAG, "publishTime is ----" + dataStr);
            return dataStr;
        }
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

        if (!circles.equals(null)) {
            for (Circle circle : circles) {
                String mRealDate = calculatePublishTime(circle.getPublishTime());
                circle.setRealDate(mRealDate);
                dataList.add(0, circle);
                adapter.notifyItemInserted(0);
                mRecyclerView.getLayoutManager().scrollToPosition(0);
            }
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
