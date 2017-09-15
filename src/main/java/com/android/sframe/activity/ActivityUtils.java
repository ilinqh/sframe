package com.android.sframe.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by lin on 2017/9/6.
 */

public class ActivityUtils {

    public static void initActivity(BaseActivity activity, ToolbarType toolbarType) {
        switch (toolbarType) {
            case FullScreen:
                initFullScreen(activity);
            case NoToolbar:
                initNoToolbar(activity);
                break;
            case Default:
                initDefault(activity);
                break;
        }
    }

    /**
     * 全屏/没有状态栏、工具栏
     *
     * @param activity
     */
    private static void initFullScreen(BaseActivity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams
                .FLAG_FULLSCREEN);
    }

    /**
     * 没有工具栏，但有状态栏
     *
     * @param activity
     */
    private static void initNoToolbar(BaseActivity activity) {
        // 设置布局文件
        initContent(activity);
    }

    /**
     * 既有工具栏，又有状态栏
     *
     * @param activity
     */
    private static void initDefault(BaseActivity activity) {
        // 设置布局文件
        initContent(activity);
        // 设置 Title
        initTitle(activity);
    }

    /**
     * 设置 Title
     *
     * @param activity
     */
    private static void initTitle(BaseActivity activity) {
        // 设置 Toolbar
        Toolbar toolbar = (Toolbar) activity.findViewById(activity.getToolBarId());
        activity.setToolbar(toolbar);
        activity.setSupportActionBar(toolbar);

        // 设置 toolbar 显示的 Title 文字
        TextView textTitle = (TextView) toolbar.findViewById(activity.getTitleTextViewResId());
        activity.setTextTitle(textTitle);
        if (activity.getTitleResId() != IBaseActivity.NO_TITLE && textTitle != null) {
            // 设置 Title
            textTitle.setText(activity.getResources().getString(activity.getTitleResId()));
        }
    }

    /**
     * 初始化 Activity 的主视图
     *
     * @param activity
     */
    private static void initContent(BaseActivity activity) {
        //  设置为竖屏
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (IBaseActivity.NO_CONTENT != activity.getContentResId()) {
            activity.setContentView(activity.getContentResId());
        }
    }

}
