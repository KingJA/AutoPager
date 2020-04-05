package sample.kingja.autopager;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.autopager.index.BaseIndexBar;
import com.kingja.autopager.pager.AutoPager;
import com.kingja.autopager.pager.BaseAutoAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] jdImgRes = {R.mipmap.jd1, R.mipmap.jd2, R.mipmap.jd3, R.mipmap.jd4, R.mipmap.jd5};
    private int[] mkImgRes = {R.mipmap.mk1, R.mipmap.mk2, R.mipmap.mk3, R.mipmap.mk4, R.mipmap.mk5};
    private int[] zfbImgRes = {R.mipmap.mk1, R.mipmap.mk2, R.mipmap.mk3, R.mipmap.mk4, R.mipmap.mk5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Pager> jdPagers = getJdPagers();
        List<Pager> mkPagers = getMkPagers();
        AutoPager autopager = findViewById(R.id.autopager);
        AutoPager autopagerMk = findViewById(R.id.autopager_mk);
        AutoPager autopagerJfb = findViewById(R.id.autopager_zfb);


        autopager.setAdapter(new BaseAutoAdapter<Pager>(this, jdPagers) {
            @Override
            public View getView(Pager data, int position) {
                SquareImageView squareImageView = new SquareImageView(MainActivity.this);
                squareImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                squareImageView.setImageResource(data.getResId());
                return squareImageView;
            }
        });
        autopager.setIndexBar(new BaseIndexBar(this) {
            @Override
            public View getView(Context context) {
                return View.inflate(context, R.layout.layout_index_jd, null);
            }

            @Override
            public void drawIndex(View view, int position, int totalCount) {
                TextView tvPosition = view.findViewById(R.id.tv_position);
                TextView tvTotalCount = view.findViewById(R.id.tv_totalCount);
                tvPosition.setText(String.valueOf(position));
                tvTotalCount.setText(String.valueOf(totalCount));
            }
        });

        autopagerMk.setAdapter(new BaseAutoAdapter<Pager>(this, mkPagers) {
            @Override
            public View getView(Pager data, int position) {
                SquareImageView squareImageView = new SquareImageView(MainActivity.this);
                squareImageView.setScaleType(ImageView.ScaleType.FIT_START);
                squareImageView.setImageResource(data.getResId());
                return squareImageView;
            }
        });


        autopagerJfb.setAdapter(new BaseAutoAdapter<Pager>(this, mkPagers) {
            @Override
            public View getView(Pager data, int position) {
                SquareImageView squareImageView = new SquareImageView(MainActivity.this);
                squareImageView.setScaleType(ImageView.ScaleType.FIT_START);
                squareImageView.setImageResource(data.getResId());
                return squareImageView;
            }
        });

    }

    private List<Pager> getMkPagers() {
        List<Pager> pagers = new ArrayList<>();
        for (int i = 0; i < mkImgRes.length; i++) {
            pagers.add(new Pager(mkImgRes[i], "星空" + i));
        }
        return pagers;
    }

    private List<Pager> getJdPagers() {
        List<Pager> pagers = new ArrayList<>();
        for (int i = 0; i < jdImgRes.length; i++) {
            pagers.add(new Pager(jdImgRes[i], "星空" + i));
        }

        return pagers;
    }
}
