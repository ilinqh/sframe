package com.android.sframe.rx;

import com.android.sframe.network.BaseResponse;

import rx.functions.Func1;

/**
 * Created by lin on 2017/9/11.
 * <p>
 * 功能：用来统一处理请求的 code，并将 BaseResponse 的 Data 部分剥离
 * 出来返回给 subscriber
 *
 * @param <T> Subscriber 真正需要的数据类型，也是 Data 部分的数据类型
 */

public class BaseResultFunc<T> implements Func1<BaseResponse<T>, T> {

    @Override
    public T call(BaseResponse<T> baseResponse) {
        if (!"SUCCESS".equals(baseResponse.getCode())) {
            throw new RuntimeException(baseResponse.getMsg());
        }
        return baseResponse.getData();
    }

}
