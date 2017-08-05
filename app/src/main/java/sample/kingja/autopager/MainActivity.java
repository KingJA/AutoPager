package sample.kingja.autopager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.kingja.autopager.AutoPager;
import com.kingja.autopager.Pager;

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
        final AutoPager autopager = (AutoPager) findViewById(R.id.autopager);
        SwitchCompat swtich_autoRool = (SwitchCompat) findViewById(R.id.swtich_autoRool);
        autopager.setAdapter(new MyAutoPagerAdapter(this, pagers));
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
