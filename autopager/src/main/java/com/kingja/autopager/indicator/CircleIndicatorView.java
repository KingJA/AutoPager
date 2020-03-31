package com.kingja.autopager.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Description:TODO
 * Create Time:2017/8/5 11:03
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CircleIndicatorView extends IndicatorView {

    public CircleIndicatorView(Context context) {
        super(context);
    }

    @Override
    protected void drawIndicator(Canvas canvas, int size, Paint paint) {
        canvas.drawCircle(0.5f * size, 0.5f * size, 0.5f * size, paint);
    }

    @Override
    public View getInstance() {
        return new CircleIndicatorView(getContext());
    }

    @Override
    public void setIndicatorNormal() {
        setIndicatorColor(0xffD1D1D1);
    }

    @Override
    public void setIndicatorSelected() {
        setIndicatorColor(0xff03A9F4);
    }
}
