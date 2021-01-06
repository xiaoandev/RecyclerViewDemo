package com.example.recyclerviewdemo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.adapter.RecycleAdapter;
import com.example.recyclerviewdemo.bean.Circle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener , View.OnClickListener{

    private final static String TAG = MainActivity.class.getSimpleName();

    private FloatingActionButton addItemBtn;
    private RecycleAdapter adapter;
    private List<Circle> list = new ArrayList<Circle>();
    private RecyclerView mRecyclerView;
    private EditText inputSearchContent;
    private ImageView searchCircleBtn;
    private TextView noData;
    private SwipeRefreshLayout mSwipeLayout;
    private ImageView searchContentDelBtn;

    private String loginUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        LitePal.getDatabase();
        initRecycle();

        //绑定刷新时间
        mSwipeLayout.setOnRefreshListener(this);
        //设置颜色
        mSwipeLayout.setColorSchemeColors(Color.RED, Color.BLUE);

    }

    private void initRecycle() {
        // 纵向滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // 获取数据，向适配器传数据，绑定适配器
        adapter = new RecycleAdapter();
        initData(mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(circleItemClickListener);
    }

    private void initView() {
        addItemBtn = (FloatingActionButton) findViewById(R.id.add_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        inputSearchContent = (EditText) findViewById(R.id.input_search);
        searchCircleBtn = (ImageView) findViewById(R.id.search_circle);
        noData = (TextView) findViewById(R.id.no_data);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_circle);
        searchContentDelBtn = (ImageView) findViewById(R.id.iv_search_delete);

        addItemBtn.setOnClickListener(this);
        searchCircleBtn.setOnClickListener(this);
        searchContentDelBtn.setOnClickListener(this);
        inputSearchContent.addTextChangedListener(new EditChangedListener());
        Intent userNameIntent = getIntent();
        loginUserName = userNameIntent.getStringExtra("login_user");
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
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
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
            mRecyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            for (Circle circle : circles) {
                String mRealDate = calculatePublishTime(circle.getPublishTime());
                circle.setRealDate(mRealDate);
                dataList.add(0, circle);
                adapter.notifyItemInserted(0);
                mRecyclerView.getLayoutManager().scrollToPosition(0);
            }
            adapter.setCircleList(dataList);
            view.setAdapter(adapter);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 搜索满足条件的数据
     * @param view
     */
    private boolean searchData(RecyclerView view) {

        String searchText = inputSearchContent.getText().toString().trim();
        if (!searchText.equals(null)) {
            List<Circle> circles = LitePal.where("content like ?", "%" + searchText + "%").find(Circle.class);
//        List<Circle> circles = LitePal.findAll(Circle.class);
            if (circles.size() == 0) {
                return false;
            }

            List<Circle> searchDataList = new ArrayList<>();

            for (Circle circle : circles) {
                String mRealDate = calculatePublishTime(circle.getPublishTime());
                circle.setRealDate(mRealDate);
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

    /**
     * item＋item里的控件点击监听事件
     */
    private RecycleAdapter.OnItemClickListener circleItemClickListener = new RecycleAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.tv_delete:
                    if (adapter.isSameUser(loginUserName, position))
                        adapter.removeData(position);
                    else
                        Toast.makeText(MainActivity.this, "仅用户本人可删除!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.iv_edit:
                    adapter.showChoosePopupWindow(v, position);
//                    Toast.makeText(MainActivity.this, "你点击了编辑按钮"+(position+1), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(MainActivity.this,"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_item:
//                Intent intent = new Intent(MainActivity.this, MomentAddActivity.class);
                Intent intent = new Intent(MainActivity.this, AddCircleActivity.class);
                intent.putExtra("current_user", loginUserName);
                startActivityForResult(intent,1);
                break;
            case R.id.search_circle:
                boolean isHasData = searchData(mRecyclerView);
                if (!isHasData) {
                    mRecyclerView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_search_delete:
                inputSearchContent.setText("");
                searchContentDelBtn.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!"".equals(s.toString()))
                searchContentDelBtn.setVisibility(View.VISIBLE);
            else
                searchContentDelBtn.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initData(mRecyclerView);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //停止刷新
                mSwipeLayout.setRefreshing(false);
                //获取最新数据
                initData(mRecyclerView);
            }
        }, 3000);
    }
}
