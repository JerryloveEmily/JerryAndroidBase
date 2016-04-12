package com.jerry.jerryandroidbase.consts;

import com.jerry.jerryandroidbase.features.qrcode.QRCodeActivity;
import com.xys.libzxing.zxing.activity.CaptureActivity;

/**
 * 全局常量类
 * Created by Jerry on 2016/4/11.
 */
public class AppConst {

    /**************************主页开始***************************/
    public static final String[] MAIN_ITEM_TITLES = {
            "ZXing二维码扫描"
    };

    public static final Class<?>[] MAIN_ITEM_CLASS = {
            QRCodeActivity.class
    };
    /**************************主页结束***************************/



    /**************************二维码扫描开始***************************/
    public static final String[] QRCODE_ITEM_TITLES = {
            "二维码扫描",
            "生成二维码"
    };

    public static final Class<?>[] QRCODE_ITEM_CLASS = {
            CaptureActivity.class,
            null
    };
    /**************************二维码扫描结束***************************/
}
