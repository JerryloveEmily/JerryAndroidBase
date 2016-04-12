package com.xys.libzxing.zxing.bean;

import com.google.zxing.Result;

import java.io.Serializable;

/**
 * 解码的结果
 * Created by Jerry on 2016/4/11.
 */
public class DecoderResult implements Serializable {

    private Result mResult;

    public DecoderResult(Result result) {
        this.mResult = result;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        this.mResult = result;
    }
}
