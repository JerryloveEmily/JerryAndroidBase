package com.jerry.androidbaselibrary.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * 进度条
 * Created by JerryloveEmily on 2016/6/8.
 */
public class JProgressBar extends View {

    private Paint mPaint;
    private int mRadius;
    private RectF mRect;

    public JProgressBar(Context context) {
        this(context, null);
    }

    public JProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20.f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int minTemp = Math.min(w, h);
        mRadius = minTemp/2;
        mRect = new RectF(w/2-mRadius/2, h/2-mRadius/2,
                w/2 + mRadius/2, h/2 + mRadius/2);
        LinearGradient lg = new LinearGradient(
                mRect.left, mRect.top, mRect.right, mRect.bottom,
                0xff17CBF4, 0xff0E84F2, Shader.TileMode.CLAMP);
        mPaint.setShader(lg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRect, 0, 230f, false, mPaint);
    }
}
