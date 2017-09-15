package com.android.sframe.rx;

import com.android.sframe.activity.BaseActivity;
import com.android.sframe.fragment.BaseFragment;

import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by lin on 2017/9/11.
 * <p>
 * 功能：判断服务器返回的数据
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private BaseActivity activity;
    private BaseFragment fragment;

    public BaseSubscriber(BaseActivity activity) {
        this.activity = activity;
    }

    public BaseSubscriber(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading();
    }

    @Override
    public void onCompleted() {
        hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e instanceof SocketTimeoutException) {
            String errorMessage = "网络连接超时";
            if (null != activity) {
                activity.tip(errorMessage);
            } else if (null != fragment) {
                fragment.tip(errorMessage);
            }
        } else {
            /*if (null != activity) {
                activity.tip(e.getMessage());
            } else if (null != fragment) {
                fragment.tip(e.getMessage());
            }*/
            // 异常处理
            myError(e);
        }
    }

    @Override
    public void onNext(T t) {
        hideLoading();
        myNext(t);
    }

    /**
     * 请求成功后，在此方法中处理返回数据
     *
     * @param t
     */
    protected abstract void myNext(T t);

    /**
     * 请求失败或不是想要的数据，在此方法中进行处理数据
     *
     * @param e
     */
    protected abstract void myError(Throwable e);

    /**
     * 显示加载中进度条
     */
    private void showLoading() {
        if (null != activity) {
            activity.showLoading();
        } else if (null != fragment) {
            fragment.showLoading();
        }
    }

    /**
     * 关闭加载中进度条
     */
    private void hideLoading() {
        if (null != activity) {
            activity.hideLoading();
        }
        if (null != fragment) {
            fragment.hideLoading();
        }
    }
}
