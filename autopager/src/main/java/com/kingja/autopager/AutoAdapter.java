package com.kingja.autopager;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/8/4 15:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface AutoAdapter<T> {
    View setLayout(T data);

    List<T> getData();

}
