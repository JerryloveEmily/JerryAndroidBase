package com.jerry.androidbaselibrary.utils.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.jerry.androidbaselibrary.utils.network.cache.LruMemoryCache;

/**
 * volley单例请求管理类
 * Created by Jerry on 2016/3/11.
 */
public class VolleyRequestManager {

    private static final String TAG = "VolleyRequestManager";

    /**
     * 盒子开始
     **/
    private static VolleyRequestManager mInstance;
    private ImageLoader.ImageCache mImageMemoryCache;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    public ImageLoader.ImageCache getImageMemoryCache() {
        return mImageMemoryCache;
    }

    public RequestQueue getRequestQueue() {
        return mQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * 盒子结束
     **/

    private VolleyRequestManager(Context context) {
        mQueue = Volley.newRequestQueue(context);
        mImageMemoryCache = new LruMemoryCache();
        mImageLoader = new ImageLoader(mQueue, mImageMemoryCache);
    }

    public static VolleyRequestManager getInstance(Context context) {
        if (null == mInstance) {
            synchronized (VolleyRequestManager.class) {
                if (null == mInstance) {
                    mInstance = new VolleyRequestManager(context);
                }
            }
        }
        return mInstance;
    }

    public void addRequestToQueue(Request<?> request, String tag) {
        if (null == mQueue) {
            throw new RuntimeException("please first by VolleyRequestManager.getInstance(Context) method init RequestQueue object!");
        }
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        mQueue.add(request);
    }

    public void addRequestToQueue(Request<?> request) {
        if (null == mQueue) {
            throw new RuntimeException("please first by VolleyRequestManager.getInstance(Context) method init RequestQueue object!");
        }
        request.setTag(TAG);
        mQueue.add(request);
    }

    public void cancelRequests(String tag){
        if (null == mQueue) {
            throw new RuntimeException("please first by VolleyRequestManager.getInstance(Context) method init RequestQueue object!");
        }
        mQueue.cancelAll(tag);
    }
}
