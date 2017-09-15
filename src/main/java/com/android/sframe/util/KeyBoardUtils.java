package com.android.sframe.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by lin on 2017/9/6.
 * 功能：键盘工具类，可控制软键盘的弹出和收入
 */

public class KeyBoardUtils {

    public static boolean flag = true;

    /**
     * 显示 Keyboard
     *
     * @param context
     */
    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示 Keyboard
     *
     * @param context
     * @param view    接受软键盘输入的视图
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 强制显示
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 测试软键盘的开关
     *
     * @param context
     * @param view
     */
    public static void autoDisplayKeyboard(Context context, View view) {
        if (flag) {
            showKeyboard(context);
            flag = false;
        } else {
            hideKeyboard(context, view);
            flag = true;
        }
    }

    /**
     * @param context
     * @return 输入法打开的状态，若返回 true，表示输入法打开
     */
    public static boolean isOpen(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

}
