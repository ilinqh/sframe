package com.android.sframe.adapter;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lin on 2017/9/7.
 * 功能：自定义 ViewHolder，可根据资源 id 获取 View
 */

public class ViewHolder {

    /**
     * 使用稀疏数组存放 View
     */
    private SparseArray<View> viewHolder;

    /**
     * 通过 item 的资源文件实例化出来的 View
     */
    private View view;

    /**
     * 私有的构造方法，实例化了稀疏数组，并把它设置为 View 的 Tag
     *
     * @param view
     */
    private ViewHolder(View view) {
        this.view = view;
        viewHolder = new SparseArray<>();
        view.setTag(viewHolder);
    }

    public static ViewHolder getViewHolder(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    /**
     * 根据资源 id 获取 View，如果 ViewHolder 数组中没有，则在 View 中查找并放入 ViewHolder 数组
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T get(@IdRes int id) {
        View childView = viewHolder.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * 获取重用的 View
     *
     * @return
     */
    public View getConvertView() {
        return view;
    }

    /**
     * 封装上面的get方法，调用
     *
     * @param id
     * @return
     */
    public TextView getTextView(@IdRes int id) {
        return get(id);
    }

    public Button getButton(@IdRes int id) {
        return get(id);
    }

    public ImageView getImageView(@IdRes int id) {
        return get(id);
    }

    public void setTextView(@IdRes int id, CharSequence charSequence) {
        getTextView(id).setText(charSequence);
    }

}
