package com.jerry.androidbaselibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.jerry.androidbaselibrary.app.ActivityManagerUtil;
import com.jerry.androidbaselibrary.lifecycle.ActivityLifecycleCallbacksCompat;
import com.jerry.androidbaselibrary.utils.network.netstate.NetWorkUtil;
import com.jerry.androidbaselibrary.utils.network.netstate.NetworkStateReceiver;

import java.lang.ref.WeakReference;

/**
 * Activity的基类
 * Created by Jerry on 2016/3/17.
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityLifecycleCallbacksCompat {

    protected   Context     mApplicationContext;
    protected   MyHandler   mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewId() != 0) {
            setContentView(getContentViewId()); // 设置视图
        }
        ActivityManagerUtil.getAppManager().addActivity(this);
        mApplicationContext = getApplicationContext();
        NetworkStateReceiver.registerNetworkStateReceiver(this);
        mHandler = new MyHandler(this);
        onActivityCreated(this, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onActivityStarted(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onActivityResumed(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onActivityPaused(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        onActivityStopped(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        onActivitySaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onActivityRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        onActivityDestroyed(this);
        NetworkStateReceiver.unRegisterNetworkStateReceiver(this);
        ActivityManagerUtil.getAppManager().finishActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected static class MyHandler extends Handler {

        WeakReference<BaseActivity> mReference = null;

        MyHandler(BaseActivity activity) {
            this.mReference = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity outer = mReference.get();
            if (outer == null && outer.isFinishing()) {
                return;
            }

            outer.handleMessage(msg);
        }
    }

    /**
     * 跳转到另一个activity
     *
     * @param clazz     进入的activity
     * @param finish    是否finish当前activity
     */
    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    /**
     * 跳转到另一个activity
     *
     * @param clazz     进入的activity
     * @param finish    是否finish当前activity
     * @param bundle    跳转传递的数据
     */
    public void gotoActivity(Class<? extends Activity> clazz, boolean finish, Bundle bundle) {

        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    /**
     * 跳转到另一个activity
     *
     * @param clazz     进入的activity
     * @param flags     跳转的时候的标记
     * @param finish    是否finish当前activity
     * @param bundle    跳转传递的数据
     */
    public void gotoActivity(Class<? extends Activity> clazz, int flags, boolean finish, Bundle bundle) {

        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        intent.addFlags(flags);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    /**
     * 连接网络时
     *
     * @param type 网络类型
     */
    public void onConnect(NetWorkUtil.netType type) {
    }

    /**
     * 网络连接断开时
     */
    public void onDisConnect() {
    }

    /**
     * handler所有的Message的处理
     *
     * @param msg 被处理的消息
     */
    public void handleMessage(Message msg) {
    }

    /**
     * 获取Activity的xml布局的id
     *
     * @return 布局的id
     */
    protected abstract int getContentViewId();
}
