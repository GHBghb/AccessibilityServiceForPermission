package com.example.xingxiaogang.testfingerprint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.xingxiaogang.testfingerprint.utils.UMaCommonUtils;


/**
 * Created by guhongbo on 2017/11/28.
 */

public class BorderLableLinearLayout extends LinearLayout {

    private static final boolean DEBUG = true;
    private static final String TAG = "BorderLableLinearLayout";

    private int mBorderColor;
    private int mBorderWidth;

    private RectF rectF = new RectF();
    private int radius;
    private Paint mBorderPaint;
    private Paint mTextPaint;
    private Paint mClearPaint;
    private String title;

    private int titleLeftMargin;

    public BorderLableLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public BorderLableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BorderLableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setWillNotDraw(false);

        setPadding(getPaddingLeft(), getPaddingTop() + UMaCommonUtils.dip2px(context, 10), getPaddingRight(), getPaddingBottom());

        mBorderColor = Color.parseColor("#ff009688");
        mBorderWidth = UMaCommonUtils.dip2px(context, 2);
        radius = UMaCommonUtils.dip2px(context, 6);
        titleLeftMargin = UMaCommonUtils.dip2px(context, 16);

        mBorderPaint = new Paint();
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(mBorderColor);
        mTextPaint.setTextSize(UMaCommonUtils.sp2px(context, 14));
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);

        mClearPaint = new Paint();
        mClearPaint.setColor(Color.BLUE);
        mClearPaint.setStyle(Paint.Style.FILL);
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        title = "测试";
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private Rect mTextRect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int textWidth = (int) mTextPaint.measureText(title, 0, title.length());
        mTextPaint.getTextBounds(title, 0, title.length(), mTextRect);
        final int textHeight = mTextRect.height();

        rectF.set(0, textHeight / 2, getWidth(), getHeight());

        if (DEBUG) {
            Log.d(TAG, "onDraw: " + rectF);
        }


        int count = canvas.saveLayerAlpha(rectF.left, rectF.top, rectF.right, rectF.bottom, 255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.drawRoundRect(rectF, radius, radius, mBorderPaint);

        if (!TextUtils.isEmpty(title)) {
            mTextRect.set(mTextRect.left + titleLeftMargin, mTextRect.top, (mTextRect.left + textWidth + titleLeftMargin), mTextRect.height());
//            canvas.drawRect(mTextRect, mClearPaint);
//            canvas.drawText(title, mTextRect.left + titleLeftMargin, mTextRect.top + textHeight, mTextPaint);
        }
        canvas.restoreToCount(count);
    }
}
