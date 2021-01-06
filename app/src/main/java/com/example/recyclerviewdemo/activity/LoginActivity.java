package com.example.recyclerviewdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private CheckBox autoLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        registerListener();
//        isShowPwd(showPwd);//设置密码显示或不显示

        //判断记住密码多选框的状态
        if(sharedPreferences.getBoolean("remember_password", false))
        {
            //设置默认是记录密码状态
            rememberPwd.setChecked(true);
            usernameEdit.setText(sharedPreferences.getString("USER_NAME", ""));
            passwordEdit.setText(sharedPreferences.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if(sharedPreferences.getBoolean("auto_login", false))
            {
                //设置默认是自动登录状态
                autoLogin.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }

        //监听记住密码多选框按钮事件
        rememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rememberPwd.isChecked())
                    sharedPreferences.edit().putBoolean("remember_password", true).commit();
                else
                    sharedPreferences.edit().putBoolean("remember_password", false).commit();
            }
        });

        //监听自动登录多选框事件
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (autoLogin.isChecked())
                    sharedPreferences.edit().putBoolean("auto_login", true).commit();
                else
                    sharedPreferences.edit().putBoolean("auto_login", false).commit();
            }
        });

    }

    private void initViews() {
        usernameEdit = (MaterialEditText)findViewById(R.id.login_username);
        passwordEdit = (MaterialEditText)findViewById(R.id.login_password);
        loginBt = (Button)findViewById(R.id.login_button);
        registerTv = (TextView) findViewById(R.id.goto_register);
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        rememberPwd = (CheckBox) findViewById(R.id.remember_password);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);

        //获得实例对象
        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
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
                    intent.putExtra("login_user", username);
                    startActivity(intent);
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    //检查复选框是否被选中
                    if (rememberPwd.isChecked()) {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_name", usernameEdit.getText().toString());
                        editor.putString("user_password",passwordEdit.getText().toString());
                        editor.commit();

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
