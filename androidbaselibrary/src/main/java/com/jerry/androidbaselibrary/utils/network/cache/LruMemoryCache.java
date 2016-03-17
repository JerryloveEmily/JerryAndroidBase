package com.jerry.androidbaselibrary.utils.network.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 使用Lru删除策略算法的内存缓存类
 * Created by Jerry on 2016/3/11.
 */
public class LruMemoryCache implements ImageCache {

    private LruCache<String, Bitmap> mLruCache;
    // 最大的内存缓存是最大可用内存的1/8

    private int mMaxSize = (int) (Runtime.getRuntime().maxMemory()/8);

    public LruMemoryCache(){

        mLruCache = new LruCache<String, Bitmap>(mMaxSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //API 19
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    return bitmap.getAllocationByteCount();
                }
                //API 12
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){
                    return bitmap.getByteCount();
                }
                //earlier version
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mLruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mLruCache.put(url, bitmap);
    }

    public void removeBitmap(String url){
        mLruCache.remove(url);
    }
}
