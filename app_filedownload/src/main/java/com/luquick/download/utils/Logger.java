package com.luquick.download.utils;

import android.util.Log;

import java.util.Locale;

/**
 * 作者：Created by Luquick on 2018/6/24.
 * 邮箱: xxxxxx@163.com
 * QQ号：930982728
 * 微信：p11225630
 * 作用：整个工程 Logger管理
 */
public class Logger {

    public static final boolean DEBUG = true;

    public static void debug(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void debug(String tag, String message, Object...args) {
        if (DEBUG) {
            Log.d(tag,String.format(Locale.getDefault(),message,args));
        }
    }

    public static void error(String tag, String message) {
        if (DEBUG) {
            Log.e(tag,message);
        }
    }

    public static void info(String tag, String message) {
        if (DEBUG) {
            Log.i(tag,message);
        }

    }
}
