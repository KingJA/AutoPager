package com.kingja.autopager.indicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Description:TODO
 * Create Time:2017/8/5 11:03
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BitmapIndicatorView extends IndicatorView {
    protected Bitmap bitmap;

    public BitmapIndicatorView(Context context) {
        super(context);
    }

    @Override
    protected void drawIndicator(Canvas canvas, int size, Paint paint) {

        if (bitmap == null) {
            return;
        }
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new Rect(0, 0, size, size),
                paint);
    }

    protected abstract Bitmap getNormalBitmap();

    protected abstract Bitmap getSelectedBitmap();

    @Override
    public abstract View getInstance();

    @Override
    public void setIndicatorNormal() {
        bitmap = getNormalBitmap();
        invalidate();
    }

    @Override
    public void setIndicatorSelected() {
        bitmap = getSelectedBitmap();
        invalidate();
    }
}
