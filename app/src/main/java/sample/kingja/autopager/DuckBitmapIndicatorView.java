package sample.kingja.autopager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.kingja.autopager.indicator.BitmapIndicatorView;

/**
 * Description:TODO
 * Create Time:2020/3/31 22:35
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DuckBitmapIndicatorView extends BitmapIndicatorView {
    public DuckBitmapIndicatorView(Context context) {
        super(context);
    }

    @Override
    protected Bitmap getNormalBitmap() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_duck_nor);
    }

    @Override
    protected Bitmap getSelectedBitmap() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_duck_sel);
    }

    @Override
    public View getInstance() {
        return new DuckBitmapIndicatorView(getContext());
    }
}
