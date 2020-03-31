package com.kingja.autopager.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

/**
 * Description:TODO
 * Create Time:2017/8/5 11:03
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class IndicatorView extends View implements Indicator{

    private Paint paint;
    private int size;
    private int initSize;

    public IndicatorView(Context context) {
        super(context);
        initIndicatorView();
    }

    private void initIndicatorView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xff00ffff);
        initSize = dp2dx(12);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(initSize, initSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = getMeasuredWidth();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawIndicator(canvas,size,paint);
    }

    protected abstract void drawIndicator(Canvas canvas, int size, Paint paint);

    protected void setIndicatorColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    @Override
    public abstract void setIndicatorNormal();

    @Override
    public abstract void setIndicatorSelected();
    @Override
    public void setIndicatorSize(int size) {
        initSize = size;
        requestLayout();
    }

    @Override
    public abstract View getInstance();

    private int dp2dx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }
}
