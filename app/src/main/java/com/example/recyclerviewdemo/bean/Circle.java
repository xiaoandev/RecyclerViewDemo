package com.example.recyclerviewdemo.bean;

import android.widget.ImageView;

import org.litepal.crud.LitePalSupport;

public class Circle extends LitePalSupport {

    /**
     * 文字内容
     */
    private String content;
    /**
     * 图片
     */
    private ImageView imageView;

    public Circle(String content, ImageView imageView) {
        this.content = content;
        this.imageView = imageView;
    }

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
