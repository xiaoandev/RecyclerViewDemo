package com.example.recyclerviewdemo.bean;

import android.widget.ImageView;

import org.litepal.crud.LitePalSupport;

public class User extends LitePalSupport {
    /**
     * 用户 ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 用户头像
     */
    private ImageView userIcon;
    /**
     * 性别
     */
    private String userSex;
    /**
     * 个性签名
     */
    private String userSignature;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    private String userPwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public ImageView getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(ImageView userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }
}
