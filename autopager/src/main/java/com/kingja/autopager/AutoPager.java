package com.kingja.autopager;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
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

    private ViewPager viewPager;
    private AutoRunnable autoRunnable;
    private int delayMillis = 3000;
    private List<View> doits = new ArrayList<>();

    public AutoPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAutoPager();
    }

    private void initAutoPager() {
        stepViewPager();
    }

    private <T> void stepDoit(List<T> list) {
        LinearLayout.LayoutParams doitLayoutParams = new LinearLayout.LayoutParams(24, 24);
        FrameLayout.LayoutParams llLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams
                        .WRAP_CONTENT);
        llLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        LinearLayout linearLayout = new LinearLayout(getContext());
        doitLayoutParams.setMargins(0, 0, 12, 12);
        for (int i = 0; i < list.size(); i++) {
            View doit = new View(getContext());
            if (i == 0) {
                doit.setBackgroundResource(R.drawable.doit_action);
            } else {
                doit.setBackgroundResource(R.drawable.doit_nor);
            }
            doits.add(doit);
            linearLayout.addView(doit, doitLayoutParams);
        }

        addView(linearLayout, llLayoutParams);


    }

    private void stepViewPager() {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        layoutParams.height = 300;
        viewPager = new ViewPager(getContext());
        addView(viewPager, layoutParams);
    }

    public <T> void start(AutoAdapter<T> adapter) {
        stepDoit(adapter.getData());
        AutoPagerAdapter autoPagerAdapter = new AutoPagerAdapter(adapter);
        viewPager.setAdapter(autoPagerAdapter);
        viewPager.addOnPageChangeListener(autoPagerChangeListener);
        autoRunnable = new AutoRunnable();
        autoRunnable.start();
    }

    public void stop() {
        removeCallbacks(autoRunnable);
    }

    private class AutoRunnable implements Runnable {
         void start() {
            removeCallbacks(autoRunnable);
            postDelayed(autoRunnable, delayMillis);

        }

        @Override
        public void run() {
            removeCallbacks(autoRunnable);
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(++currentItem);
            postDelayed(autoRunnable, delayMillis);
        }
    }

    private ViewPager.OnPageChangeListener autoPagerChangeListener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int index = position % doits.size();
            for (int i = 0; i < doits.size(); i++) {
                if (i == index) {
                    doits.get(index).setBackgroundResource(R.drawable.doit_action);
                } else {
                    doits.get(i).setBackgroundResource(R.drawable.doit_nor);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


   private class AutoPagerAdapter extends PagerAdapter {
        private List list;
        private AutoAdapter adapter;

         <T> AutoPagerAdapter(AutoAdapter<T> adapter) {
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
