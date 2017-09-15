package com.android.sframe.network;

/**
 * Created by lin on 2017/9/11.
 * <p>
 * 功能：网络请求返回数据
 */

public class BaseResponse<T> {

    // 请求返回的消息
    private String msg;
    // 请求状态，若请求成功返回 "SUCCESS"
    private String code;
    // 请求返回的数据对象
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
