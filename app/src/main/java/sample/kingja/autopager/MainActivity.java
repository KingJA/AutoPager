package sample.kingja.autopager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        AutoPager ap = (AutoPager) findViewById(R.id.ap);
        initData();
        ap.start(new MyAutoPagerAdapter(this, pagers));

    }

    private void initData() {
        for (int i = 0; i < resIds.length; i++) {
            pagers.add(new Pager(resIds[i], "星空" + i));
        }
    }
}
