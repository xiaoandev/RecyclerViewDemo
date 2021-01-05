package com.example.recyclerviewdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.MaterialEditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.bean.User;
import com.example.recyclerviewdemo.utils.UserHelpUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private MaterialEditText usernameEdit;
    private MaterialEditText passwordEdit;
    private Button loginBt;
    private TextView registerTv;
    private TextView forgetPwd;
    private CheckBox rememberPwd;
    private CheckBox showPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        registerListener();
//        isShowPwd(showPwd);//设置密码显示或不显示
    }

    private void initViews() {
        usernameEdit = (MaterialEditText)findViewById(R.id.login_username);
        passwordEdit = (MaterialEditText)findViewById(R.id.login_password);
        loginBt = (Button)findViewById(R.id.login_button);
        registerTv = (TextView) findViewById(R.id.goto_register);
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        rememberPwd = (CheckBox)findViewById(R.id.remember_password);
        showPwd = (CheckBox)findViewById(R.id.show_password);
    }

    private void registerListener() {
        loginBt.setOnClickListener(this);
        registerTv.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
    }

    //设置密码是否可见
    private void isShowPwd(CheckBox showPwd) {
        showPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //显示明文，即设置为可见的密码
                    passwordEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //将光标移至末尾
                } else {
                    //不显示明文，即设置为不可见的密码
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //将光标移至末尾
                }
                passwordEdit.setSelection(passwordEdit.getText().length());
            }
        });
    }

    private void login_judge() {
        if (isUserNameAndPwdValid()) {
            String username = usernameEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();
            User user = UserHelpUtil.findUserByName(username);
            if (user != null) {
                boolean flag = UserHelpUtil.findNameAndPwd(username, password);
                if (flag) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("current_user", username);
                    startActivity(intent);
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    //检查复选框是否被选中
                    if (!rememberPwd.isChecked()) {
                        usernameEdit.setText("");
                        passwordEdit.setText("");
                    }
                } else {
                    Toast.makeText(this, "用户名或密码不正确！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "用户名不存在！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //判断信息是否填写完整
    private boolean isUserNameAndPwdValid() {
        if (usernameEdit.getText().toString().trim().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordEdit.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                login_judge();
                //Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.goto_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_pwd:
//                Intent findPwdIntent = new Intent(LoginActivity.this, PhoneVerifyActivity.class);
//                startActivity(findPwdIntent);
                break;
            default:
                break;
        }
    }
}
