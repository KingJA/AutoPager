package com.kingja.autopager.pager;

import android.view.View;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/8/4 15:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface AutoAdapter<T> {
    View getView(T data, int position);

    List<T> getData();

}
