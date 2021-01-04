package com.example.recyclerviewdemo.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.bean.Circle;
import com.example.recyclerviewdemo.utils.CircleUtil;
import com.example.recyclerviewdemo.view.ExpandTextView;
import com.google.android.material.snackbar.Snackbar;

import org.litepal.LitePal;

import java.util.List;
/**
 * Created by qzs on 2017/9/04.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private List<Circle> circleList;
    private Context context;

    public RecycleAdapter() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 获取全部数据
     * @return
     */
    public List<Circle> getCircleList() {
        return circleList;
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     * @param circleList
     */
    public void setCircleList(List<Circle> circleList) {

        this.circleList = circleList;
//        circleList.clear();
//        circleList.addAll(mData);
//        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_video, parent,
                false));
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.item_content.setText(circleList.get(position).getContent());
        holder.item_photos.setData(circleList.get(position).getPhotos());
        holder.item_publish_time.setText(circleList.get(position).getPublishTime());
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleList.size() == 1) {
                    Snackbar.make(v, "此条目不能删除", Snackbar.LENGTH_SHORT).show();
                } else {
                    removeData(position);
                    int rowAffect = LitePal.delete(Circle.class, circleList.get(position).getId());
                    Log.d("Adapter", "remove " + rowAffect);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return circleList.size();
    }

    /**
     * 添加单个数据到指定位置
     *
     * @param data
     * @param position
     */
    public void insert(Circle data, int position) {
        if (position > circleList.size() || position < 0) {
            return;
        }
        circleList.add(position, data);
        notifyItemInserted(position);
    }

    // 添加数据
    public void addData(int position) {
//   在circleList中添加数据，并通知条目加入一条

        CircleUtil.insertCircle("我是商品" + position);
        circleList = LitePal.findAll(Circle.class);

//        circleList.add(position, "我是商品" + position);
        //添加动画
        notifyItemInserted(position);
    }

    // 删除数据
    public void removeData(int position) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("提示");
        alert.setMessage("你确定要删除吗?");
        alert.setNegativeButton("取消", null);
        alert.setPositiveButton("确定", (dialog, which) -> {
            circleList.remove(position);
            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            //删除动画
            notifyItemRemoved(position);
            notifyDataSetChanged();
            //调接口删除,这里写死
            dialog.dismiss();
        });
        alert.show();
    }

    /**
     * ViewHolder的类，用于缓存控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        //发布内容
        ExpandTextView item_content;
        //图片
        BGANinePhotoLayout item_photos;
        //删除按钮
        TextView item_delete;
        //发布时间
        TextView item_publish_time;
        //因为删除有可能会删除中间条目，然后会造成角标越界，所以必须整体刷新一下！
        public MyViewHolder(View view) {
            super(view);
            item_content = (ExpandTextView) view.findViewById(R.id.tv_content);
            item_photos = (BGANinePhotoLayout) view.findViewById(R.id.item_photos);
            item_delete = (TextView) view.findViewById(R.id.tv_delete);
            item_publish_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}

