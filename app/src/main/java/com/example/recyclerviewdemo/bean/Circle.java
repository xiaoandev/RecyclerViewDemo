package com.example.recyclerviewdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;

public class Circle extends LitePalSupport implements Parcelable {


    private Long id;
    /**
     * 文字内容
     */
    private String content;

    /**
     * 发布时间（毫秒）
     */
    private long publishTime;

    /**
     * 标准日期（年-月-日 格式）
     */
    private String realDate;

    /**
     * 图片
     */
    public ArrayList<String> photos;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeStringList(this.photos);
    }

    public Circle() {}

    public Circle(String content) {
        this.content = content;
    }

    public Circle(String content, ArrayList<String> photos) {
        this.content = content;
        this.photos = photos;
    }

    protected Circle(Parcel in) {
        this.content = in.readString();
        this.photos = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Circle> CREATOR = new Parcelable.Creator<Circle>() {
        @Override
        public Circle createFromParcel(Parcel source) {
            return new Circle(source);
        }

        @Override
        public Circle[] newArray(int size) {
            return new Circle[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getRealDate() {
        return realDate;
    }

    public void setRealDate(String realDate) {
        this.realDate = realDate;
    }
}
