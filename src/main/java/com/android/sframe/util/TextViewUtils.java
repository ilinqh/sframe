package com.android.sframe.util;

import android.support.annotation.StringRes;
import android.widget.TextView;

/**
 * Created by lin on 2017/9/12.
 */

public class TextViewUtils {

    /**
     * TextView 设置文字前判断是否为空
     *
     * @param textView
     * @param str
     */
    public static void setText(TextView textView, String str) {
        textView.setText(StringUtils.isEmpty(str) ? "" : str);
    }

    /**
     * TextView 设置多个参数，建议传参前建议检验参数是否为空
     *
     * @param textView
     * @param resId
     * @param obj
     */
    public static void setText(TextView textView, @StringRes int resId, Object... obj) {
        textView.setText(String.format(ResUtils.getString(resId), obj));
    }

}
