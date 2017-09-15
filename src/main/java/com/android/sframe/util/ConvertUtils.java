package com.android.sframe.util;

import android.text.TextUtils;

/**
 * Created by lin on 2017/9/7.
 * 功能：基本类型转换
 */

public class ConvertUtils {

    /**
     * Object -> String
     *
     * @param object
     * @param defaultValue
     * @return
     */
    public static String convertString(Object object, String defaultValue) {
        return object == null ? defaultValue : String.valueOf(object);
    }

    public static String convertString(Object object) {
        return convertString(object, "");
    }

    /**
     * object -> int
     *
     * @param object
     * @return
     */
    public static int convertInt(Object object) {
        String value = convertString(object, "0");
        return Integer.parseInt(value);
    }

    public static int convertInt(Object object, int defaultValue) {
        int target = defaultValue;
        if (object != null) {
            String value = convertString(object);
            if (!TextUtils.isEmpty(value)) {
                try {
                    target = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return target;
    }

    /**
     * object -> double
     *
     * @param object
     * @param defaultValue
     * @return
     */
    public static double convertDouble(Object object, double defaultValue) {
        double target = defaultValue;
        if (object != null) {
            String value = convertString(object);
            if (!TextUtils.isEmpty(value)) {
                try {
                    target = Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return target;
    }

    public static double convertDouble(Object object) {
        return convertDouble(object, 0f);
    }

    /**
     * object -> boolean
     * @param object
     * @param defaultValue
     * @return
     */
    public static boolean convertBoolean(Object object, boolean defaultValue) {
        boolean target = defaultValue;
        if (object != null) {
            String value = convertString(object);
            if (!TextUtils.isEmpty(value)) {
                try {
                    target = Boolean.parseBoolean(value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return target;
    }

    public static boolean convertBoolean(Object object) {
        return convertBoolean(object, false);
    }
}
