package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recyclerviewdemo.bean.Circle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCircleActivity extends AppCompatActivity {

    private final static String TAG = AddCircleActivity.class.getSimpleName();

    private EditText et_circle_text;
    private Button btn_add_circle;

    private  String startTime = "2020-01-04 16:14:35";//初始时间
    private long formattingTime;
    private long TimeCha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);

        initView();

        btn_add_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String publishTime = CalculateTime(startTime);
                Circle circle= new Circle();
                circle.setContent(et_circle_text.getText().toString());
                circle.setPublishTime(publishTime);
//                Circle circle = new Circle(et_circle_text.getText().toString());
                boolean isSave =  circle.save();
                Log.d(TAG, "publishTime ---- " + publishTime);
                Log.d(TAG, "Is save success ? ---- " + isSave);

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                AddCircleActivity.this.finish();
            }
        });
    }

    private void initView() {
        et_circle_text = (EditText) findViewById(R.id.et_circle_text);
        btn_add_circle = (Button) findViewById(R.id.btn_add_circle);
    }

    private String CalculateTime(String starTime) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = calendar.getTimeInMillis();//获取系统当先时间的毫秒值
        Log.d("作者测试时间的毫秒值",currentTime+"");
        try {//如果你的初始时间是格式化过的,则需要先转化成毫秒值
            formattingTime = dataFormat.parse(starTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //这里需要注意,因为获取的时间因为时差的缘故已经自动加上了8小时的时间,所以这里需要减去.如果不减去,就算是你刚刚发布的动态也会显示是8小时之前
        TimeCha = ((currentTime-formattingTime)/1000)-(8*60*60);//将毫秒值转化成分钟数

        StringBuffer str = new StringBuffer();
        if (TimeCha > 0 & TimeCha < 60){
            String just = str.append("刚刚").toString();
            Log.d("时间：",just);
            return just;
//            tv_time.setText(just);

        } else if (TimeCha >= 60 && TimeCha < 3600) {
            String minute = str.append(TimeCha / 60 + "分钟前").toString();
//            tv_time.setText(minute);
            return minute;

        } else if (TimeCha >= 3600 && TimeCha < 3600 * 24) {
            String hour = str.append(TimeCha / 3600 + "小时前").toString();
//            tv_time.setText(hour);
            return hour;

        } else if (TimeCha >= 3600 * 1 * 24 && TimeCha < 3600 * 2 * 24) {
            String day1 = str.append("1天前").toString();
//            tv_time.setText(day1);
            return day1;

        } else if (TimeCha >= 3600 * 2 * 24 && TimeCha < 3600 * 3 * 24) {
            String day2 = str.append("2天前").toString();
//            tv_time.setText(day2);
            return day2;

        } else if (TimeCha >= 3600 * 3 * 24 && TimeCha < 3600 * 4 * 24) {
            String day3 = str.append("3天前").toString();
//            tv_time.setText(day3);
            return day3;
        } else {
//            tv_time.setText(starTime);
            return starTime;
        }
    }
}