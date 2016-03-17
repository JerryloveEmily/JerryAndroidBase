package com.jerry.androidbaselibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 应用Crash后的异常捕获和处理
 * Created by Jerry on 2015/9/6.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static CrashHandler mInstance;
    private Context mContext;

    private CrashHandler(Context context) {
        init(context);
    }

    public static CrashHandler getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler(context);
                }
            }
        }
        return mInstance;
    }

    private void init(Context context) {
        this.mContext = context;
        // 设置异常捕获处理
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 收集出现app奔溃设备的信息
        String deviceInfo = getCrashDeviceInfo();

        // 记录错误信息
        String saveFileName = saveExceptionInfo(ex, deviceInfo);
        Log.e(TAG, "错误日志文件名称：" + saveFileName);


        // 判断当前线程是否为UI线程
        if (thread.getId() == 1) {// 为UI线程
            Log.e(TAG, "Application crashed is in UI thread. Now, restarting app...");
//            rebootApp(mContext);
            if (mOnCrashAfterListener != null){
                mOnCrashAfterListener.onUICrashAfter(mContext);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            Log.e(TAG, " Application crashed isn't in UI thread. Now, restarting app...");
            // 当前线程不是UI线程的处理
            if (mOnCrashAfterListener != null){
                mOnCrashAfterListener.onSubThreadCrashAfter(mContext);
            }
        }
    }

    private String getCrashDeviceInfo() {
        StringBuilder sbDevice = new StringBuilder();
        try {
            PackageManager pkm = mContext.getPackageManager();
            PackageInfo pi = pkm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            String versionName = TextUtils.isEmpty(pi.versionName) ? "null" : pi.versionName;
            String versionCode = String.valueOf(pi.versionCode);

            sbDevice.append("手机品牌信号：")
                    .append(Build.BRAND)
                    .append(" ")
                    .append(Build.MODEL)
                    .append(", 系统版本号：")
                    .append(Build.VERSION.RELEASE)
                    .append("\n")
                    .append("App版本名称：")
                    .append(versionName)
                    .append(", App版本编码：")
                    .append(versionCode);
            Log.e(TAG, sbDevice.toString());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        return sbDevice.toString();
    }

    private String saveExceptionInfo(Throwable ex, String deviceInfo) {
        StringBuffer sb = new StringBuffer();
        sb.append("报错的设备信息：\n").append(deviceInfo)
                .append("\n").append("\n")
                .append("错误异常信息：\n");
        //用于格式化日期,作为日志文件名的一部分
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".cr";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = "/sdcard/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

    /**
     * 重启APP
     *
     * @param context
     */
    private void rebootApp(Context context) {
        Toast.makeText(context, "程序异常退出，重启中...", Toast.LENGTH_SHORT).show();
        // 把所有的Activity放入Activity列表中，并一个个删除列表和退出该Activity
        // 重新启动app
//        Intent intent = new Intent(context, ScreenLockActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
    }

    public void doSomethingAfterCrash(Context context) {

    }

    private OnCrashAfterListener mOnCrashAfterListener;

    public void setOnCrashAfterListener(OnCrashAfterListener onCrashAfterListener) {
        this.mOnCrashAfterListener = onCrashAfterListener;
    }

    public OnCrashAfterListener getOnCrashAfterListener() {
        return mOnCrashAfterListener;
    }

    /**
     * App被Crash后的监听事件
     */
    interface OnCrashAfterListener {
        /**
         * 在UI线程Crash的处理
         * @param context ...
         */
        void onUICrashAfter(Context context);

        /**
         * 在子线程Crash的处理
         * @param context ...
         */
        void onSubThreadCrashAfter(Context context);
    }
}
