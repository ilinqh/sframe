package com.android.sframe.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by lin on 2017/9/6.
 * 功能：dp -> px , px -> dp , 获取屏幕宽度/ 高度
 */

public class ScreenUtils {
    // dp -> px
    public static int dp2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // px -> dp
    public static int px2pd(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
