package com.example.recyclerviewdemo.bean;

import android.widget.ImageView;

import org.litepal.crud.DataSupport;

public class Circle extends DataSupport {

    /**
     * 文字内容
     */
    private String content;
    /**
     * 图片
     */
    private ImageView imageView;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
