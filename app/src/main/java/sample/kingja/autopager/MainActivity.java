package sample.kingja.autopager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kingja.autopager.index.BaseIndexBar;
import com.kingja.autopager.pager.AutoPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] resIds = {R.mipmap.star_1, R.mipmap.star_2, R.mipmap.star_3, R.mipmap.star_4, R.mipmap.star_5};
    private List<Pager> pagers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        final AutoPager autopager =  findViewById(R.id.autopager);
        SwitchCompat swtich_autoRool =  findViewById(R.id.swtich_autoRool);
        autopager.setAdapter(new MyAutoPagerAdapter(this, pagers));
        autopager.setIndicator(new DuckBitmapIndicatorView(this));
        autopager.setIndexBar(new BaseIndexBar(this) {
            @Override
            public View getView(Context context) {
                TextView textView = new TextView(context);
                textView.setBackgroundResource(R.drawable.shape_index);
                textView.setTextColor(Color.WHITE);
                return textView;
            }

            @Override
            public void drawIndex(View view, int position, int totalCount) {
                TextView indexView= (TextView) view;
                indexView.setText(String.format("%d/%d",position,totalCount));
            }
        });
        swtich_autoRool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    autopager.startRoll();
                }else{
                    autopager.stopRoll();
                }
            }
        });

    }

    private void initData() {
        for (int i = 0; i < resIds.length; i++) {
            pagers.add(new Pager(resIds[i], "星空" + i));
        }
    }
}
