package com.android.sframe.activity;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

/**
 * Created by lin on 2017/9/6.
 */

public interface IBaseActivity {

    @StringRes
    @LayoutRes
    int NO_CONTENT = -1;
    @StringRes
    @LayoutRes
    int NO_TITLE = -2;

    @LayoutRes
    int getContentResId();

    @StringRes
    int getTitleResId();

    @IdRes
    int getToolBarId();

    /**
     * 获取标题的 TextView id
     *
     * @return
     */
    @IdRes
    int getTitleTextViewResId();

    void initView();

    ToolbarType getToolbarType();

}
