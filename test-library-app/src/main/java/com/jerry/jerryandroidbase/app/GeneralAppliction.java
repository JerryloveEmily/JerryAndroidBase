package com.jerry.jerryandroidbase.app;

import android.content.Context;

import com.jerry.androidbaselibrary.BaseApplication;
import com.jerry.androidbaselibrary.utils.log.JLog;

/**
 *
 * Created by Jerry on 2016/3/17.
 */
public class GeneralAppliction extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void initLogUtils() {
        super.initLogUtils();
        // 配置日志是否输出(默认true)
        JLog.isAllowDebug = true;

        // 配置日志前缀
        JLog.customTagPrefix = "JerryLog - ";
    }

    @Override
    protected void initOtherServer() {
        super.initOtherServer();
    }

    @Override
    protected void onUICrashAfter(Context context) {
        super.onUICrashAfter(context);
    }

    @Override
    protected void onSubThreadCrashAfter(Context context) {
        super.onSubThreadCrashAfter(context);
    }
}
