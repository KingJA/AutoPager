package com.kingja.autopager.pager;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/8/4 17:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseAutoAdapter<T> implements AutoAdapter<T> {
    protected Context context;
    protected List<T> list;

    public BaseAutoAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    public abstract View getView(T data, int position);

    @Override
    public List<T> getData() {
        return list;
    }
}
