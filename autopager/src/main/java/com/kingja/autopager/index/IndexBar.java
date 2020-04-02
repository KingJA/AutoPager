package com.kingja.autopager.index;

import android.content.Context;
import android.view.View;

/**
 * Description:TODO
 * Create Time:2020/4/2 21:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface IndexBar {
    View getView(Context context);
    View getIndexView();
    void drawIndex(int position, int totalCount);
}
