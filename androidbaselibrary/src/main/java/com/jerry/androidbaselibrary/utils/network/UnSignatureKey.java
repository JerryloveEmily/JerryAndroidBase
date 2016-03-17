package com.jerry.androidbaselibrary.utils.network;

import com.bumptech.glide.load.Key;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2016/3/14.
 */
public class UnSignatureKey implements Key {


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {

    }
}
