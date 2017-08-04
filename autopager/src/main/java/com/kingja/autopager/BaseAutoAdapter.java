package com.kingja.autopager;

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

    public abstract View setLayout(T data);

    @Override
    public List<T> getData() {
        return list;
    }
}
