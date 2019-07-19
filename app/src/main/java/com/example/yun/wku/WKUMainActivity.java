package com.example.yun.wku;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;

public class WKUMainActivity extends AppCompatActivity implements View.OnClickListener{
    BroadcastReceiver bbsNotiReceiver;
    BroadcastReceiver bbsRoomReceiver;
    BroadcastReceiver bbsMarketReceiver;
    BroadcastReceiver bbsJobReceiver;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    private LinearLayout schecduleBtn;
    private LinearLayout mainBtn;
    private LinearLayout notiBtn;
    private LinearLayout privateMenu;
    private LinearLayout bookmarkMenu;

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

    private ImageView scheduleImg;
    private ImageView mainImg;
    private ImageView notiImg;

    private ImageView privateImage;

    private TextView privateName;
    private TextView privateDept;
    private TextView privateMajor;
    private TextView privateStudentNo;
    private TextView privateGrade;
    private TextView privateTel;
    private TextView privateAddr;
    private ImageView logoutBtn;

    private ViewPager pager;

    private WKUDatabase wkuDatabase;

    private DrawerLayout drawer;
    private LinearLayout leftDrawer;
    private LinearLayout rightDrawer;

    private String isDorm = "";

    private WKUMainFragment wkuMainFragment;
    private WKUScheduleFragment wkuScheduleFragment;
    private WKUAlarmFragment wkuAlarmFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkumain);

        init();

        startService();
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            ActivityCompat.finishAffinity(this);
        }

        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.private_menu :
                drawer.openDrawer(leftDrawer);
                break;
            case R.id.bookmark_menu :
                drawer.openDrawer(rightDrawer);
                break;

            case R.id.schedule_btn :
                pager.setCurrentItem(0);
                changeIconBySelectSchedule();

                if(!isAccessSchedule())
                    Toast.makeText(this, "조회기간이 아닙니다", Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_btn :
                pager.setCurrentItem(1);
                changeIconBySelectMain();
                break;

            case R.id.noti_btn :
                pager.setCurrentItem(2);
                changeIconBySelectNoti();
                break;

            case R.id.bookmark_attend_menu :
                drawer.closeDrawer(rightDrawer);
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                drawer.closeDrawer(rightDrawer);
                pager.setCurrentItem(0);
                break;

            case R.id.bookmark_scholar_menu :
                drawer.closeDrawer(rightDrawer);
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                drawer.closeDrawer(rightDrawer);
                Intent gradeIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(gradeIntent);
                break;

            case R.id.bookmark_dorm_menu :
                drawer.closeDrawer(rightDrawer);
                if(isDorm.equals("YES")) {
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                drawer.closeDrawer(rightDrawer);
                Toast.makeText(WKUMainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bookmark_setting_menu :
                drawer.closeDrawer(rightDrawer);
                Intent settingIntent = new Intent(this, WKUSettingActivity.class);
                startActivity(settingIntent);
                break;

            case R.id.bookmark_complain_menu :
                drawer.closeDrawer(rightDrawer);
                Toast.makeText(WKUMainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout_btn :
//                Intent stopServiceIntent = new Intent(this, WKUService.class);
//                stopService(stopServiceIntent);
                Intent loginIntent = new Intent(this, WKULoginActivity.class);
                loginIntent.putExtra("source", "logout");
                startActivity(loginIntent);

                finish();
                break;
        }
    }

    private void init() {
        wkuMainFragment = new WKUMainFragment();
        wkuScheduleFragment = new WKUScheduleFragment();
        wkuAlarmFragment = new WKUAlarmFragment();

        wkuDatabase = new WKUDatabase(this);

        initFindViewById();
        initSetOnClickListener();
        initImageResource();
        initPager();

        initPrivateDataFromSQLite();
        initIcon();
    }

    private void initPager() {
        pager = findViewById(R.id.content_container);
        pager.setAdapter(new WKUMainPagerAdapter(getSupportFragmentManager(), this.wkuMainFragment, this.wkuScheduleFragment, this.wkuAlarmFragment, wkuDatabase));

        pager.setCurrentItem(1);
        changeIconBySelectMain();

        pager.setOffscreenPageLimit(1);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0 :
                        changeIconBySelectSchedule();

                        if(!isAccessSchedule()) {
                            Toast.makeText(getApplicationContext(), "조회기간이 아닙니다", Toast.LENGTH_SHORT).show();
                        } else {
                            wkuScheduleFragment.getScheduleData();
                            wkuScheduleFragment.syncScheduleToDisplay();
                        }
                        break;

                    case 1 :
                        changeIconBySelectMain();
                        break;

                    case 2 :
                        changeIconBySelectNoti();
                        wkuAlarmFragment.getAlarmData();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(getIntent().getExtras() != null) {
//            if(getIntent().getExtras().getString("curPage").equals("schedule")) {
//
//                pager.setCurrentItem(0);
//                changeIconBySelectSchedule();
//
//            } else if(getIntent().getExtras().getString("curPage").equals("alarm")) {
//
//                pager.setCurrentItem(2);
//                changeIconBySelectNoti();
//            } else {
//                pager.setCurrentItem(1);
//                changeIconBySelectMain();
//            }

            if(getIntent().getExtras().getString("curPage").equals("schedule")) {
                pager.setCurrentItem(0);
                changeIconBySelectSchedule();

            } else {
                pager.setCurrentItem(1);
                changeIconBySelectMain();
            }
        }
    }

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

    private void initFindViewById() {
        drawer = findViewById(R.id.drawer);
        leftDrawer = findViewById(R.id.leftDrawer);
        rightDrawer = findViewById(R.id.rightDrawer);

        schecduleBtn = findViewById(R.id.schedule_btn);
        mainBtn = findViewById(R.id.main_btn);
        notiBtn = findViewById(R.id.noti_btn);

        privateMenu = findViewById(R.id.private_menu);
        bookmarkMenu = findViewById(R.id.bookmark_menu);

        bookmarkAttendMenu = findViewById(R.id.bookmark_attend_menu);
        bookmarkScheduleMenu = findViewById(R.id.bookmark_schedule_menu);
        bookmarkScholarMenu = findViewById(R.id.bookmark_scholar_menu);
        bookmarkGradeMenu = findViewById(R.id.bookmark_grade_menu);
        bookmarkDormMenu = findViewById(R.id.bookmark_dorm_menu);
        bookmarkMenstMenu = findViewById(R.id.bookmark_menst_menu);
        bookmarkSettingMenu = findViewById(R.id.bookmark_setting_menu);
        bookmarkComplainMenu = findViewById(R.id.bookmark_complain_menu);

        privateImage = findViewById(R.id.private_image);

        privateName = findViewById(R.id.private_name);
        privateDept = findViewById(R.id.private_department);
        privateMajor = findViewById(R.id.private_major);
        privateStudentNo = findViewById(R.id.private_studentNo);
        privateGrade = findViewById(R.id.private_grade);
        privateTel = findViewById(R.id.private_tel);
        privateAddr = findViewById(R.id.private_address);
        logoutBtn = findViewById(R.id.logout_btn);

        bookmarkAttendImageView = findViewById(R.id.bookmark_attend_imageview);
        bookmarkScheduleImageView = findViewById(R.id.bookmark_schedule_imageview);
        bookmarkScholarImageView = findViewById(R.id.bookmark_scholar_imageview);
        bookmarkGradeImageView = findViewById(R.id.bookmark_grade_imageview);
        bookmarkDormImageView = findViewById(R.id.bookmark_dorm_imageview);
        bookmarkMenstImageView = findViewById(R.id.bookmark_menst_imageview);
        bookmarkSettingImageView = findViewById(R.id.bookmark_setting_imageview);
        bookmarkComplainImageView = findViewById(R.id.bookmark_complain_imageview);

        scheduleImg = findViewById(R.id.schedule_img);
        mainImg = findViewById(R.id.main_img);
        notiImg = findViewById(R.id.noti_img);
    }

    private void initSetOnClickListener() {
        schecduleBtn.setOnClickListener(this);
        mainBtn.setOnClickListener(this);
        notiBtn.setOnClickListener(this);

        privateMenu.setOnClickListener(this);
        bookmarkMenu.setOnClickListener(this);

        bookmarkAttendMenu.setOnClickListener(this);
        bookmarkScheduleMenu.setOnClickListener(this);
        bookmarkScholarMenu.setOnClickListener(this);
        bookmarkGradeMenu.setOnClickListener(this);
        bookmarkDormMenu.setOnClickListener(this);
        bookmarkMenstMenu.setOnClickListener(this);
        bookmarkSettingMenu.setOnClickListener(this);
        bookmarkComplainMenu.setOnClickListener(this);

        logoutBtn.setOnClickListener(this);
    }

    private void initPrivateDataFromSQLite() {
        try {
            int isStartService;

            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT * FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
//            Log.i("WKU", "WKUMainActivity - 개인정보 번호 : " + cursor.getString(0) + " 이름 : " + cursor.getString(1) + " 대학계열 : " + cursor.getString(2) + " 전공 : " + cursor.getString(3) + " 학년 : " + cursor.getString(4) + " 전화번호 : " + cursor.getString(5) + " 주소 : " + cursor.getString(6) + " 장학금 개수 " + cursor.getString(7) + " 학기 당 성적 개수 : " + cursor.getString(8) + " 과목 개수 : " + cursor.getString(9) + " 출결 과목 개수 : " + cursor.getString(10));
                privateName.setText(wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(1), wkuDatabase.getWkuRsaSecurity().getPrivateKey()));
                privateDept.setText(wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(2), wkuDatabase.getWkuRsaSecurity().getPrivateKey()));
                privateMajor.setText(wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(3), wkuDatabase.getWkuRsaSecurity().getPrivateKey()));
                privateStudentNo.setText(wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(0), wkuDatabase.getWkuRsaSecurity().getPrivateKey()));
                privateGrade.setText(wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(4), wkuDatabase.getWkuRsaSecurity().getPrivateKey()));
                privateTel.setText(wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(5), wkuDatabase.getWkuRsaSecurity().getPrivateKey()));
                privateAddr.setText(wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(6), wkuDatabase.getWkuRsaSecurity().getPrivateKey()));
                isDorm = cursor.getString(7);

