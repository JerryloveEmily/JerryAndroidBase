package com.jerry.androidbaselibrary.utils.network.cache.disk;

import android.content.Context;

import com.bumptech.glide.load.engine.cache.DiskCache;

import java.io.File;

/**
 * 自定义key的SD卡外部磁盘缓存工厂
 * Created by Jerry on 2016/3/14.
 */
public class ExternalCacheDiskCacheCustomKeyFactory extends DiskLruCacheCustomKeyFactory {

    public ExternalCacheDiskCacheCustomKeyFactory(Context context) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
    }

    public ExternalCacheDiskCacheCustomKeyFactory(Context context, int diskCacheSize) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public ExternalCacheDiskCacheCustomKeyFactory(final Context context, final String diskCacheName, int diskCacheSize) {
        super(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                File cacheDirectory = context.getExternalCacheDir();
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
