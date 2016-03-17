package com.jerry.androidbaselibrary.utils.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志工具类
 *
 * Created by Administrator on 2015/8/6.
 */
public class JLog {
    public static boolean isAllowDebug = true;
    public static boolean isAllowV = true;
    public static boolean isAllowD = true;
    public static boolean isAllowI = true;
    public static boolean isAllowW = true;
    public static boolean isAllowE = true;
    public static String customTagPrefix = "";

    private JLog(){
    }

    public static void v(String msg){
        if (isAllowDebug){
            if (isAllowV) {
                StackTraceElement caller = getCallerStackTraceElement();
                String tag = generateTag(caller);
                Log.v(tag, msg);
            }
        }
    }

    public static void d(String msg){
        if (isAllowDebug){
            if (isAllowD) {
                StackTraceElement caller = getCallerStackTraceElement();
                String tag = generateTag(caller);
                Log.d(tag, msg);
            }
        }
    }

    public static void i(String msg){
        if (isAllowDebug){
            if (isAllowI) {
                StackTraceElement caller = getCallerStackTraceElement();
                String tag = generateTag(caller);
                Log.i(tag, msg);
            }
        }
    }

    public static void w(String msg){
        if (isAllowDebug){
            if (isAllowW) {
                StackTraceElement caller = getCallerStackTraceElement();
                String tag = generateTag(caller);
                Log.w(tag, msg);
            }
        }
    }

    public static void e(String msg){
        if (isAllowDebug){
            if (isAllowE) {
                StackTraceElement caller = getCallerStackTraceElement();
                String tag = generateTag(caller);
                Log.e(tag, msg);
            }
        }
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(Line:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, new Object[]{callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        tag = TextUtils.isEmpty(customTagPrefix)?tag:customTagPrefix + ":" + tag;
        return tag;
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
