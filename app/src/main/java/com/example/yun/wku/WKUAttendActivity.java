package com.example.yun.wku;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WKUAttendActivity extends AppCompatActivity implements View.OnClickListener {
    private final int SENSITIVE_TRANSTIVE_ANIMATION = 10;
    private final int ANIMATION_MAX_SIZE = 600;

    private final int SENSITIVE_ANIMATION_ALPHA = 4;
    private int ANIMATION_ALPHA = 0xff;

    private int prevY;
    private int pastPrevY;
    private int pastPastPrevY;

    private boolean ANIMATION_FIRST_FLAG = true;
    private boolean ANIMATION_SECOND_FLAG = true;

    private YunAnimationController yunAnimationController;

    private LinearLayout attendLayout;

    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;

    private ImageView wkuLogoImg;
    private ProgressBar loadingBar;

    private ListView attendList;
    private ArrayList<WKUAttendData> arrayAttendList;
    private AttendAdapter attendAdapter;

    private ListView attendDetailList;
    private ArrayList<WKUAttendDetailData> arrayAttendDetailList;
    private AttendDetailAdapter attendDetailAdapter;

    private WKUDatabase wkuDatabase;
    private WKUScrapingEngine wkuScrapingEngine;

    private DrawerLayout attendDrawer;
    private LinearLayout attendRightDrawer;

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

    private String isDorm = "";
    private String wkuId, wkuPw;

    private String jSessionId = "";
    private String wkuTokenKey = "";

    private int studentNo;

    private TextView noAttend;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {
                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.ATTEND_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();

            } else if(msg.what == WKUDBThread.DB_FINISH_FLAG) {
            loadingBar.setVisibility(View.GONE);
            wkuLogoImg.setVisibility(View.VISIBLE);

            getAttendData();
        }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkuattend);

        attendLayout = findViewById(R.id.attend_layout);

        prevBtn = findViewById(R.id.attend_prev_btn);
        wkuLogoBtn = findViewById(R.id.attend_wkulogo_btn);
        bookmarkBtn = findViewById(R.id.attend_bookmark_btn);

        wkuLogoImg = findViewById(R.id.attend_wkulogo_img);
        loadingBar = findViewById(R.id.attend_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

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

        attendDrawer = findViewById(R.id.attend_drawer);
        attendRightDrawer = findViewById(R.id.attend_rightDrawer);

        noAttend = findViewById(R.id.no_attend);

        prevBtn.setOnClickListener(this);
        wkuLogoBtn.setOnClickListener(this);
        bookmarkBtn.setOnClickListener(this);

        bookmarkAttendMenu.setOnClickListener(this);
        bookmarkScheduleMenu.setOnClickListener(this);
        bookmarkScholarMenu.setOnClickListener(this);
        bookmarkGradeMenu.setOnClickListener(this);
        bookmarkDormMenu.setOnClickListener(this);
        bookmarkMenstMenu.setOnClickListener(this);
        bookmarkSettingMenu.setOnClickListener(this);
        bookmarkComplainMenu.setOnClickListener(this);

        initImageResource();
        initLists();

        wkuDatabase = new WKUDatabase(this);

        yunAnimationController = new YunAnimationController(attendDetailList);

        yunAnimationController.addAnimation(
                new YunScaleAnimation(attendLayout, 0, ANIMATION_MAX_SIZE, 0, 0),
                new YunScaleAnimation(attendLayout, 0, 0, 0, ANIMATION_MAX_SIZE),
                null,
                null
        );
        yunAnimationController.start();

        getAttendData();

        attendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(getAttendDetailData(position)) {
                    attendDetailList.setVisibility(View.VISIBLE);
                    noAttend.setVisibility(View.GONE);
                } else {
                    attendDetailList.setVisibility(View.GONE);
                    noAttend.setVisibility(View.VISIBLE);
                }
            }
        });

        getScrapingData();

        loadingBar.setVisibility(View.VISIBLE);
        wkuLogoImg.setVisibility(View.GONE);

        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_ATTEND_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, studentNo);
        wkuScrapingEngine.start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.attend_prev_btn :
                finish();
                break;

            case R.id.attend_wkulogo_btn :
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;

            case R.id.attend_bookmark_btn :
                attendDrawer.openDrawer(attendRightDrawer);
                break;

            case R.id.bookmark_attend_menu :
                attendDrawer.closeDrawer(attendRightDrawer);
                break;

            case R.id.bookmark_schedule_menu :
                attendDrawer.closeDrawer(attendRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                attendDrawer.closeDrawer(attendRightDrawer);
                finish();
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                attendDrawer.closeDrawer(attendRightDrawer);
                finish();
                Intent scoreIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(scoreIntent);
                break;

            case R.id.bookmark_dorm_menu :
                attendDrawer.closeDrawer(attendRightDrawer);
                if(isDorm.equals("YES")) {
                    finish();
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                attendDrawer.closeDrawer(attendRightDrawer);
                Toast.makeText(WKUAttendActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                attendDrawer.closeDrawer(attendRightDrawer);
                Toast.makeText(WKUAttendActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_complain_menu :
                attendDrawer.closeDrawer(attendRightDrawer);
                Toast.makeText(WKUAttendActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initLists() {
        attendList = findViewById(R.id.attend_list);
        attendDetailList = findViewById(R.id.attend_detail_list);

        arrayAttendList = new ArrayList<>();
        arrayAttendDetailList = new ArrayList<>();

        attendAdapter = new AttendAdapter(this, R.layout.list_wkuattend, arrayAttendList);
        attendList.setAdapter(attendAdapter);

        attendDetailAdapter = new AttendDetailAdapter(this, R.layout.list_wkuattenddetail, arrayAttendDetailList);
        attendDetailList.setAdapter(attendDetailAdapter);
    }

    private void getScrapingData() {
        try {
            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT wkuId, wkuPw, jSessionId, wkuTokenKey, studentNo, isDorm FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
                this.wkuId = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(0), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.wkuPw = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(1), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.jSessionId = cursor.getString(2);
                this.wkuTokenKey = cursor.getString(3);
                this.studentNo = Integer.parseInt(wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(4), wkuDatabase.getWkuRsaSecurity().getPrivateKey()));
                this.isDorm = cursor.getString(5);

                Log.i("WKU", "WKUAttendActivity.getScrapingData / studentNo : " + this.studentNo);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getAttendData() {
        arrayAttendList.clear();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT subject, professor FROM wkuAttendData", null);

        WKUAttendData attend;

        while(cursor.moveToNext()) {
            attend = new WKUAttendData(cursor.getString(0), cursor.getString(1));
            arrayAttendList.add(attend);
        }

        attendAdapter.notifyDataSetChanged();
    }

    private boolean getAttendDetailData(int attendIndex) {
        arrayAttendDetailList.clear();
        boolean isExist = false;

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT week, date, period, attend, attend_id FROM wkuAttendDetailData", null);

        WKUAttendDetailData attendDetail;

        while(cursor.moveToNext()) {

            if(cursor.getString(4).equals(attendIndex + "")) {
                isExist = true;

                attendDetail = new WKUAttendDetailData(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3));

                arrayAttendDetailList.add(attendDetail);
            }
        }

        attendDetailAdapter.notifyDataSetChanged();

        return isExist;
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

class AttendAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<WKUAttendData> arrayList;
    private int layout;

    public AttendAdapter(Context context, int layout, ArrayList<WKUAttendData> arrayList) {
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
        return this.arrayList.get(position).getSubject();
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

        TextView subject = convertView.findViewById(R.id.attend_subject);
        TextView professor = convertView.findViewById(R.id.attend_professor);

        subject.setText(arrayList.get(position).getSubject());
        professor.setText(arrayList.get(position).getProfessor());

        return convertView;
    }
}

class AttendDetailAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<WKUAttendDetailData> arrayList;
    private int layout;

    public AttendDetailAdapter(Context context, int layout, ArrayList<WKUAttendDetailData> arrayList) {
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
        return this.arrayList.get(position).getAttend();
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

        TextView week = convertView.findViewById(R.id.attend_detail_week);
        TextView date = convertView.findViewById(R.id.attend_detail_date);
        TextView period = convertView.findViewById(R.id.attend_detail_period);
        TextView attend = convertView.findViewById(R.id.attend_detail_attend);


        week.setText(arrayList.get(position).getWeek() + "주차");
        date.setText(arrayList.get(position).getDate());
        period.setText(arrayList.get(position).getPeriod() + "교시");
        attend.setText(arrayList.get(position).getAttend());

        if(arrayList.get(position).getAttend().equals("출석")) {
            attend.setTextColor(Color.BLACK);
        } else if(arrayList.get(position).getAttend().equals("결석")) {
            attend.setTextColor(Color.RED);
        } else if(arrayList.get(position).getAttend().equals("지각")) {
            attend.setTextColor(Color.BLUE);
        }

        return convertView;
    }
}
