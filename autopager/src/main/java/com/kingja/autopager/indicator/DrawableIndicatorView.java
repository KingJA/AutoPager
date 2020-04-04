package com.kingja.autopager.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;

import com.kingja.autopager.R;

/**
 * Description:TODO
 * Create Time:2020/4/4 12:26
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DrawableIndicatorView extends IndicatorView  {
    private final LayerDrawable layerDrawable;

    public DrawableIndicatorView(Context context, LayerDrawable layerDrawable) {
        super(context);
        this.layerDrawable = layerDrawable;
    }

    @Override
    protected void drawIndicator(Canvas canvas, int size, Paint paint) {

    }

    @Override
    public void setIndicatorNormal() {
        Drawable normalDrawable = layerDrawable.findDrawableByLayerId(android.R.id.background);
        setBackground(normalDrawable);
    }

    @Override
    public void setIndicatorSelected() {
        Drawable actionDrawable = layerDrawable.findDrawableByLayerId(android.R.id.progress );
        setBackground(actionDrawable);
    }

    @Override
    public View getInstance() {
        return null;
    }
}
