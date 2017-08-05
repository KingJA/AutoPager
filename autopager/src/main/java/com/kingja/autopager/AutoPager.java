package com.kingja.autopager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

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
    private AutoAdapter adapter;

    public AutoPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AutoPager);
        autoRoll = a.getBoolean(R.styleable.AutoPager_autopager_autoRoll, DEFAULT_AUTO_ROLL);
        period = a.getInt(R.styleable.AutoPager_autopager_period, DEFAULT_PERIOD_MILLIS);
        actionColor = a.getColor(R.styleable.AutoPager_autopager_indicatorActionColor, DEFAULT_INDICATOR_ACTION_COLOR);
        normalColor = a.getColor(R.styleable.AutoPager_autopager_indicatorNormalColor, DEFAULT_INDICATOR_NORMAL_COLOR);
        indicatorGravity = a.getInt(R.styleable.AutoPager_autopager_indicatorGravity, DEFAULT_INDICATOR_GRAVITY);
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        showAutoPager();
    }


    public <T> void setAdapter(AutoAdapter<T> adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("adapter must not be null");
        }
        this.adapter = adapter;
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

    /*==================================================================================================*/


    private void showAutoPager() {
        stepViewPager();
        stepIndicator();
        setRollable();
    }

    private void stepViewPager() {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        layoutParams.height = getMeasuredHeight();
        viewPager = new ViewPager(getContext());
        viewPager.setAdapter(new AutoPagerAdapter(adapter));
        viewPager.addOnPageChangeListener(autoPagerChangeListener);
        addView(viewPager, layoutParams);
    }

    private void setRollable() {
        if (autoRoll) {
            autoRunnable = new AutoRunnable();
            autoRunnable.start();
        }
    }

    private void stepIndicator() {
        List datas = adapter.getData();
        LinearLayout.LayoutParams indicatorLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams llLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        llLayoutParams.gravity = indicatorGravity;
        llLayoutParams.setMargins(indicatorMarginLeft, indicatorMarginTop, indicatorMarginRight, indicatorMarginBottom);
        LinearLayout linearLayout = new LinearLayout(getContext());
        indicatorLp.setMargins(0, 0, indicatorSpacint, 0);
        for (int i = 0; i < datas.size(); i++) {
            IndicatorView indicator = new IndicatorView(getContext());
            indicator.setIndicatorSize(indicatorSize);
            if (i == 0) {
                indicator.setIndicatorColor(actionColor);
            } else {
                indicator.setIndicatorColor(normalColor);
            }
            if (i == datas.size() - 1) {
                indicatorLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                        .LayoutParams.WRAP_CONTENT);
                indicatorLp.setMargins(0, 0, 0, 0);
            }
            linearLayout.addView(indicator, indicatorLp);
            indicators.add(indicator);
        }
        addView(linearLayout, llLayoutParams);
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
            int index = position % indicators.size();
            for (int i = 0; i < indicators.size(); i++) {
                if (i == index) {
                    indicators.get(i).setIndicatorColor(actionColor);
                } else {
                    indicators.get(i).setIndicatorColor(normalColor);
                }
            }
        }
    };

    private class AutoPagerAdapter<T> extends PagerAdapter {
        private List<T> list;
        private AutoAdapter<T> adapter;

        AutoPagerAdapter(AutoAdapter<T> adapter) {
            this.list = adapter.getData();
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
            View view = adapter.setLayout(list.get(position % list.size()));
            container.addView(view);
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }


}
