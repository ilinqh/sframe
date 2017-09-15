package com.android.sframe.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.sframe.config.App;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by lin on 2017/9/12.
 * <p>
 * 功能：SharedPreferences 的封装
 * 通过 put() 直接保存数据
 * 通过 get() 方法获取对应的值
 */

public class SPHelper {

    // SharedPreference 存放的路径
    private static String fileName;
    // 默认值
    private static int defaultInt = 0;
    private static String defaultString = "";
    private static float defaultFloat = 0.0f;
    private static long defaultLong = 0;
    private static boolean defaultBoolean = false;

    public static void init(String fileName) {
        SPHelper.fileName = fileName;
    }

    /**
     * 保存数据，根据数据的类型调用相应的保存函数
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {

        SharedPreferences sp = App.getApp().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        apply(editor);
    }

    /**
     * 获取保存的数据，根据默认值获取相应保存数据的具体类型
     * 调用相应的方法获取保存的数据
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = App.getApp().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 保存数组
     *
     * @param name
     * @param list
     */
    public static void put(String name, LinkedList<?> list) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);
        put(name, jsonStr);
    }

    /**
     * 获取数组
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> LinkedList getList(String name, Class clazz) {
        String jsonStr = (String) get(name, defaultString);
        Gson gson = new Gson();
        LinkedList<T> list = (LinkedList<T>) gson.fromJson(jsonStr, clazz);
        return list;
    }

    /**
     * 往数组中添加元素
     *
     * @param name
     * @param data
     * @param clazz
     * @param maxNum
     * @param <T>
     */
    public static <T> void addNode(String name, T data, Class clazz, int maxNum) {
        String jsonStr = (String) get(name, defaultString);
        Gson gson = new Gson();
        LinkedList<T> list = (LinkedList<T>) gson.fromJson(jsonStr, clazz);
        list.offer(data);
        while (list.size() > maxNum) {
            list.poll();
        }
        put(name, list);
    }

    /**
     * 移除 key 对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sharedPreferences = App.getApp().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        apply(editor);
    }

    /**
     * 查询某个 key 是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sharedPreferences = App.getApp().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    public static Map<String, ?> getAll() {
        SharedPreferences sharedPreferences = App.getApp().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }

    /**
     * SharedPreferences.Editor.apply() 兼容方法
     *
     * @param editor
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void apply(SharedPreferences.Editor editor) {
        try {
            Class clazz = SharedPreferences.Editor.class;
            Method method = clazz.getMethod("apply");
            if (null != clazz.getMethod("apply")) {
                method.invoke(editor);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }
}
