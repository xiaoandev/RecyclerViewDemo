package com.example.recyclerviewdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.bean.Circle;

public class AddCircleActivity extends AppCompatActivity {

    private final static String TAG = AddCircleActivity.class.getSimpleName();

    private EditText et_circle_text;
    private Button btn_add_circle;
    private Button btn_picture_test;
    private String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);

        initView();

        btn_add_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userNameIntent = getIntent();
                currentUserName = userNameIntent.getStringExtra("current_user");
                Circle circle= new Circle();
                circle.setUserName(currentUserName);
                circle.setContent(et_circle_text.getText().toString());
                circle.setPublishTime(System.currentTimeMillis());
//                Circle circle = new Circle(et_circle_text.getText().toString());
                boolean isSave =  circle.save();
                Log.d(TAG, "Is save success ? ---- " + isSave);

                Intent intent = new Intent(AddCircleActivity.this, MainActivity.class);
//                setResult(RESULT_OK,intent);
                startActivity(intent);
//                AddCircleActivity.this.finish();
            }
        });

        btn_picture_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(AddCircleActivity.this, SystemGalleryActivity.class);
                startActivity(backIntent);
            }
        });
    }

    private void initView() {
        et_circle_text = (EditText) findViewById(R.id.et_circle_text);
        btn_add_circle = (Button) findViewById(R.id.btn_add_circle);
        btn_picture_test = (Button) findViewById(R.id.btn_picture_test);
    }

}