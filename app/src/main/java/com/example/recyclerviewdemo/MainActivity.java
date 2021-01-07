package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionsMenu actionMenu;
    private FloatingActionButton publishCircle;
    private FloatingActionButton loginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        actionMenu = (FloatingActionsMenu) findViewById(R.id.action_menu);
        publishCircle = (FloatingActionButton) findViewById(R.id.publish_circle);
        loginOut = (FloatingActionButton) findViewById(R.id.login_out);

        publishCircle.setOnClickListener(this);
        loginOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_circle:
                Toast.makeText(MainActivity.this, "点击了发布信息按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_out:
                Toast.makeText(MainActivity.this, "点击了退出登录按钮", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}