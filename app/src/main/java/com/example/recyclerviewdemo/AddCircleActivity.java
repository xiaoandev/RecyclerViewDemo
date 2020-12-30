package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recyclerviewdemo.bean.Circle;

public class AddCircleActivity extends AppCompatActivity {

    private EditText et_circle_text;
    private Button btn_add_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);

        initView();

        btn_add_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Circle circle = new Circle(et_circle_text.getText().toString());
                circle.save();

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
}