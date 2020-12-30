package com.example.recyclerviewdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.BaseApplication;
import com.example.recyclerviewdemo.MainActivity;
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
    private List<Circle> list;
    private Context context;
    public RecycleAdapter(Context context, List<Circle> list) {
        this.context = context;
        this.list = list;
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
        holder.item_content.setText(list.get(position).getContent());
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 1) {
                    Snackbar.make(v, "此条目不能删除", Snackbar.LENGTH_SHORT).show();
                } else {
                    removeData(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     * @param mData
     */
    public void setNewData(List<Circle> mData) {

        list.clear();
        list.addAll(mData);
        notifyDataSetChanged();
    }


    /**
     * 添加单个数据到指定位置
     *
     * @param data
     * @param position
     */
    public void insert(Circle data, int position) {
        if (position > list.size() || position < 0) {
            return;
        }
        list.add(position, data);
        notifyItemInserted(position);
    }

    // 添加数据
    public void addData(int position) {
//   在list中添加数据，并通知条目加入一条

        CircleUtil.insertCircle("我是商品" + position, null);
        list = LitePal.findAll(Circle.class);

//        list.add(position, "我是商品" + position);
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
            list.remove(position);
            //删除动画
//        notifyItemRemoved(position);
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
        ExpandTextView item_content;
        TextView item_delete;
        //因为删除有可能会删除中间条目，然后会造成角标越界，所以必须整体刷新一下！
        public MyViewHolder(View view) {
            super(view);
            item_content = (ExpandTextView) view.findViewById(R.id.tv_content);
            item_delete = (TextView) view.findViewById(R.id.tv_delete);
        }
    }
}

