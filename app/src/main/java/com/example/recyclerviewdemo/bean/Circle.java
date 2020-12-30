package com.example.recyclerviewdemo.bean;

import android.widget.ImageView;

import org.litepal.crud.LitePalSupport;

public class Circle extends LitePalSupport {


    private Long id;
    /**
     * 文字内容
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 图片
     */
//    private ImageView imageView;



    public Circle(String content) {
        this.content = content;
//        this.imageView = imageView;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public ImageView getImageView() {
////        return imageView;
////    }
////
////    public void setImageView(ImageView imageView) {
////        this.imageView = imageView;
////    }
}
