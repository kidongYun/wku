package com.example.yun.wku;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class WKUMainFragment extends Fragment implements View.OnClickListener {
    LinearLayout bookmarkFirst;
    LinearLayout bookmarkSecond;
    LinearLayout bookmarkThird;
    LinearLayout bookmarkFourth;

    private ViewPager viewPager;
    private Integer[] images = {
            R.drawable.wkumainfragment_advertise1,
            R.drawable.wkumainfragment_advertise2};

    private int currentPage = 0;

    public WKUMainFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wkumain, container, false);

        bookmarkFirst = rootView.findViewById(R.id.bookmark_first);
        bookmarkSecond = rootView.findViewById(R.id.bookmark_second);
        bookmarkThird = rootView.findViewById(R.id.bookmark_third);
        bookmarkFourth = rootView.findViewById(R.id.bookmark_fourth);

        bookmarkFirst.setOnClickListener(this);
        bookmarkSecond.setOnClickListener(this);
        bookmarkThird.setOnClickListener(this);
        bookmarkFourth.setOnClickListener(this);

        viewPager = rootView.findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), images);
        viewPager.setAdapter(viewPagerAdapter);

        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext());

            mScroller.set(viewPager, scroller);
        } catch (Exception e) {
        }

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 7000, 7000);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.bookmark_first:
                intent = new Intent(getActivity(), WKUInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.bookmark_second:
                intent = new Intent(getActivity(), WKUBBSActivity.class);
                startActivity(intent);
                break;

            case R.id.bookmark_third:
                intent = new Intent(getActivity(), WKUMapActivity.class);
                startActivity(intent);
                break;

            case R.id.bookmark_fourth:
                Toast.makeText(getActivity(), "준비중입니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

class FixedSpeedScroller extends Scroller {

    private int mDuration = 3000;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}

class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images;

    public ViewPagerAdapter(Context context, Integer[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_wkumain_slider, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageBitmap(decodeBitmapFromResource(context.getResources(), images[position], 100, 100));

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager)container;
        View view = (View)object;
        vp.removeView(view);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
