package sample.kingja.autopager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.autopager.pager.BaseAutoAdapter;

import java.util.List;


/**
 * Description:TODO
 * Create Time:2017/8/4 16:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MyAutoPagerAdapter extends BaseAutoAdapter<Pager> {

    private static final String TAG = "MyAutoPagerAdapter";

    public MyAutoPagerAdapter(Context context, List<Pager> list) {
        super(context, list);
    }

    @Override
    public View getView(Pager data, int position) {
        Log.e(TAG, "getView: "+position);
        View rootView = View.inflate(context, R.layout.vp_item, null);
        ImageView imageView =  rootView.findViewById(R.id.iv);
        TextView tv_title =  rootView.findViewById(R.id.tv_title);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundResource(data.getResId());
        tv_title.setText(data.getTitle());
        return rootView;
    }
}
