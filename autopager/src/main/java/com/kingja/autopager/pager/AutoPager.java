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

    private final String TAG = getClass().getSimpleName();
    private static final int DEFAULT_PERIOD_MILLIS = 2000;
    private static final int DEFAULT_INDICATOR_GRAVITY = 0x50;
    private static final int DEFAULT_INDEXBAR_GRAVITY = Gravity.BOTTOM | Gravity.RIGHT;
    private static final int DEFAULT_INDICATOR_MARGIN = 12;
    private static final int DEFAULT_INDEXBAR_MARGIN = 0;
    private static final int DEFAULT_INDICATOR_HEIGHT = 24;
    private static final int DEFAULT_INDICATOR_WIDTH = 24;
    private static final int DEFAULT_INDICATOR_SPACING = 12;
    private static final int DEFAULT_HEIGHT = 160;
    private static final boolean DEFAULT_AUTO_ROLL = false;
    private static final boolean DEFAULT_UNLIMITED = false;
    private boolean autoRoll;
    private int period;
    private int indicatorGravity;
    private int indicatorWidth;
    private int indicatorHeight;
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
    private int indexBarMargin;
    private int indexBarGravity;
    private int indexBarMarginBottom;
    private int indexBarMarginRight;
    private int indexBarMarginTop;
    private int indexBarMarginLeft;
    private boolean unlimited;

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
        if (indicatorDrawable != null) {
            indicator = new DrawableIndicatorView(getContext(), (LayerDrawable) indicatorDrawable);
        }
    }

    private void addIndicatior() {
        LinearLayout.LayoutParams indicatorlLLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams flLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        flLayoutParams.gravity = indicatorGravity;
        flLayoutParams.setMargins(indicatorMarginLeft, indicatorMarginTop, indicatorMarginRight, indicatorMarginBottom);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setId(R.id.ll_indicatior);
        indicatorlLLp.setMargins(0, 0, indicatorSpacint, 0);
        for (int i = 0; i < count; i++) {
            IndicatorView indicatorView = (IndicatorView) indicator.getInstance();
            indicatorView.setIndicatorSize(indicatorWidth,indicatorHeight);
            if (i == 0) {
                indicatorView.setIndicatorSelected();
            } else {
                indicatorView.setIndicatorNormal();
            }
            if (i == count - 1) {
                indicatorlLLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                        .LayoutParams.WRAP_CONTENT);
                indicatorlLLp.setMargins(0, 0, 0, 0);
            }
            linearLayout.addView(indicatorView, indicatorlLLp);
            indicators.add(indicatorView);
        }
        addView(linearLayout, flLayoutParams);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AutoPager);
        autoRoll = a.getBoolean(R.styleable.AutoPager_autopager_autoRoll, DEFAULT_AUTO_ROLL);
        unlimited = a.getBoolean(R.styleable.AutoPager_autopager_unlimited, DEFAULT_UNLIMITED);
        period = a.getInt(R.styleable.AutoPager_autopager_period, DEFAULT_PERIOD_MILLIS);
        indicatorGravity = a.getInt(R.styleable.AutoPager_autopager_indicatorGravity, DEFAULT_INDICATOR_GRAVITY);
        indicatorDrawable = a.getDrawable(R.styleable.AutoPager_autopager_indicatorDrawable);

        indicatorWidth = (int) a.getDimension(R.styleable.AutoPager_autopager_indicatorWidth, DEFAULT_INDICATOR_WIDTH);
        indicatorHeight = (int) a.getDimension(R.styleable.AutoPager_autopager_indicatorHeight,DEFAULT_INDICATOR_HEIGHT );

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

        indexBarMargin = (int) a.getDimension(R.styleable.AutoPager_autopager_indexBarMargin,
                DEFAULT_INDEXBAR_MARGIN);
        indexBarGravity = a.getInt(R.styleable.AutoPager_autopager_indexBarGravity, DEFAULT_INDEXBAR_GRAVITY);

        indexBarMarginLeft = (int) a.getDimension(R.styleable.AutoPager_autopager_indexBarMarginLeft,
                DEFAULT_INDEXBAR_MARGIN);
        indexBarMarginTop = (int) a.getDimension(R.styleable.AutoPager_autopager_indexBarMarginTop,
                DEFAULT_INDEXBAR_MARGIN);
        indexBarMarginRight = (int) a.getDimension(R.styleable.AutoPager_autopager_indexBarMarginRight,
                DEFAULT_INDEXBAR_MARGIN);
        indexBarMarginBottom = (int) a.getDimension(R.styleable.AutoPager_autopager_indexBarMarginBottom,
                DEFAULT_INDEXBAR_MARGIN);
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

        Log.e(TAG, "onSizeChanged getMeasuredWidth: " + getMeasuredWidth());
        Log.e(TAG, "onSizeChanged getMeasuredHeight: " + getMeasuredHeight());
    }

    public <T> void setAdapter(AutoAdapter<T> adapter) {
        Log.e(TAG, "setAdapter: ");
        if (adapter == null) {
            throw new IllegalArgumentException("adapter must not be null");
        }
        stepViewPager(adapter);
        count = adapter.getData().size();
        if (indicator != null) {
            addIndicatior();
        }
        if (autoRoll) {
            startRoll();
        }

    }

    private <T> void stepViewPager(AutoAdapter<T> adapter) {
        Log.e(TAG, "stepViewPager: ");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        viewPager = new ViewPager(getContext());
        viewPager.setId(R.id.viewpager);
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
        flLayoutParams.gravity = indexBarGravity;
        flLayoutParams.setMargins(indexBarMarginLeft, indexBarMarginTop, indexBarMarginRight, indexBarMarginBottom);
        indexBar.drawIndex(1, count);
        addView(indexBar.getIndexView(), flLayoutParams);
    }

    private void stepIndicator() {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView.getId() == R.id.ll_indicatior) {
                removeView(childView);
                indicators.clear();
                break;
            }
        }
        addIndicatior();
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
            int index = position % count;
            if (indicators.size() > 0) {
                for (int i = 0; i < indicators.size(); i++) {
                    if (i == index) {
                        indicators.get(i).setIndicatorSelected();
                    } else {
                        indicators.get(i).setIndicatorNormal();
                    }
                }
            }

            if (indexBar != null) {
                indexBar.drawIndex(index + 1, count);
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
            return unlimited || autoRoll ? Integer.MAX_VALUE : list.size();
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
