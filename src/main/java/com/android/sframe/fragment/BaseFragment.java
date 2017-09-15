package com.android.sframe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sframe.util.KeyBoardUtils;
import com.android.sframe.util.TipUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by lin on 2017/9/7.
 * 功能：所有 Fragment 的基类
 */

public abstract class BaseFragment extends Fragment implements IBaseFragment {

    /**
     * 上下文
     */
    public Context context;

    /**
     * View  of this fragment
     */
    private View view;

    public LayoutInflater inflater;

    protected WeakReference<View> mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        this.inflater = inflater;

        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(getContentResId(), null);
            ButterKnife.bind(this, view);
            initView(view);

            mRootView = new WeakReference<View>(view);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        // TODO initProgress
    }

    public void tip(String content) {
        if (view != null) {
            KeyBoardUtils.hideKeyboard(context, view);
            TipUtils.snackBar(view, content);
        } else {
            TipUtils.toast(context, content);
        }
    }

    public abstract void showLoading();
    public abstract void hideLoading();
}
