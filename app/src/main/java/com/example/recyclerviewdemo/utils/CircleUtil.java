package com.example.recyclerviewdemo.utils;

import android.widget.ImageView;

import com.example.recyclerviewdemo.bean.Circle;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

public class CircleUtil {

    /**
     * 添加数据
     * @param content
     * @param imageView
     * @return
     */
    public static boolean insertCircle(String content, ImageView imageView) {
        Circle circle = new Circle();
        if (content != null)
            circle.setContent(content);
        if (imageView != null)
            circle.setImageView(imageView);

        //创建数据库
        Connector.getDatabase();
        return circle.save();
    }

    /**
     * 删除指定的一条数据
     * @param position
     */
    public static void deleteCircle(int position) {
        DataSupport.delete(Circle.class, position);
    }
}
