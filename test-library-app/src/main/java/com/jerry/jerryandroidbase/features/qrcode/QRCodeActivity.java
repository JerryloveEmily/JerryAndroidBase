package com.jerry.jerryandroidbase.features.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.jerry.androidbaselibrary.BaseActivity;
import com.jerry.androidbaselibrary.widget.quickadapter.QuickAdapter;
import com.jerry.jerryandroidbase.R;
import com.jerry.jerryandroidbase.consts.AppConst;
import com.jerry.jerryandroidbase.features.MainItem;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.decode.DecodingUtils;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 二维码扫描
 * Created by Jerry on 2016/4/11.
 */
public class QRCodeActivity extends BaseActivity {

    // 解析扫描的二维码
    private static final int DECODE_QRCODE = 0x520;

    @Bind(R.id.btn_decode_qrcode)
    Button btnDecodeQrcode;
    @Bind(R.id.btn_encode_qrcode)
    Button btnEncodeQrcode;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.iv_result)
    ImageView ivResult;
    @Bind(R.id.et_text)
    EditText etText;
    @Bind(R.id.cb_logo)
    CheckBox cbLogo;
    @Bind(R.id.ll_result)
    LinearLayout llResult;

    private QuickAdapter<MainItem> mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        initView();
//        initData();
        initEvent();
    }

    private void initView() {
        ButterKnife.bind(this);

    }

    private void initData() {
        List<MainItem> datas = new ArrayList<>(15);
        for (int i = 0; i < AppConst.QRCODE_ITEM_TITLES.length; i++) {
            String title = AppConst.QRCODE_ITEM_TITLES[i];
            MainItem item = new MainItem(title, AppConst.QRCODE_ITEM_CLASS[i]);
            datas.add(item);
        }

        mAdapter.addAll(datas);
    }

    private void initEvent() {
        btnDecodeQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QRCodeActivity.this, CaptureActivity.class);
                startActivityForResult(i, DECODE_QRCODE);
            }
        });
        btnEncodeQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 生成二维码
                String inputText = etText.getText().toString();
                if (TextUtils.isEmpty(inputText)) {
                    Toast.makeText(QRCodeActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    tvResult.setVisibility(View.GONE);
                    llResult.setVisibility(View.VISIBLE);
                    Bitmap logoBitmap = null;
                    if (cbLogo.isChecked()) {
                        logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    }
                    // 生成二维码
                    Bitmap bitmap = EncodingUtils.createQRCode(inputText, 600, 600, logoBitmap);
                    ivResult.setImageBitmap(bitmap);
                }
            }
        });

        ivResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 解析二维码
                Result result = DecodingUtils.decodeQRCodeImage(getBitmapFromImageView(ivResult));
                String resultTxt;
                if (result != null){
                    resultTxt = "解码内容为：" + result.getText();
                }else {
                    resultTxt = "二维码解码错误";
                }
                Toast.makeText(QRCodeActivity.this,
                        resultTxt, Toast.LENGTH_SHORT).show();
                return false;
            }

            public Bitmap getBitmapFromImageView(ImageView imageView){
                return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            }

        });
        /*lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainItem item = mAdapter.getItem(position);
                if (null == item) {
                    return;
                }

                Intent i = new Intent(QRCodeActivity.this, item.getCls());
                startActivityForResult(i, DECODE_QRCODE);
            }
        });*/
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    public void onActivityRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DECODE_QRCODE) {
            if (resultCode == RESULT_OK) {
                tvResult.setVisibility(View.VISIBLE);
                llResult.setVisibility(View.VISIBLE);
                Bundle bundle = data.getExtras();
                String resultText = bundle.getString("resultText");
                Bitmap bitmap = EncodingUtils.createQRCode(resultText, 600, 600, null);
                ivResult.setImageBitmap(bitmap);
                tvResult.setText(resultText);
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