//            isStartService = cursor.getInt(17);

//            Log.i("WKU", "WKUMainActivity -> initPrivateDataFromSQLite() -> isStartService : " + isStartService);
            }

            FileInputStream fis = openFileInput("wkuprivateimage.png");
            privateImage.setImageBitmap(BitmapFactory.decodeStream(fis));
        } catch (Exception e) {
        }
    }

    private void initIcon() {
        setNoSelectedScheduleIcon();
        setSelectedMainIcon();
        setNoSelectedNotiIcon();
    }

    public void changeIconBySelectSchedule() {
        setSelectedScheduleIcon();
        setNoSelectedMainIcon();
        setNoSelectedNotiIcon();
    }

    public void changeIconBySelectMain() {
        setNoSelectedScheduleIcon();
        setSelectedMainIcon();
        setNoSelectedNotiIcon();
    }

    public void changeIconBySelectNoti() {
        setNoSelectedScheduleIcon();
        setNoSelectedMainIcon();
        setSelectedNotiIcon();
    }

    private void setSelectedScheduleIcon() {
        scheduleImg.setImageResource(R.drawable.tailer_schedule_selected);

        android.view.ViewGroup.LayoutParams layoutParams = scheduleImg.getLayoutParams();
        layoutParams.width = 270;
        layoutParams.height = 270;
        scheduleImg.setPadding(0, 0, 0, 0);
        scheduleImg.setLayoutParams(layoutParams);
    }

    private void setNoSelectedScheduleIcon() {
        scheduleImg.setImageResource(R.drawable.tailer_schedule_no_selected);

        android.view.ViewGroup.LayoutParams layoutParams = scheduleImg.getLayoutParams();
        layoutParams.width = 130;
        layoutParams.height = 130;
        scheduleImg.setPadding(0, 0, 0, 0);
        scheduleImg.setLayoutParams(layoutParams);
    }

    private void setSelectedMainIcon() {
        mainImg.setImageResource(R.drawable.tailer_main_selected);

        android.view.ViewGroup.LayoutParams layoutParams = mainImg.getLayoutParams();
        layoutParams.width = 270;
        layoutParams.height = 270;
        scheduleImg.setPadding(0, 0, 0, 0);
        mainImg.setLayoutParams(layoutParams);
    }

    private void setNoSelectedMainIcon() {
        mainImg.setImageResource(R.drawable.tailer_main_no_selected);

        android.view.ViewGroup.LayoutParams layoutParams = mainImg.getLayoutParams();
        layoutParams.width = 100;
        layoutParams.height = 100;
        scheduleImg.setPadding(0, 0, 0, 0);
        mainImg.setLayoutParams(layoutParams);
    }

    private void setSelectedNotiIcon() {
        notiImg.setImageResource(R.drawable.tailer_noti_selected);

        android.view.ViewGroup.LayoutParams layoutParams = notiImg.getLayoutParams();
        layoutParams.width = 270;
        layoutParams.height = 270;
        scheduleImg.setPadding(0, 0, 0, 0);
        notiImg.setLayoutParams(layoutParams);
    }

    private void setNoSelectedNotiIcon() {
        notiImg.setImageResource(R.drawable.tailer_noti_no_selected);

        android.view.ViewGroup.LayoutParams layoutParams = notiImg.getLayoutParams();
        layoutParams.width = 100;
        layoutParams.height = 100;
        scheduleImg.setPadding(0, 0, 0, 0);
        notiImg.setLayoutParams(layoutParams);
    }

    private boolean isAccessSchedule() {
        boolean IS_EXIST_SCHEDULE_FLAG = false;

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT subject FROM wkuScheduleData", null);

        while(cursor.moveToNext()) {
            IS_EXIST_SCHEDULE_FLAG = true;

            break;
        }

        return IS_EXIST_SCHEDULE_FLAG;
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

    private void startService() {

//        if(!isServiceRunning(WKUAlarmEClassService.class)) {
//            Log.i("WKU", "WKUMainActivity.onCreate() / WKUAlarmEClassService START...");
//
//            Intent intent = new Intent(this, WKUAlarmEClassService.class);
//            startService(intent);
//        } else {
//            Log.i("WKU", "WKUMainActivity.onCreate() / WKUAlarmEClassService IS ALREADY STARTED...");
//        }

        if(!isServiceRunning(WKUAlarmBBSNotiService.class)) {

            Intent intent = new Intent(this, WKUAlarmBBSNotiService.class);
            bbsNotiReceiver = new WKUAlarmBBSNotiImmortalService();

            try {
                IntentFilter mainFilter = new IntentFilter("com.thirty.yun.wku");
                registerReceiver(bbsNotiReceiver, mainFilter);
                startService(intent);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        if(!isServiceRunning(WKUAlarmBBSRoomService.class)) {

            Intent intent = new Intent(this, WKUAlarmBBSRoomService.class);
            bbsRoomReceiver = new WKUAlarmBBSRoomImmortalService();

            try {
                IntentFilter mainFilter = new IntentFilter("com.thirty.yun.wku");
                registerReceiver(bbsRoomReceiver, mainFilter);
                startService(intent);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        if(!isServiceRunning(WKUAlarmBBSMarketService.class)) {

            Intent intent = new Intent(this, WKUAlarmBBSMarketService.class);
            bbsMarketReceiver = new WKUAlarmBBSMarketImmortalService();

            try {
                IntentFilter mainFilter = new IntentFilter("com.thirty.yun.wku");
                registerReceiver(bbsMarketReceiver, mainFilter);
                startService(intent);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        if(!isServiceRunning(WKUAlarmBBSJobService.class)) {

            Intent intent = new Intent(this, WKUAlarmBBSJobService.class);
            bbsJobReceiver = new WKUAlarmBBSJobImmortalService();

            try {
                IntentFilter mainFilter = new IntentFilter("com.thirty.yun.wku");
                registerReceiver(bbsJobReceiver, mainFilter);
                startService(intent);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(bbsNotiReceiver);
            unregisterReceiver(bbsJobReceiver);
            unregisterReceiver(bbsRoomReceiver);
            unregisterReceiver(bbsMarketReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }
}

class WKUMainPagerAdapter extends FragmentStatePagerAdapter {
    private WKUMainFragment wkuMainFragment;
    private WKUScheduleFragment wkuScheduleFragment;
    private WKUAlarmFragment wkuAlarmFragment;

    private WKUDatabase wkuDatabase;

    public WKUMainPagerAdapter(android.support.v4.app.FragmentManager fm, WKUMainFragment wkuMainFragment, WKUScheduleFragment wkuScheduleFragment, WKUAlarmFragment wkuAlarmFragment, WKUDatabase wkuDatabase) {
        super(fm);

        this.wkuMainFragment = wkuMainFragment;
        this.wkuScheduleFragment = wkuScheduleFragment;
        this.wkuAlarmFragment = wkuAlarmFragment;

        this.wkuDatabase = wkuDatabase;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wkuScheduleFragment;
            case 1:
                return wkuMainFragment;
            case 2:
                return wkuAlarmFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        if(object instanceof WKUAlarmFragment) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }



    @Override
    public int getCount() {
        return 3;
    }
}