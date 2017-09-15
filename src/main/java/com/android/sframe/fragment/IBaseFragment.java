package com.android.sframe.fragment;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by lin on 2017/9/7.
 */

public interface IBaseFragment {

    void initView(View view);

    @LayoutRes
    int getContentResId();

}
