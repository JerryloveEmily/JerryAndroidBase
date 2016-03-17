package com.jerry.androidbaselibrary.utils.network;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Glide全局配置
 * Created by Jerry on 2016/3/14.
 */
public class VolleyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        // 配置全局的Glide参数
        configGlobalGlideBuilder(context, builder);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

    private void configGlobalGlideBuilder(Context context, GlideBuilder builder){
        // 最大的使用大小
        int mMaxSize = (int) (Runtime.getRuntime().maxMemory()/8);

        builder.setBitmapPool(new LruBitmapPool(mMaxSize / 2));
        builder.setMemoryCache(new LruResourceCache(mMaxSize));
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
    }
}
