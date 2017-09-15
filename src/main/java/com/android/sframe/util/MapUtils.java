package com.android.sframe.util;

import android.text.TextUtils;

import java.util.Map;

/**
 * Created by lin on 2017/9/7.
 * 功能：Map 工具类，对添加到 Map 中的元素进行过滤
 */

public class MapUtils {

    public static void add(Map map, String key, Object value) {
        String strValue = ConvertUtils.convertString(value);
        if (TextUtils.isEmpty(strValue) || TextUtils.isEmpty(key)) {
            return;
        }

        map.put(key, strValue);
    }

}
