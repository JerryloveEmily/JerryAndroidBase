package com.jerry.jerryandroidbase.features;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jerry.androidbaselibrary.BaseActivity;
import com.jerry.androidbaselibrary.utils.log.JLog;
import com.jerry.androidbaselibrary.utils.network.netstate.NetWorkUtil;
import com.jerry.androidbaselibrary.widget.quickadapter.BaseAdapterHelper;
import com.jerry.androidbaselibrary.widget.quickadapter.QuickAdapter;
import com.jerry.jerryandroidbase.R;
import com.jerry.jerryandroidbase.consts.AppConst;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.lv_list)
    ListView lvList;

    private QuickAdapter<MainItem> mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        JLog.d("onActivityCreated...");
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        ButterKnife.bind(this);
        initListView();
    }

    private void initListView() {
        mAdapter = new QuickAdapter<MainItem>(this, R.layout.main_list_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, int position, MainItem item) {
                TextView tvTitle = helper.getView(R.id.tv_title);
                tvTitle.setText(item.getTitle());
            }
        };
        lvList.setAdapter(mAdapter);
    }

    private void initData() {
        List<MainItem> datas = new ArrayList<>(15);
        for (int i = 0; i < AppConst.MAIN_ITEM_TITLES.length; i++) {
            String title = AppConst.MAIN_ITEM_TITLES[i];
            MainItem item = new MainItem(title, AppConst.MAIN_ITEM_CLASS[i]);
            datas.add(item);
        }

        mAdapter.addAll(datas);
    }

    private void initEvent() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainItem item = mAdapter.getItem(position);
                Intent i = new Intent(MainActivity.this, item.getCls());
                startActivity(i);
            }
        });
    }

    @Override
    public void onActivityStarted(Activity activity) {
        JLog.d("onActivityStarted...");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        JLog.d("onActivityResumed...");
    }

    @Override
    public void onConnect(NetWorkUtil.netType type) {
        super.onConnect(type);
    }

    @Override
    public void onDisConnect() {
        super.onDisConnect();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        JLog.d("onActivityPaused...");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        JLog.d("onActivityStopped...");
    }

    @Override
    public void onActivitySaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    public void onActivityRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        JLog.d("onActivityDestroyed...");
    }
}
