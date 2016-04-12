package com.xys.libzxing.zxing.decode;

import android.graphics.Bitmap;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.HashMap;
import java.util.Map;

/**
 * 二维码解码工具
 * Created by Jerry on 2016/4/11.
 */
public class DecodingUtils {

    private DecodingUtils(){}

    /**
     * 解码一张二维码图片获取解码结果
     * @param qrcodeImageBitmap 二维码图片
     * @return  解码结果
     */
    public static Result decodeQRCodeImage(Bitmap qrcodeImageBitmap){
        if (null == qrcodeImageBitmap){
            return null;
        }

        Map<DecodeHintType, String> hints = new HashMap<>(10);
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

        int width = qrcodeImageBitmap.getWidth();
        int height = qrcodeImageBitmap.getHeight();

        int[] pixels = new int[width * height];
        qrcodeImageBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();

        try {
            return reader.decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ChecksumException e) {
            e.printStackTrace();
            return null;
        } catch (FormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
