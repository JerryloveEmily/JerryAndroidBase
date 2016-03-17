package com.jerry.androidbaselibrary.utils.network.request;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

/**
 * volley网络请求响应回调接口
 * Created by Jerry on 2016/3/11.
 */
public class ResponseListener<T> implements Response.Listener<T>, ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(T response) {

    }
}
