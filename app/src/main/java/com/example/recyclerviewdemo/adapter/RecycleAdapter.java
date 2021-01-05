package com.example.recyclerviewdemo.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.bean.Circle;
import com.example.recyclerviewdemo.utils.CircleUtil;
import com.example.recyclerviewdemo.view.ChoosePopupWindow;
import com.example.recyclerviewdemo.view.ExpandTextView;
import com.example.recyclerviewdemo.view.OnPraiseOrCommentClickListener;
import com.google.android.material.snackbar.Snackbar;

import org.litepal.LitePal;

import java.util.List;
/**
 * Created by qzs on 2017/9/04.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private List<Circle> circleList;
    private Context context;
    private ChoosePopupWindow choosePopupWindow;

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
//        holder.item_photos.setData(circleList.get(position).getPhotos());
        holder.item_real_date.setText(circleList.get(position).getRealDate());
        holder.tv_name.setText(circleList.get(position).getUserName());
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

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePopupWindow(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return circleList == null ? 0 : circleList.size();
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
     * 获取控件左上顶点Y坐标
     *
     * @param view
     * @return
     */
    private int getCoordinateY(View view) {
        int[] coordinate = new int[2];
        view.getLocationOnScreen(coordinate);
        return coordinate[1];
    }

    /**
     * 点赞、评论弹框
     * @param position
     */
    private void showChoosePopupWindow(View view, int position) {
        //item 底部y坐标
//        final int mBottomY = getCoordinateY(view) + view.getHeight();
        if (choosePopupWindow == null) {
            choosePopupWindow = new ChoosePopupWindow(context);
        }
        choosePopupWindow.setOnPraiseOrCommentClickListener(new OnPraiseOrCommentClickListener() {
            @Override
            public void onPraiseClick(int position) {
                //调用点赞接口
//                getLikeData();
                choosePopupWindow.dismiss();
            }

            @Override
            public void onCommentClick(int position) {

//                llComment.setVisibility(View.VISIBLE);
//                etComment.requestFocus();
//                etComment.setHint("说点什么");
//                to_user_id = null;
//                KeyboardUtil.showSoftInput(this);
                choosePopupWindow.dismiss();
//                etComment.setText("");
//                view.postDelayed(() -> {
//                    int y = getCoordinateY(llComment) - 20;
//                    //评论时滑动到对应item底部和输入框顶部对齐
//                    recyclerView.smoothScrollBy(0, mBottomY - y);
//                }, 300);
            }

            @Override
            public void onClickFrendCircleTopBg() {

            }

            @Override
            public void onDeleteItem(String id, int position) {

            }
//            .setTextView(isLike).
        }).setCurrentPosition(position);
        if (choosePopupWindow.isShowing()) {
            choosePopupWindow.dismiss();
        } else {
            choosePopupWindow.showPopupWindow(view);
        }
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
        //用户名
        TextView tv_name;
        //点赞评论选择按钮
        ImageView iv_edit;
        //发布时间
        TextView item_real_date;
        //评论框
        LinearLayout ll_comment;

        //因为删除有可能会删除中间条目，然后会造成角标越界，所以必须整体刷新一下！
        public MyViewHolder(View view) {
            super(view);
            item_content = (ExpandTextView) view.findViewById(R.id.tv_content);
//            item_photos = (BGANinePhotoLayout) view.findViewById(R.id.item_photos);
            item_delete = (TextView) view.findViewById(R.id.tv_delete);
            iv_edit = (ImageView) view.findViewById(R.id.iv_edit);
            item_real_date = (TextView) view.findViewById(R.id.tv_time);
            ll_comment = (LinearLayout) view.findViewById(R.id.ll_comment);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}

