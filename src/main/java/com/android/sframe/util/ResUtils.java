package com.android.sframe.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.android.sframe.config.App;

/**
 * Created by lin on 2017/9/7.
 * 功能：根据资源 id 直接获取资源
 */

public class ResUtils {

    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId) {
        return App.getContext().getString(resId);
    }

    public static String getString(@StringRes int resId, Object... args) {
        return String.format(getString(resId), args);
    }

    /**
     * 获取颜色
     *
     * @param resId
     * @return
     */
    @ColorInt
    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(App.getContext(), resId);
    }

    public static float getDimen(@DimenRes int resId) {
        return App.getContext().getResources().getDimension(resId);
    }

    public static int getAttr(int resId) {
        TypedValue typedValue = new TypedValue();
        App.getContext().getTheme().resolveAttribute(resId, typedValue, true);
        return typedValue.density;
    }

    public static Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(App.getContext(), resId);
    }

    public static ColorStateList getColorStateList(@ColorRes int resId) {
        return ContextCompat.getColorStateList(App.getContext(), resId);
    }
}
