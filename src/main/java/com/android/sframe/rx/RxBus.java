package com.android.sframe.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by lin on 2017/9/15.
 * <p>
 * 功能：组件通信，用来替代 EventBus
 * 使用 RxBus 不需要注册，但是需要在关闭所在页面时注销
 * 防止内存泄露
 */

public class RxBus {

    private static volatile RxBus instance;

    private final Subject<Object, Object> bus;

    // PublishSubject 只会把在订阅发生的时间点之后
    // 来自原始 Observable 的数据发送给观察者
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例 RxBus
    public static RxBus getDefault() {
        if (null == instance) {
            synchronized (RxBus.class) {
                if (null == instance) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 发送一个新的事件
     *
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型 (eventType) 的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public void reset() {
        instance = null;
    }
}
