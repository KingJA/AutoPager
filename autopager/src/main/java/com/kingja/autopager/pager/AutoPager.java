package com.kingja.autopager.pager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kingja.autopager.R;
import com.kingja.autopager.index.IndexBar;
import com.kingja.autopager.indicator.DrawableIndicatorView;
import com.kingja.autopager.indicator.Indicator;
import com.kingja.autopager.indicator.IndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Description:TODO
 * Create Time:2017/8/4 13:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AutoPager extends FrameLayout {

    private static final String TAG = "AutoPager";
    private static final int DEFAULT_INDICATOR_ACTION_COLOR = 0xff00ffff;
    private static final int DEFAULT_INDICATOR_NORMAL_COLOR = 0xffffffff;
    private static final int DEFAULT_PERIOD_MILLIS = 2000;
    private static final int DEFAULT_INDICATOR_GRAVITY = 0x50;
    private static final float DEFAULT_INDICATOR_MARGIN = 12f;
    private static final float DEFAULT_INDICATOR_SIZE = 24f;
    private static final float DEFAULT_INDICATOR_SPACING = 12f;
    private static final float DEFAULT_HEIGHT = 160;
    private static final boolean DEFAULT_AUTO_ROLL = true;
    private boolean autoRoll;
    private int period;
    private int actionColor;
    private int normalColor;
    private int indicatorGravity;
    private int indicatorSize;
    private int indicatorSpacint;
    private int indicatorMarginLeft;
    private int indicatorMarginTop;
    private int indicatorMarginRight;
    private int indicatorMarginBottom;
    private ViewPager viewPager;
    private AutoRunnable autoRunnable;
    private List<IndicatorView> indicators = new ArrayList<>();
    private Indicator indicator;
    private int count;
    private IndexBar indexBar;
    private Drawable indicatorDrawable;

    public AutoPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initAutoPager();
    }

    private void initAutoPager() {

    }

    private void initAttr(AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AutoPager);
        autoRoll = a.getBoolean(R.styleable.AutoPager_autopager_autoRoll, DEFAULT_AUTO_ROLL);
        period = a.getInt(R.styleable.AutoPager_autopager_period, DEFAULT_PERIOD_MILLIS);
        actionColor = a.getColor(R.styleable.AutoPager_autopager_indicatorActionColor, DEFAULT_INDICATOR_ACTION_COLOR);
        normalColor = a.getColor(R.styleable.AutoPager_autopager_indicatorNormalColor, DEFAULT_INDICATOR_NORMAL_COLOR);
        indicatorGravity = a.getInt(R.styleable.AutoPager_autopager_indicatorGravity, DEFAULT_INDICATOR_GRAVITY);
        indicatorDrawable = a.getDrawable(R.styleable.AutoPager_autopager_indicatorDrawable);
        indicatorSize = (int) a.getDimension(R.styleable.AutoPager_autopager_indicatorSize, DEFAULT_INDICATOR_SIZE);

        indicatorMarginLeft = (int) a.getDimension(R.styleable.AutoPager_autopager_indicatorMarginLeft,
                DEFAULT_INDICATOR_MARGIN);
        indicatorMarginTop = (int) a.getDimension(R.styleable.AutoPager_autopager_indicatorMarginTop,
                DEFAULT_INDICATOR_MARGIN);
        indicatorMarginRight = (int) a.getDimension(R.styleable.AutoPager_autopager_indicatorMarginRight,
                DEFAULT_INDICATOR_MARGIN);
        indicatorMarginBottom = (int) a.getDimension(R.styleable.AutoPager_autopager_indicatorMarginBottom,
                DEFAULT_INDICATOR_MARGIN);

        indicatorSpacint = (int) a.getDimension(R.styleable.AutoPager_autopager_indicatorSpacing,
                DEFAULT_INDICATOR_SPACING);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            int defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEIGHT,
                    getContext().getResources().getDisplayMetrics());
            setMeasuredDimension(getMeasuredWidth(), defaultHeight);
        }
    }

    public <T> void setAdapter(AutoAdapter<T> adapter) {
        Log.e(TAG, "setAdapter: ");
        if (adapter == null) {
            throw new IllegalArgumentException("adapter must not be null");
        }
        stepViewPager(adapter);
        count = adapter.getData().size();

    }

    private <T> void stepViewPager(AutoAdapter<T> adapter) {
        Log.e(TAG, "stepViewPager: ");
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .MATCH_PARENT);
        viewPager = new ViewPager(getContext());
        viewPager.setAdapter(new AutoPagerAdapter<>(adapter));
        viewPager.addOnPageChangeListener(autoPagerChangeListener);
        addView(viewPager, layoutParams);
    }

    public void startRoll() {
        if (autoRunnable == null) {
            autoRunnable = new AutoRunnable();
        }
        autoRunnable.start();
    }

    public void stopRoll() {
        if (autoRunnable != null) {
            removeCallbacks(autoRunnable);
        }
    }

    private void setRollable() {
        Log.e(TAG, "setRollable: ");
        if (autoRoll) {
            autoRunnable = new AutoRunnable();
            autoRunnable.start();
        }
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
        stepIndicator();
        setRollable();
    }

    public void setIndexBar(IndexBar indexBar) {
        this.indexBar = indexBar;
        FrameLayout.LayoutParams flLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        flLayoutParams.gravity = Gravity.END | Gravity.TOP;
        flLayoutParams.setMargins(indicatorMarginLeft, indicatorMarginTop, indicatorMarginRight, indicatorMarginBottom);
        indexBar.drawIndex(1,count);
        addView(indexBar.getIndexView(), flLayoutParams);
    }

    private void stepIndicator() {
        Log.e(TAG, "stepIndicator: ");
        LinearLayout.LayoutParams indicatorLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams flLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        flLayoutParams.gravity = indicatorGravity;
        flLayoutParams.setMargins(indicatorMarginLeft, indicatorMarginTop, indicatorMarginRight, indicatorMarginBottom);
        LinearLayout linearLayout = new LinearLayout(getContext());
        indicatorLp.setMargins(0, 0, indicatorSpacint, 0);
        for (int i = 0; i < count; i++) {

            IndicatorView indicatorView = (IndicatorView) indicator.getInstance();
            if (indicatorDrawable != null) {
                indicatorView=new DrawableIndicatorView(getContext(), (LayerDrawable) indicatorDrawable);
            }
            indicatorView.setIndicatorSize(indicatorSize);
            if (i == 0) {
                indicatorView.setIndicatorSelected();
            } else {
                indicatorView.setIndicatorNormal();
            }
            if (i == count - 1) {
                indicatorLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                        .LayoutParams.WRAP_CONTENT);
                indicatorLp.setMargins(0, 0, 0, 0);
            }
            linearLayout.addView(indicatorView, indicatorLp);
            indicators.add(indicatorView);
        }
        addView(linearLayout, flLayoutParams);
    }

    private class AutoRunnable implements Runnable {
        void start() {
            removeCallbacks(autoRunnable);
            postDelayed(autoRunnable, period);
        }

        @Override
        public void run() {
            removeCallbacks(autoRunnable);
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(++currentItem);
            postDelayed(autoRunnable, period);
        }
    }

    private ViewPager.OnPageChangeListener autoPagerChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            Log.e(TAG, "onPageSelected: " + position);
            int index = position % indicators.size();
            for (int i = 0; i < indicators.size(); i++) {
                if (i == index) {
                    indicators.get(i).setIndicatorSelected();
                } else {
                    indicators.get(i).setIndicatorNormal();
                }
            }

            if (indexBar != null) {
                indexBar.drawIndex(index+1,count);
            }
        }
    };

    private class AutoPagerAdapter<T> extends PagerAdapter {
        private List<T> list;
        private AutoAdapter<T> adapter;

        AutoPagerAdapter(AutoAdapter<T> adapter) {
            this.list = adapter.getData() == null ? new ArrayList<T>() : adapter.getData();
            this.adapter = adapter;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e(TAG, "instantiateItem: " + position);
            View view = adapter.getView(list.get(position % list.size()), position % list.size());
            container.addView(view);
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }


}
