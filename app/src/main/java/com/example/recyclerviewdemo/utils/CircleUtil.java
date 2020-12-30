package com.example.recyclerviewdemo.utils;

import android.widget.ImageView;

import com.example.recyclerviewdemo.bean.Circle;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class CircleUtil {

    /**
     * 添加数据
     * @param content
     * @return
     */
    public static boolean insertCircle(String content) {
        Circle circle = null;
        if (content != null)
            circle.setContent(content);
//        if (imageView != null)
//            circle.setImageView(imageView);

        //创建数据库
        LitePal.getDatabase();
        return circle.save();
    }

    /**
     * 删除指定的一条数据
     * @param position
     */
    public static void deleteCircle(int position) {
        LitePal.delete(Circle.class, position);
//        LitePalSupport.delete(Circle.class, position);
    }


    public static List<Circle> findAllCircle() {
        List<Circle> circles = LitePal.findAll(Circle.class);
        return circles;
    }
}
