package com.example.recyclerviewdemo.utils;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;

import com.example.recyclerviewdemo.bean.User;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserHelpUtil {

    public static String currentUserPhone = "";
    public static String currentUserName = "";

    /**
     * 添加用户信息（注册用）
     * @param name
     * @param pwd
     * @return
     */
    public static boolean insertUser(String name, String phone, String pwd) {
        User user = new User();
        user.setUserName(name);
        user.setUserPhone(phone);
        user.setUserPwd(pwd);

        //创建数据库
        LitePal.getDatabase();
        return user.save();
    }

    /**
     * 通过用户ID，查看用户是否存在
     * @param userId
     * @return
     */
//    public static User findUserById(String userId) {
//        List<User> users = DataSupport.findAll(User.class);
//        for (User person: users) {
//            if (userId.equals(person.getUserId())) {
//                return person;
//            }
//        }
//        return null;
//    }

    /**
     * 通过查询手机号，查看用户是否存在
     * @param phone
     * @return
     */
    public static User findUserByPhone(String phone) {
        List<User> users = LitePal.findAll(User.class);
        for (User person: users) {
            if (phone.equals(person.getUserPhone())) {
                return person;
            }
        }
        return null;
    }

    /**
     * 查找用户名，查看用户名是否存在
     * @param name
     * @return
     */
    public static User findUserByName(String name) {
        List<User> users = LitePal.findAll(User.class);
        for (User person: users) {
            if (name.equals(person.getUserName())) {
                currentUserName = person.getUserName();
                currentUserPhone = person.getUserPhone();
                Log.d("UserHelpUtil", "currentUserName " + currentUserName);
                Log.d("UserHelpUtil", "currentUserPhone " + currentUserPhone);
                return person;
            }
        }
        return null;
    }

    /**
     * 查找用户名、密码是否正确匹配
     * @param name
     * @param password
     * @return
     */
    public static boolean findNameAndPwd(String name, String password) {
        List<User> users = LitePal.findAll(User.class);
        for (User person: users) {
            if (name.equals(person.getUserName())&&(password.trim().equals(person.getUserPwd()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断手机号是否符合规范
     * @param phoneNo 输入的手机号
     * @return
     */
    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }
}
