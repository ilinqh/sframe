package com.android.sframe.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.sframe.util.KeyBoardUtils;
import com.android.sframe.util.TipUtils;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by lin on 2017/9/5.
 * 功能：所有 Activity 的基类
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity, SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper;

    public Bundle savedInstanceState;

    // Toolbar 类型
    private ToolbarType toolbarType;

    public Toolbar toolbar;

    public TextView textTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentResId() > 0) {
            ActivityUtils.initActivity(this, getToolbarType());
            // 初始化 ButterKnife
            ButterKnife.bind(this);
            initView();

            mHelper = new SwipeBackActivityHelper(this);
            mHelper.onActivityCreate();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    /**
     * 是否可滑动退出
     *
     * @param enable
     */
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public void tip(String content) {
        if (toolbar != null) {
            KeyBoardUtils.hideKeyboard(this, toolbar);
            TipUtils.snackBar(toolbar, content);
        } else {
            TipUtils.toast(this, content);
        }
    }

    // 设置 Toolbar
    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    /**
     * 强制转换字体，不随系统而改变
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 设置 Title 的 TextView
     *
     * @param textTitle
     */
    public void setTextTitle(TextView textTitle) {
        this.textTitle = textTitle;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        textTitle.setText(title);
    }

    public abstract void showLoading();

    public abstract void hideLoading();
}
