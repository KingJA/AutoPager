package com.kingja.autopager.index;

import android.content.Context;
import android.view.View;

/**
 * Description:TODO
 * Create Time:2020/4/2 22:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseIndexBar implements IndexBar {
    protected Context context;
    private View view;

    public BaseIndexBar(Context context) {
        this.context = context;
        view = getView(context);
    }

    @Override
    public abstract View getView(Context context);

    @Override
    public View getIndexView() {
        return view;
    }

    @Override
    public void drawIndex(int position, int totalCount) {
        drawIndex(view, position, totalCount);
    }

    protected abstract void drawIndex(View view, int position, int totalCount);

}
