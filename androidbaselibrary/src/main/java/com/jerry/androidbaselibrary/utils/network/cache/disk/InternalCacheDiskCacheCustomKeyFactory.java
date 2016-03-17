package com.jerry.androidbaselibrary.utils.network.cache.disk;

import android.content.Context;

import com.bumptech.glide.load.engine.cache.DiskCache;

import java.io.File;

/**
 * 自定义key的内部data文件磁盘缓存工厂
 *
 * Created by Jerry on 2016/3/14.
 */
public class InternalCacheDiskCacheCustomKeyFactory extends DiskLruCacheCustomKeyFactory {

    public InternalCacheDiskCacheCustomKeyFactory(Context context) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
    }

    public InternalCacheDiskCacheCustomKeyFactory(Context context, int diskCacheSize) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public InternalCacheDiskCacheCustomKeyFactory(final Context context, final String diskCacheName, int diskCacheSize) {
        super(new DiskLruCacheCustomKeyFactory.CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                File cacheDirectory = context.getCacheDir();
                if (cacheDirectory == null) {
                    return null;
                }
                if (diskCacheName != null) {
                    return new File(cacheDirectory, diskCacheName);
                }
                return cacheDirectory;
            }
        }, diskCacheSize);
    }
}
