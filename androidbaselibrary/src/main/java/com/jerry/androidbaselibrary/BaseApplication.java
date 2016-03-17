package com.jerry.androidbaselibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.jerry.androidbaselibrary.app.ActivityManagerUtil;
import com.jerry.androidbaselibrary.utils.network.netstate.NetChangeObserver;
import com.jerry.androidbaselibrary.utils.network.netstate.NetWorkUtil;
import com.jerry.androidbaselibrary.utils.network.netstate.NetworkStateReceiver;

/**
 * Application基类
 * Created by Jerry on 2016/3/17.
 */
public class BaseApplication extends Application {

    public Activity mCurrentActivity;
    protected CrashHandler mCrashHandler;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 这个方法比onCreate方法更早执行，适合在此加入多Dex分包支持来解决 65k方法数爆棚问题
        // 当然你得先在当前module的build.gradle文件中加入如下内容：
        /*android {
            defaultConfig {
                // Enabling multidex support.
                multiDexEnabled true
            }
        }
        dependencies {  com.android.support:multidex:1.0.0'}*/
        // 然后在此安装初始化分包工具
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initWork();
    }

    private void initWork() {
        // 初始化app crash捕获对象
        initExcetionCatch();
        // 初始化日志开关
        initLogUtils();
        // 注册网络状态监听器
        registerNetWorkStateListener();
        // 初始化第三方服务功能
        initOtherServer();
    }

    /**
     * 初始化日志开关
     */
    protected void initLogUtils() {

    }

    /**
     * 初始化未主动捕获的异常捕获工具
     */
    private void initExcetionCatch(){
        mCrashHandler = CrashHandler.getInstance(this);
        mCrashHandler.setOnCrashAfterListener(new CrashHandler.OnCrashAfterListener() {
            @Override
            public void onUICrashAfter(Context context) {
                try {
                    BaseApplication.this.onUICrashAfter(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSubThreadCrashAfter(Context context) {
                try {
                    BaseApplication.this.onSubThreadCrashAfter(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void registerNetWorkStateListener() {
        NetChangeObserver netChangeObserver = new NetChangeObserver() {
            @Override
            public void onConnect(NetWorkUtil.netType type) {
                super.onConnect(type);
                try {
                    BaseApplication.this.onConnect(type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisConnect() {
                super.onDisConnect();
                try {
                    BaseApplication.this.onDisConnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        NetworkStateReceiver.registerObserver(netChangeObserver);
    }

    /**
     * 初始化第三方服务功能
     */
    protected void initOtherServer() {

    }

    /**
     * 当前没有网络连接通知
     */
    public void onDisConnect() {
        mCurrentActivity = ActivityManagerUtil.getAppManager().currentActivity();
        if (mCurrentActivity != null) {
            if (mCurrentActivity instanceof BaseActivity) {
                ((BaseActivity) mCurrentActivity).onDisConnect();
            }
        }
    }

    /**
     * 网络连接连接时通知
     */
    protected void onConnect(NetWorkUtil.netType type) {
        mCurrentActivity = ActivityManagerUtil.getAppManager().currentActivity();
        if (mCurrentActivity != null) {
            if (mCurrentActivity instanceof BaseActivity) {
                ((BaseActivity) mCurrentActivity).onConnect(type);
            }
        }
    }

    /**
     * 在UI线程Crash后的处理
     * @param context ...
     */
    protected void onUICrashAfter(Context context){

    }

    /**
     * 在子线程Crash后的处理
     * @param context ...
     */
    protected void onSubThreadCrashAfter(Context context){

    }
}
