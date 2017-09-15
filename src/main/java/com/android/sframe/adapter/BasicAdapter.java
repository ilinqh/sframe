package com.android.sframe.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin on 2017/9/7.
 * 功能：所有 Adapter 的基类
 */

public abstract class BasicAdapter extends BaseAdapter {

    /**
     * 需要展示的数据
     */
    public List<?> data;

    /**
     * item 的布局文件
     */
    @LayoutRes
    public int itemLayoutId;
    public Context context;
    private View view;

    public ViewHolder holder;

    public BasicAdapter(List<?> data, int itemLayoutId) {
        this.data = data;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);
            holder = ViewHolder.getViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        bind(holder, position);

        // 动画效果
        AlphaAnimation animation = new AlphaAnimation(0.6f, 1f);
        animation.setDuration(1000);
        convertView.startAnimation(animation);

        return convertView;
    }

    public abstract void bind(ViewHolder holder, int position);

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data, Long offset) {
        if (offset == 0) {
            this.data = data;
        } else {
            this.data.addAll((ArrayList) data);
        }

        this.notifyDataSetChanged();
    }

    public void setData(List<?> data) {
        setData(data, 0L);
    }

    public void clearData() {
        if (data == null) {
            return;
        }
        data.clear();
    }

    public Long getOffset() {
        if (data == null) {
            return 0L;
        }
        return Long.valueOf(data.size());
    }

    public View getView() {
        return view;
    }
}
