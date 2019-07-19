package com.example.yun.wku;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WKUBBSActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int NOTI_PAGE_INDEX = 0;
    public static final int ROOM_PAGE_INDEX = 1;
    public static final int MARKET_PAGE_INDEX = 2;
    public static final int JOB_PAGE_INDEX = 3;

    private ProgressBar loadingBar;

    private String wkuId, wkuPw;
    private String isDorm = "";
    private String jSessionId = "";
    private String wkuTokenKey = "";


    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;
    private ImageView bbsSearchBtn;
    private ImageView wkuLogoImg;

    private LinearLayout bbsNotiBtn;
    private LinearLayout bbsRoomBtn;
    private LinearLayout bbsMarketBtn;
    private LinearLayout bbsJobBtn;

    private DrawerLayout bbsDrawer;
    private LinearLayout bbsRightDrawer;

    private ViewPager bbsPager;
    private WKUBBSPagerAdapter wkubbsPagerAdapter;

    public int curBBSPage = NOTI_PAGE_INDEX;

    private LinearLayout bookmarkAttendMenu;
    private LinearLayout bookmarkScheduleMenu;
    private LinearLayout bookmarkScholarMenu;
    private LinearLayout bookmarkGradeMenu;
    private LinearLayout bookmarkDormMenu;
    private LinearLayout bookmarkMenstMenu;
    private LinearLayout bookmarkSettingMenu;
    private LinearLayout bookmarkComplainMenu;

    private ImageView bookmarkAttendImageView;
    private ImageView bookmarkScheduleImageView;
    private ImageView bookmarkScholarImageView;
    private ImageView bookmarkGradeImageView;
    private ImageView bookmarkDormImageView;
    private ImageView bookmarkMenstImageView;
    private ImageView bookmarkSettingImageView;
    private ImageView bookmarkComplainImageView;

    private WKUBBSNotiFragment wkuBbsNotiFragment;
    private WKUBBSRoomFragment wkuBbsRoomFragment;
    private WKUBBSMarketFragment wkuBbsMarketFragment;
    private WKUBBSJobFragment wkuBbsJobFragment;

    private LinearLayout tabBar;
    private LinearLayout search_box;
    private EditText search_edittext;
    private ImageView search_btn;

    private WKUScrapingEngine wkuScrapingEngine;

    private WKUDatabase wkuDatabase;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {
                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.BBS_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();

            } else if(msg.what == WKUDBThread.DB_FINISH_FLAG) {
                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);

                wkuBbsNotiFragment.getBbsNotiData(wkuDatabase);
                wkuBbsRoomFragment.getBbsRoomData(wkuDatabase);
                wkuBbsJobFragment.getBbsJobData(wkuDatabase);
                wkuBbsMarketFragment.getBbsMarketData(wkuDatabase);
            } else if(msg.what == WKUScrapingEngine.SCRAPING_NOTI_SEARCH_FINISH_MODE) {
                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);

                wkuBbsNotiFragment.getBbsNotiData(wkuScrapingEngine, WKUBBSData.SEARCH_MODE);
            } else if(msg.what == WKUScrapingEngine.SCRAPING_ROOM_SEARCH_FINISH_MODE) {
                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);

                wkuBbsRoomFragment.getBbsRoomData(wkuScrapingEngine, WKUBBSData.SEARCH_MODE);
            } else if(msg.what == WKUScrapingEngine.SCRAPING_JOB_SEARCH_FINISH_MODE) {
                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);

                wkuBbsJobFragment.getBbsJobData(wkuScrapingEngine, WKUBBSData.SEARCH_MODE);
            } else if(msg.what == WKUScrapingEngine.SCRAPING_MARKET_SEARCH_FINISH_MODE) {
                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);

                wkuBbsMarketFragment.getBbsMarketData(wkuScrapingEngine, WKUBBSData.SEARCH_MODE);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkubbs);

        init();
        initPager();
        search_btn.setOnClickListener(this);
        search_edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search_btn.callOnClick();
                    return true;
                }
                return false;
            }
        });

        wkuDatabase = new WKUDatabase(this);

        getScrapingData();

        loadingBar.setVisibility(View.VISIBLE);
        wkuLogoImg.setVisibility(View.GONE);

        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_BBS_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey);
        wkuScrapingEngine.start();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bbs_prev_btn :
                finish();
                break;

            case R.id.bbs_wkulogo_btn:
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;

            case R.id.bbs_bookmark_btn :
                bbsDrawer.openDrawer(bbsRightDrawer);
                break;

            case R.id.bbs_noti_btn :
                bbsPager.setCurrentItem(0);
                bbsNotiBtn.setBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
                bbsRoomBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsMarketBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsJobBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                break;

            case R.id.bbs_room_btn :
                bbsPager.setCurrentItem(1);
                bbsNotiBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsRoomBtn.setBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
                bbsMarketBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsJobBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                break;

            case R.id.bbs_market_btn :
                bbsPager.setCurrentItem(2);
                bbsNotiBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsRoomBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsMarketBtn.setBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
                bbsJobBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                break;

            case R.id.bbs_job_btn :
                bbsPager.setCurrentItem(3);
                bbsNotiBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsRoomBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsMarketBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                bbsJobBtn.setBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
                break;

            case R.id.bbs_search_btn :
                if(isCompleteSearchText()) {

                    loadingBar.setVisibility(View.VISIBLE);
                    wkuLogoImg.setVisibility(View.GONE);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search_edittext.getWindowToken(), 0);

                    if(curBBSPage == NOTI_PAGE_INDEX) {
                        wkuBbsNotiFragment.setSearchScrapingFlag(true);
                        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_BBS_NOTI_SEARCH_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, search_edittext.getText().toString());
                        wkuScrapingEngine.start();

                    } else if(curBBSPage == ROOM_PAGE_INDEX) {
                        wkuBbsRoomFragment.setSearchScrapingFlag(true);
                        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_BBS_ROOM_SEARCH_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, search_edittext.getText().toString());
                        wkuScrapingEngine.start();

                    } else if(curBBSPage == JOB_PAGE_INDEX) {
                        wkuBbsJobFragment.setSearchScrapingFlag(true);
                        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_BBS_JOB_SEARCH_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, search_edittext.getText().toString());
                        wkuScrapingEngine.start();

                    } else if(curBBSPage == MARKET_PAGE_INDEX) {
                        wkuBbsMarketFragment.setSearchScrapingFlag(true);
                        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_BBS_MARKET_SEARCH_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, search_edittext.getText().toString());
                        wkuScrapingEngine.start();
                    }
                }
                break;

            case R.id.bookmark_attend_menu :
                bbsDrawer.closeDrawer(bbsRightDrawer);
                finish();
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                bbsDrawer.closeDrawer(bbsRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                bbsDrawer.closeDrawer(bbsRightDrawer);
                finish();
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                bbsDrawer.closeDrawer(bbsRightDrawer);
                finish();
                Intent scoreIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(scoreIntent);
                break;

            case R.id.bookmark_dorm_menu :
                bbsDrawer.closeDrawer(bbsRightDrawer);
                if(isDorm.equals("YES")) {
                    finish();
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                bbsDrawer.closeDrawer(bbsRightDrawer);
                Toast.makeText(WKUBBSActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                bbsDrawer.closeDrawer(bbsRightDrawer);
                Toast.makeText(WKUBBSActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_complain_menu :
                bbsDrawer.closeDrawer(bbsRightDrawer);
                Toast.makeText(WKUBBSActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void init() {
        loadingBar = findViewById(R.id.bbs_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        prevBtn = findViewById(R.id.bbs_prev_btn);
        wkuLogoBtn = findViewById(R.id.bbs_wkulogo_btn);
        wkuLogoImg = findViewById(R.id.bbs_wkulogo_img);
        bookmarkBtn = findViewById(R.id.bbs_bookmark_btn);

        bbsNotiBtn = findViewById(R.id.bbs_noti_btn);
        bbsRoomBtn = findViewById(R.id.bbs_room_btn);
        bbsMarketBtn = findViewById(R.id.bbs_market_btn);
        bbsJobBtn = findViewById(R.id.bbs_job_btn);

        bbsDrawer = findViewById(R.id.bbs_drawer);
        bbsRightDrawer = findViewById(R.id.bbs_rightDrawer);

        bookmarkAttendMenu = findViewById(R.id.bookmark_attend_menu);
        bookmarkScheduleMenu = findViewById(R.id.bookmark_schedule_menu);
        bookmarkScholarMenu = findViewById(R.id.bookmark_scholar_menu);
        bookmarkGradeMenu = findViewById(R.id.bookmark_grade_menu);
        bookmarkDormMenu = findViewById(R.id.bookmark_dorm_menu);
        bookmarkMenstMenu = findViewById(R.id.bookmark_menst_menu);
        bookmarkSettingMenu = findViewById(R.id.bookmark_setting_menu);
        bookmarkComplainMenu = findViewById(R.id.bookmark_complain_menu);

        bookmarkAttendImageView = findViewById(R.id.bookmark_attend_imageview);
        bookmarkScheduleImageView = findViewById(R.id.bookmark_schedule_imageview);
        bookmarkScholarImageView = findViewById(R.id.bookmark_scholar_imageview);
        bookmarkGradeImageView = findViewById(R.id.bookmark_grade_imageview);
        bookmarkDormImageView = findViewById(R.id.bookmark_dorm_imageview);
        bookmarkMenstImageView = findViewById(R.id.bookmark_menst_imageview);
        bookmarkSettingImageView = findViewById(R.id.bookmark_setting_imageview);
        bookmarkComplainImageView = findViewById(R.id.bookmark_complain_imageview);

        tabBar = findViewById(R.id.bbs_tab_bar);
        search_box = findViewById(R.id.bbs_search_box);
        search_edittext = findViewById(R.id.bbs_search_edittext);
        search_edittext.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        search_btn = findViewById(R.id.bbs_search_btn);

        wkuBbsNotiFragment = new WKUBBSNotiFragment();
        wkuBbsRoomFragment = new WKUBBSRoomFragment();
        wkuBbsMarketFragment = new WKUBBSMarketFragment();
        wkuBbsJobFragment = new WKUBBSJobFragment();

        prevBtn.setOnClickListener(this);
        wkuLogoBtn.setOnClickListener(this);
        bookmarkBtn.setOnClickListener(this);

        bbsNotiBtn.setOnClickListener(this);
        bbsRoomBtn.setOnClickListener(this);
        bbsMarketBtn.setOnClickListener(this);
        bbsJobBtn.setOnClickListener(this);

        bookmarkAttendMenu.setOnClickListener(this);
        bookmarkScheduleMenu.setOnClickListener(this);
        bookmarkScholarMenu.setOnClickListener(this);
        bookmarkGradeMenu.setOnClickListener(this);
        bookmarkDormMenu.setOnClickListener(this);
        bookmarkMenstMenu.setOnClickListener(this);
        bookmarkSettingMenu.setOnClickListener(this);
        bookmarkComplainMenu.setOnClickListener(this);

        initImageResource();
    }

    private void initPager() {
        bbsPager = findViewById(R.id.bbs_content_container);
        wkubbsPagerAdapter = new WKUBBSPagerAdapter(getSupportFragmentManager(), this.wkuBbsNotiFragment, this.wkuBbsRoomFragment, this.wkuBbsMarketFragment, this.wkuBbsJobFragment);
        bbsPager.setAdapter(wkubbsPagerAdapter);

        bbsPager.setOffscreenPageLimit(3);
        bbsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0 :
                        curBBSPage = NOTI_PAGE_INDEX;
                        bbsNotiBtn.setBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
                        bbsRoomBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsMarketBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsJobBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        break;

                    case 1 :
                        curBBSPage = ROOM_PAGE_INDEX;
                        bbsNotiBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsRoomBtn.setBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
                        bbsMarketBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsJobBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        break;

                    case 2 :
                        curBBSPage = MARKET_PAGE_INDEX;
                        bbsNotiBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsRoomBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsMarketBtn.setBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
                        bbsJobBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        break;

                    case 3 :
                        curBBSPage = JOB_PAGE_INDEX;
                        bbsNotiBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsRoomBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsMarketBtn.setBackgroundColor(Color.argb(0x33, 0xaa, 0xaa, 0xaa));
                        bbsJobBtn.setBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private boolean isCompleteSearchText() {
        if(search_edittext.getText().toString().equals("")) {
            Toast.makeText(this, "검색어를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void getScrapingData() {
        try {
            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT wkuId, wkuPw, jSessionId, wkuTokenKey, studentNo, isDorm FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
                this.wkuId = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(0), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.wkuPw = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(1), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.jSessionId = cursor.getString(2);
                this.wkuTokenKey = cursor.getString(3);
                this.isDorm = cursor.getString(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinearLayout getSearchBox() { return this.search_box; }
    public LinearLayout getTabBar() { return this.tabBar; }

    private void initImageResource() {
        bookmarkAttendImageView.setImageBitmap(decodeBitmapFromResource(getResources(), R.drawable.all_right_attend, 25, 25));
        bookmarkScheduleImageView.setImageBitmap(decodeBitmapFromResource(getResources(), R.drawable.all_right_schedule, 25, 25));
        bookmarkScholarImageView.setImageBitmap(decodeBitmapFromResource(getResources(), R.drawable.all_right_scholar, 30, 30));
        bookmarkGradeImageView.setImageBitmap(decodeBitmapFromResource(getResources(), R.drawable.all_right_grade, 25, 25));
        bookmarkDormImageView.setImageBitmap(decodeBitmapFromResource(getResources(), R.drawable.all_right_dorm, 30, 30));
        bookmarkMenstImageView.setImageBitmap(decodeBitmapFromResource(getResources(), R.drawable.all_right_menst, 25, 25));
        bookmarkSettingImageView.setImageBitmap(decodeBitmapFromResource(getResources(), R.drawable.all_right_setting, 25, 25));
        bookmarkComplainImageView.setImageBitmap(decodeBitmapFromResource(getResources(), R.drawable.all_right_complain, 25, 25));
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private Bitmap decodeBitmapFromResource(Resources res, int resId,
                                            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}

class WKUBBSPagerAdapter extends FragmentStatePagerAdapter {

    WKUBBSNotiFragment wkuBbsNotiFragment;
    WKUBBSRoomFragment wkuBbsRoomFragment;
    WKUBBSMarketFragment wkuBbsMarketFragment;
    WKUBBSJobFragment wkuBbsJobFragment;

    public WKUBBSPagerAdapter(android.support.v4.app.FragmentManager fm, WKUBBSNotiFragment wkuBbsNotiFragment, WKUBBSRoomFragment wkuBbsRoomFragment, WKUBBSMarketFragment wkuBbsMarketFragment, WKUBBSJobFragment wkuBbsJobFragment) {
        super(fm);

        this.wkuBbsNotiFragment = wkuBbsNotiFragment;
        this.wkuBbsRoomFragment = wkuBbsRoomFragment;
        this.wkuBbsMarketFragment = wkuBbsMarketFragment;
        this.wkuBbsJobFragment = wkuBbsJobFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wkuBbsNotiFragment;
            case 1:
                return wkuBbsRoomFragment;
            case 2:
                return wkuBbsMarketFragment;
            case 3:
                return wkuBbsJobFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

class BBSAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<WKUBBSData> arrayList;
    private int layout;

    public BBSAdapter(Context context, int layout, ArrayList<WKUBBSData> arrayList) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arrayList.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        TextView type = convertView.findViewById(R.id.bbs_type);
        TextView title = convertView.findViewById(R.id.bbs_title);
        TextView author = convertView.findViewById(R.id.bbs_author);
        TextView hits = convertView.findViewById(R.id.bbs_hits);
        TextView date = convertView.findViewById(R.id.bbs_date);

        type.setText(arrayList.get(position).getType());
        title.setText(arrayList.get(position).getTitle());
        author.setText(arrayList.get(position).getAuthor());
        hits.setText(arrayList.get(position).getHits() + "");
        date.setText(arrayList.get(position).getDate() + "");

        return convertView;
    }
}

