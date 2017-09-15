package com.android.sframe.rx;

import rx.functions.Action1;

/**
 * Created by lin on 2017/9/15.
 * <p>
 * 功能：重写 call 方法，减少闪退
 */

public abstract class EventAction<T> implements Action1<T> {

    public abstract void eventCall(T t);

    @Override
    public void call(T t) {
        try {
            eventCall(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
