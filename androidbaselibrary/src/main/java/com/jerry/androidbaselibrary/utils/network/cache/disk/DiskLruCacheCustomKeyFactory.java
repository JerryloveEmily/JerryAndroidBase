package com.jerry.androidbaselibrary.utils.network.cache.disk;

import com.bumptech.glide.load.engine.cache.DiskCache;

import java.io.File;

/**
 * 自定义key的磁盘缓存工厂
 * Created by Jerry on 2016/3/14.
 */
public class DiskLruCacheCustomKeyFactory implements DiskCache.Factory {

private final int diskCacheSize;
private final CacheDirectoryGetter cacheDirectoryGetter;

/**
 * Interface called out of UI thread to get the cache folder.
 */
public interface CacheDirectoryGetter {
    File getCacheDirectory();
}

    public DiskLruCacheCustomKeyFactory(final String diskCacheFolder, int diskCacheSize) {
        this(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                return new File(diskCacheFolder);
            }
        }, diskCacheSize);
    }

    public DiskLruCacheCustomKeyFactory(final String diskCacheFolder, final String diskCacheName, int diskCacheSize) {
        this(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                return new File(diskCacheFolder, diskCacheName);
            }
        }, diskCacheSize);
    }

    /**
     * When using this constructor {@link CacheDirectoryGetter#getCacheDirectory()} will be called out of UI thread,
     * allowing to do I/O access without performance impacts.
     *
     * @param cacheDirectoryGetter Interface called out of UI thread to get the cache folder.
     * @param diskCacheSize        Desired max bytes size for the LRU disk cache.
     */
    public DiskLruCacheCustomKeyFactory(CacheDirectoryGetter cacheDirectoryGetter, int diskCacheSize) {
        this.diskCacheSize = diskCacheSize;
        this.cacheDirectoryGetter = cacheDirectoryGetter;
    }

    @Override
    public DiskCache build() {
        File cacheDir = cacheDirectoryGetter.getCacheDirectory();

        if (cacheDir == null) {
            return null;
        }

        if (!cacheDir.mkdirs() && (!cacheDir.exists() || !cacheDir.isDirectory())) {
            return null;
        }

        return DiskLruCustomKeyCache.get(cacheDir, diskCacheSize);
    }
}
