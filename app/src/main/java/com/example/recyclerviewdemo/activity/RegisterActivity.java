package com.example.recyclerviewdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.MaterialEditText;
import android.widget.Toast;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.bean.User;
import com.example.recyclerviewdemo.utils.UserHelpUtil;

/**
 * 注册界面代码
 * @author xiaoandev
 * @since 2020-12-14
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 注册用户名
     */
    private MaterialEditText registerUserName;
    /**
     * 手机号
     */
    private MaterialEditText registerPhone;
    /**
     * 注册用户密码
     */
    private MaterialEditText registerPwd;
    /**
     * 再次输入密码
     */
    private MaterialEditText registerPwdCheck;
    /**
     * 注册按钮
     */
    private Button registerBt;
    /**
     * 返回登录界面按钮
     */
    private Button backLoginBt;
    /**
     * 显示密码
     */
    private CheckBox registerShowPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化控件
        initViews();
        registerListener();
        isShowPwd(registerShowPwd);
    }

    private void initViews() {
        registerUserName = (MaterialEditText) findViewById(R.id.register_username);
        registerPhone = (MaterialEditText) findViewById(R.id.register_phone);
        registerPwd = (MaterialEditText) findViewById(R.id.register_password);
        registerPwdCheck = (MaterialEditText) findViewById(R.id.register_again_password);
        registerBt = (Button) findViewById(R.id.register_ok_button);
        backLoginBt = (Button) findViewById(R.id.register_back);
        registerShowPwd = (CheckBox) findViewById(R.id.register_show_password);

    }

    private void registerListener(){
        registerBt.setOnClickListener(this);
        backLoginBt.setOnClickListener(this);
    }

    /**
     * 设置密码是否可见
     * @param showPwd
     */
    private void isShowPwd(CheckBox showPwd){
        showPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //显示明文，即设置为可见的密码
                    registerPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    registerPwdCheck.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //将光标移至末尾
                } else {
                    //不显示明文，即设置为不可见的密码
                    registerPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    registerPwdCheck.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //将光标移至末尾
                }
                registerPwd.setSelection(registerPwd.getText().length());
                registerPwdCheck.setSelection(registerPwdCheck.getText().length());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_ok_button:
                register_check();
                break;
            case R.id.register_back:
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            default:
                break;
        }
    }

    /**
     * 对填写的注册信息进行检查
     */
    private void register_check() {
        if (isUserNameAndPwdValid()) {
            String name = registerUserName.getText().toString().trim();
            String phone = registerPhone.getText().toString().trim();
            String password = registerPwd.getText().toString().trim();
            String rePwd = registerPwdCheck.getText().toString().trim();
            User user = UserHelpUtil.findUserByName(name);
            if (user != null) {
                Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
                return;
            } else if (!UserHelpUtil.isPhoneNumber(phone)) {
                Toast.makeText(this, "输入的手机号格式不正确！", Toast.LENGTH_SHORT).show();
            } else {
                if (!password.equals(rePwd)) {
                    Toast.makeText(this, "两次输入的密码不匹配！", Toast.LENGTH_SHORT).show();
                } else {
                    boolean flag = UserHelpUtil.insertUser(name, phone, password);
                    if (flag) {
                        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent backIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(backIntent);
                        //finish();
                    } else {
                        Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        }
    }

    /**
     * 判断用户信息是否填写完整
     * @return
     */
    private boolean isUserNameAndPwdValid() {
        if (registerUserName.getText().toString().trim().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (registerPhone.getText().toString().trim().equals("")) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (registerPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (registerPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, "再次输入密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
