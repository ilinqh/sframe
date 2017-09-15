package com.android.sframe.util;

import android.content.Context;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lin on 2017/9/6.
 * 功能：提供两种提示方式
 * 第一种是 toast，封装了常用的 Toast 方法
 * 第二种是 Android 5.0 新增的 snackBar 提示
 */

public class TipUtils {

    private static Toast mToast;

    /**
     * 进行 Toast 提示
     *
     * @param context
     * @param content
     */
    public static void toast(Context context, String content) {
        if (null == mToast) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

    /**
     * 进行 SnackBar 提示，并提供点击按钮，前提是实现 onClickListener
     *
     * @param view
     * @param content
     */
    public static void snackBar(View view, String content) {
        Snackbar.make(view, content, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    public static void snackBar(View view, String content, String action, View.OnClickListener listener) {
        Snackbar.make(view, content, BaseTransientBottomBar.LENGTH_SHORT).setAction(action, listener).show();
    }

}
