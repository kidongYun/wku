package com.example.yun.wku;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.charts.LineChart;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WKUGradeActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;
    private LinearLayout gradeCurBtn;

    private ImageView wkuLogoImg;
    private ProgressBar loadingBar;

    private LinearLayout gradeContentLayout;

    private LinearLayout gradeHeaderLayout;
    private LinearLayout gradeBorderLayout;
    private LinearLayout gradeDetailHeaderLayout;
    private LinearLayout gradeDetailBorderLayout;
    private LinearLayout gradeCurHeaderLayout;
    private LinearLayout gradeCurBorderLayout;

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

    private ImageView gradeGraduationBtn;

    private TextView totalCredit;
    private TextView majorCredit;
    private TextView generalCredit;
    private TextView totalGrade;

    private ListView gradeList;
    private ArrayList<WKUGradeData> arrayGradeList;
    private GradeAdapter gradeAdapter;

    private ListView gradeDetailList;
    private ArrayList<WKUGradeDetailData> arrayGradeDetailList;
    private GradeDetailAdapter gradeDetailAdapter;

    private ListView gradeCurList;
    private ArrayList<WKUGradeCurData> arrayGradeCurList;
    private GradeCurAdapter gradeCurAdapter;

    private LineChart gradeChart;
    private ArrayList<Entry> gradeEntries;
    private ArrayList<String> gradeLabels;

    private LineChart gradeDetailChart;
    private ArrayList<Entry> gradeDetailEntries;
    private ArrayList<String> gradeDetailLabels;

    private LineChart gradeCurChart;
    private ArrayList<Entry> gradeCurEntries;
    private ArrayList<String> gradeCurLabels;

    private DrawerLayout gradeDrawer;
    private LinearLayout gradeRightDrawer;

    private WKUDatabase wkuDatabase;
    private WKUScrapingEngine wkuScrapingEngine;

    private boolean GRADE_FLAG = true;
    private boolean GRADE_CUR_BTN_FLAG = false;

    private String isDorm = "";
    private String major, studentNo = "";
    private String wkuId, wkuPw;
    private String graduationType = "";

    private String jSessionId = "";
    private String wkuTokenKey = "";

    private int gradeCurYear;
    private int gradeCurTerm;

    private WKUGraduation wkuGraduation;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {
                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.GRADE_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();

            } else if(msg.what == WKUDBThread.DB_FINISH_FLAG) {
                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);

                if(!isExistGraduationDB()) {
                    initGraduationDatabse();
                }

                initGradeCurBtn();

                getGradeData();
                getTotalGradeData();
                getGraduationData();

                wkuGraduation.setCurCredit();

                setGraduationInfo(totalCredit, majorCredit, generalCredit, totalGrade);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkugrade);

        prevBtn = findViewById(R.id.grade_prev_btn);
        wkuLogoBtn = findViewById(R.id.grade_wkulogo_btn);
        bookmarkBtn = findViewById(R.id.grade_bookmark_btn);
        gradeCurBtn = findViewById(R.id.grade_cur_btn);

        wkuLogoImg = findViewById(R.id.grade_wkulogo_img);
        loadingBar = findViewById(R.id.grade_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        gradeContentLayout = findViewById(R.id.grade_content_layout);

        gradeHeaderLayout = findViewById(R.id.grade_header_layout);
        gradeBorderLayout = findViewById(R.id.grade_border_layout);
        gradeDetailHeaderLayout = findViewById(R.id.grade_detail_header_layout);
        gradeDetailBorderLayout = findViewById(R.id.grade_detail_border_layout);
        gradeCurHeaderLayout = findViewById(R.id.grade_cur_header_layout);
        gradeCurBorderLayout = findViewById(R.id.grade_cur_border_layout);

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

        gradeGraduationBtn = findViewById(R.id.grade_graduation_btn);

        initImageResource();

        totalCredit = findViewById(R.id.grade_total_credit);
        majorCredit = findViewById(R.id.grade_major_credit);
        generalCredit = findViewById(R.id.grade_liberal_credit);
        totalGrade = findViewById(R.id.grade_total_grade);

        gradeDrawer = findViewById(R.id.grade_drawer);
        gradeRightDrawer = findViewById(R.id.grade_rightDrawer);

        prevBtn.setOnClickListener(this);
        wkuLogoBtn.setOnClickListener(this);
        bookmarkBtn.setOnClickListener(this);
        gradeCurBtn.setOnClickListener(this);

        gradeGraduationBtn.setOnClickListener(this);

        bookmarkAttendMenu.setOnClickListener(this);
        bookmarkScheduleMenu.setOnClickListener(this);
        bookmarkScholarMenu.setOnClickListener(this);
        bookmarkGradeMenu.setOnClickListener(this);
        bookmarkDormMenu.setOnClickListener(this);
        bookmarkMenstMenu.setOnClickListener(this);
        bookmarkSettingMenu.setOnClickListener(this);
        bookmarkComplainMenu.setOnClickListener(this);

        wkuDatabase = new WKUDatabase(this);

        initLists();
        initCharts();
        initGradeCurBtn();
        getGradeCurData();

        getScrapingData();

        wkuGraduation = new WKUGraduation(this, major, Integer.parseInt(studentNo.substring(0, 4)));

        getGradeData();

        getGraduationData();
        getTotalGradeData();
        setGraduationInfo(totalCredit, majorCredit, generalCredit, totalGrade);

        loadingBar.setVisibility(View.VISIBLE);
        wkuLogoImg.setVisibility(View.GONE);

        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_GRADE_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey);
        wkuScrapingEngine.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grade_cur_btn :
                gradeChart.setVisibility(View.GONE);
                gradeHeaderLayout.setVisibility(View.GONE);
                gradeBorderLayout.setVisibility(View.GONE);
                gradeList.setVisibility(View.GONE);

                gradeDetailChart.setVisibility(View.GONE);
                gradeDetailHeaderLayout.setVisibility(View.GONE);
                gradeDetailBorderLayout.setVisibility(View.GONE);
                gradeDetailList.setVisibility(View.GONE);

                gradeCurChart.setVisibility(View.VISIBLE);
                gradeCurHeaderLayout.setVisibility(View.VISIBLE);
                gradeCurBorderLayout.setVisibility(View.VISIBLE);
                gradeCurList.setVisibility(View.VISIBLE);

                if(GRADE_CUR_BTN_FLAG) {
                    gradeCurBtn.setVisibility(View.GONE);
                }

                getGradeCurData();

                GRADE_FLAG = false;
                break;
            case R.id.grade_prev_btn :
                if(GRADE_FLAG) {
                    finish();
                } else {
                    gradeChart.setVisibility(View.VISIBLE);
                    gradeHeaderLayout.setVisibility(View.VISIBLE);
                    gradeBorderLayout.setVisibility(View.VISIBLE);
                    gradeList.setVisibility(View.VISIBLE);

                    gradeDetailChart.setVisibility(View.GONE);
                    gradeDetailHeaderLayout.setVisibility(View.GONE);
                    gradeDetailBorderLayout.setVisibility(View.GONE);
                    gradeDetailList.setVisibility(View.GONE);

                    gradeCurChart.setVisibility(View.GONE);
                    gradeCurHeaderLayout.setVisibility(View.GONE);
                    gradeCurBorderLayout.setVisibility(View.GONE);
                    gradeCurList.setVisibility(View.GONE);

                    if(GRADE_CUR_BTN_FLAG) {
                        gradeCurBtn.setVisibility(View.VISIBLE);
                    }

                    GRADE_FLAG = true;
                }
                break;

            case R.id.grade_wkulogo_btn:
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;

            case R.id.grade_bookmark_btn :
                gradeDrawer.openDrawer(gradeRightDrawer);
                break;

            case R.id.grade_graduation_btn :
                if(wkuGraduation.getWkuGraduationData().getGraduationType() == -1) {
                    Toast.makeText(WKUGradeActivity.this, "준비중입니다",  Toast.LENGTH_SHORT).show();
                } else {
                    Intent graduationIntent = new Intent(this, WKUGraduationActivity.class);
                    startActivity(graduationIntent);
                }
                break;

            case R.id.bookmark_attend_menu :
                gradeDrawer.closeDrawer(gradeRightDrawer);
                finish();
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                gradeDrawer.closeDrawer(gradeRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                gradeDrawer.closeDrawer(gradeRightDrawer);
                finish();
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                gradeDrawer.closeDrawer(gradeRightDrawer);
                break;

            case R.id.bookmark_dorm_menu :
                gradeDrawer.closeDrawer(gradeRightDrawer);
                if(isDorm.equals("YES")) {
                    finish();
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                gradeDrawer.closeDrawer(gradeRightDrawer);
                Toast.makeText(WKUGradeActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                gradeDrawer.closeDrawer(gradeRightDrawer);
                Toast.makeText(WKUGradeActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_complain_menu :
                gradeDrawer.closeDrawer(gradeRightDrawer);
                Toast.makeText(WKUGradeActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initLists() {
        gradeList = findViewById(R.id.grade_list);
        gradeDetailList = findViewById(R.id.grade_detail_list);
        gradeCurList = findViewById(R.id.grade_cur_list);

        arrayGradeList = new ArrayList<>();
        arrayGradeDetailList = new ArrayList<>();
        arrayGradeCurList = new ArrayList<>();

        gradeAdapter = new GradeAdapter(this, R.layout.list_wkugrade, arrayGradeList);
        gradeDetailAdapter = new GradeDetailAdapter(this, R.layout.list_wkugradedetail, arrayGradeDetailList);
        gradeCurAdapter = new GradeCurAdapter(this, R.layout.list_wkugradecur, arrayGradeCurList);

        gradeList.setAdapter(gradeAdapter);
        gradeDetailList.setAdapter(gradeDetailAdapter);
        gradeCurList.setAdapter(gradeCurAdapter);

        gradeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getGradeDetailData(position);

                LineDataSet gradeDetailDataSet = new LineDataSet(gradeDetailEntries, "학점");
                LineData gradeDetailData = new LineData(gradeDetailLabels, gradeDetailDataSet);

                gradeDetailDataSet.setCircleSize(5.0f);
                gradeDetailDataSet.setValueTextSize(10.0f);
                gradeDetailDataSet.setDrawFilled(true);
                gradeDetailDataSet.setColor(Color.RED);
                gradeDetailDataSet.setFillColor(Color.RED);
                gradeDetailDataSet.setCircleColor(Color.RED);

                gradeDetailChart.setData(gradeDetailData);
                gradeDetailChart.setDescription("");
                gradeDetailChart.setDescriptionTextSize(11.0f);
                gradeDetailChart.setBackgroundColor(Color.TRANSPARENT);
                gradeDetailChart.setDrawGridBackground(false);
                gradeDetailChart.getAxisLeft().setDrawLabels(false);
                gradeDetailChart.getAxisRight().setDrawLabels(false);
                gradeDetailChart.animateY(1500);

                gradeChart.setVisibility(View.GONE);
                gradeHeaderLayout.setVisibility(View.GONE);
                gradeBorderLayout.setVisibility(View.GONE);
                gradeList.setVisibility(View.GONE);

                gradeDetailChart.setVisibility(View.VISIBLE);
                gradeDetailHeaderLayout.setVisibility(View.VISIBLE);
                gradeDetailBorderLayout.setVisibility(View.VISIBLE);
                gradeDetailList.setVisibility(View.VISIBLE);

                GRADE_FLAG = false;
            }
        });
    }

    private void initCharts() {
        gradeChart = findViewById(R.id.grade_chart);
        gradeEntries = new ArrayList<>();
        gradeLabels = new ArrayList<>();

        gradeDetailChart = findViewById(R.id.grade_detail_chart);
        gradeDetailEntries = new ArrayList<>();
        gradeDetailLabels = new ArrayList<>();

        gradeCurChart = findViewById(R.id.grade_cur_chart);
        gradeCurEntries = new ArrayList<>();
        gradeCurLabels = new ArrayList<>();
    }

    private void initGradeCurBtn() {
        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT subject, markGrade FROM wkuGradeCurData", null);

        while(cursor.moveToNext()) {
            if(!cursor.getString(1).equals("0.0")) {
                GRADE_CUR_BTN_FLAG = true;
            }
        }

        if(GRADE_CUR_BTN_FLAG) {
            gradeCurBtn.setVisibility(View.VISIBLE);
        } else {
            gradeCurBtn.setVisibility(View.GONE);
        }
    }

    private void initGraduationDatabse() {
        wkuGraduation.createTable();

        for(int i=0 ; i<wkuGraduation.getWkuGraduationDatas().size(); i++) {
            wkuDatabase.insertGraduationData(wkuGraduation.getWkuGraduationDatas().get(i));
        }
    }

    private void setCharts() {
        LineDataSet gradeDataSet = new LineDataSet(gradeEntries, "학기별 학점");
        LineData gradeData = new LineData(gradeLabels, gradeDataSet);

        LineDataSet gradeCurDataSet = new LineDataSet(gradeCurEntries, "이번학기 학점");
        LineData gradeCurData = new LineData(gradeCurLabels, gradeCurDataSet);

        gradeDataSet.setCircleSize(5.0f);
        gradeDataSet.setValueTextSize(10.0f);
        gradeDataSet.setDrawFilled(true);

        gradeChart.setData(gradeData);
        gradeChart.setDescription("");
        gradeChart.setDescriptionTextSize(11.0f);
        gradeChart.setBackgroundColor(Color.TRANSPARENT);
        gradeChart.setDrawGridBackground(false);
        gradeChart.getAxisLeft().setDrawLabels(false);
        gradeChart.getAxisRight().setDrawLabels(false);
        gradeChart.animateY(1500);

        gradeCurDataSet.setCircleSize(5.0f);
        gradeCurDataSet.setValueTextSize(10.0f);
        gradeCurDataSet.setDrawFilled(true);
        gradeCurDataSet.setColor(Color.GREEN);
        gradeCurDataSet.setFillColor(Color.GREEN);
        gradeCurDataSet.setCircleColor(Color.GREEN);

        gradeCurChart.setData(gradeCurData);
        gradeCurChart.setDescription("");
        gradeCurChart.setDescriptionTextSize(11.0f);
        gradeCurChart.setBackgroundColor(Color.TRANSPARENT);
        gradeCurChart.setDrawGridBackground(false);
        gradeCurChart.getAxisLeft().setDrawLabels(false);
        gradeCurChart.getAxisRight().setDrawLabels(false);
    }

    private void getScrapingData() {
        try {
            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT wkuId, wkuPw, jSessionId, wkuTokenKey, studentNo, isDorm, major, graduationType FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
                this.wkuId = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(0), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.wkuPw = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(1), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.jSessionId = cursor.getString(2);
                this.wkuTokenKey = cursor.getString(3);
                this.studentNo = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(4), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.isDorm = cursor.getString(5);
                this.major = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(6), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.graduationType = cursor.getString(7);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getGradeData() {
        arrayGradeList.clear();
        gradeEntries.clear();
        gradeLabels.clear();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT year, schoolYear, term, reqCredit, ackCredit, totalGrade, avgGrade FROM wkuGradeData", null);

        WKUGradeData grade;
        int i=0;

        while(cursor.moveToNext()) {
            grade = new WKUGradeData(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Float.parseFloat(cursor.getString(3)), Float.parseFloat(cursor.getString(4)), Float.parseFloat(cursor.getString(5)), Float.parseFloat(cursor.getString(6)));

            arrayGradeList.add(grade);
            gradeEntries.add(new Entry(Float.parseFloat(cursor.getString(6)), i++));
            gradeLabels.add(cursor.getString(1) + "-" + cursor.getString(2));
        }

        gradeAdapter.notifyDataSetChanged();
        setCharts();
    }

    private void getGradeDetailData(int position) {
        arrayGradeDetailList.clear();
        gradeDetailEntries.clear();
        gradeDetailLabels.clear();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT * FROM wkuGradeDetailData", null);

        WKUGradeDetailData gradeDetail;
        int i=0;

        while(cursor.moveToNext()) {
//            Log.i("WKU", "성적번호 : " + cursor.getString(0) + " 과목분류 : " + cursor.getString(1) + " 과목 : " + cursor.getString(2) + " 학점 : " + cursor.getString(3) + " 점수 : " + cursor.getString(4) + " 마크점수 : " + cursor.getString(5) + " 학기구분 : " + cursor.getString(6));
            if(Integer.parseInt(cursor.getString(7)) == position) {
                gradeDetail = new WKUGradeDetailData(cursor.getString(1), cursor.getString(2), cursor.getString(3), Float.parseFloat(cursor.getString(4)), Float.parseFloat(cursor.getString(5)), cursor.getString(6));

                arrayGradeDetailList.add(gradeDetail);
                gradeDetailEntries.add(new Entry(Float.parseFloat(cursor.getString(5)), i++));
                gradeDetailLabels.add(cursor.getString(3));
            }
        }

        gradeDetailAdapter.notifyDataSetChanged();
    }

    private void getGradeCurData() {
        arrayGradeCurList.clear();
        gradeCurEntries.clear();
        gradeCurLabels.clear();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT * FROM wkuGradeCurData", null);

        WKUGradeCurData curGrade;
        int i=0;


        while(cursor.moveToNext()) {
            curGrade = new WKUGradeCurData(cursor.getString(1), cursor.getString(2), Float.parseFloat(cursor.getString(3)), Float.parseFloat(cursor.getString(4)), Float.parseFloat(cursor.getString(5)), Float.parseFloat(cursor.getString(6)), Float.parseFloat(cursor.getString(7)), Float.parseFloat(cursor.getString(8)), cursor.getString(9));
            arrayGradeCurList.add(curGrade);

            gradeCurEntries.add(new Entry(Float.parseFloat(cursor.getString(8)), i++));
            gradeCurLabels.add(cursor.getString(2));
        }

        gradeCurAdapter.notifyDataSetChanged();
        setCharts();
    }

    private float getTotalGradeData() {
        float totalGrade = 0.0f;
        float credit = 0.0f;

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT totalGrade FROM wkuGradeData", null);

        while(cursor.moveToNext()) {
            totalGrade += Float.parseFloat(cursor.getString(0));
        }

        cursor = wkuDatabase.getDB().rawQuery("SELECT credit, markGrade FROM wkuGradeDetailData", null);

        while(cursor.moveToNext()) {
            if(!cursor.getString(1).equals("P")) {
                credit += cursor.getDouble(0);
            }
        }

        return totalGrade / credit;
    }

    private void getGraduationData() {

        Log.i("WKU", "WKUGradeActivity.getGraduationData / graudationType : " + graduationType);

        if(Integer.parseInt(graduationType) == -1) {
            Log.i("WKU", "WKUGradeActivity.getGraduationData / graduationType is UNKNOWN TYPE");

        } else {
            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT * FROM wkuGraduationData WHERE graduation_id = " + graduationType, null);

            while(cursor.moveToNext()) {
                this.wkuGraduation.getWkuGraduationData().set(Integer.parseInt(graduationType), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10), cursor.getInt(11), cursor.getInt(12), cursor.getInt(13), cursor.getInt(14));
            }
        }
    }

    private boolean isExistGraduationDB() {
        boolean flag = false;
        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT graduation_id FROM wkuGraduationData", null);

        while(cursor.moveToNext()) {
            flag = true;
        }

        if(flag) {
            return true;
        } else {
            return false;
        }
    }

    private void setGraduationInfo(TextView totalCredit, TextView majorCredit, TextView generalCredit, TextView totalGrade) {
        if(wkuGraduation.getWkuGraduationData().getGraduationType() != WKUGraduation.UNKOWN_TYPE) {
            totalCredit.setText(wkuGraduation.getWkuGraduationData().getCurGraduationCredit() + "/" + wkuGraduation.getWkuGraduationData().getMinGraduationCredit());
            majorCredit.setText(wkuGraduation.getWkuGraduationData().getCurMajorCredit() + "/" + wkuGraduation.getWkuGraduationData().getMinMajorCredit());
            generalCredit.setText(wkuGraduation.getWkuGraduationData().getCurGeneralCredit() + "/" + wkuGraduation.getWkuGraduationData().getMinGeneralCredit());
            totalGrade.setText(String.format("%.3g%n", getTotalGradeData()));

            if(wkuGraduation.getWkuGraduationData().getCurGraduationCredit() >= wkuGraduation.getWkuGraduationData().getMinGraduationCredit()) {
                totalCredit.setTextColor(Color.parseColor("#004386"));
            } else {
                totalCredit.setTextColor(Color.parseColor("#ff0000"));
            }

            if(wkuGraduation.getWkuGraduationData().getCurMajorCredit() >= wkuGraduation.getWkuGraduationData().getMinMajorCredit()) {
                majorCredit.setTextColor(Color.parseColor("#004386"));
            } else {
                majorCredit.setTextColor(Color.parseColor("#ff0000"));
            }

            if(wkuGraduation.getWkuGraduationData().getCurGeneralCredit() >= wkuGraduation.getWkuGraduationData().getMinGeneralCredit()) {
                generalCredit.setTextColor(Color.parseColor("#004386"));
            } else {
                generalCredit.setTextColor(Color.parseColor("#ff0000"));
            }
        } else {
            totalCredit.setText(wkuGraduation.getWkuGraduationData().getCurGraduationCredit() + "");
            majorCredit.setText(wkuGraduation.getWkuGraduationData().getCurMajorCredit() + "");
            generalCredit.setText(wkuGraduation.getWkuGraduationData().getCurGeneralCredit() + "");
            totalGrade.setText(String.format("%.3g%n", getTotalGradeData()));
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

class GradeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<WKUGradeData> arrayList;
    private int layout;

    public GradeAdapter(Context context, int layout, ArrayList<WKUGradeData> arrayList) {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.layout = layout;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arrayList.get(position).getAvgGrade();
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

        TextView schoolYear = convertView.findViewById(R.id.grade_schoolYear);
        TextView term = convertView.findViewById(R.id.grade_term);
        TextView ackCredit = convertView.findViewById(R.id.grade_ack_credit);
        TextView avgGrade = convertView.findViewById(R.id.grade_avg_grade);

        schoolYear.setText(arrayList.get(position).getSchoolYear());
        term.setText(arrayList.get(position).getTerm()+"");
        ackCredit.setText(arrayList.get(position).getAckCredit() + "");
        avgGrade.setText(arrayList.get(position).getAvgGrade() + "");

        return convertView;
    }
}

class GradeDetailAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<WKUGradeDetailData> arrayList;
    private int layout;

    public GradeDetailAdapter(Context context, int layout, ArrayList<WKUGradeDetailData> arrayList) {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() { return arrayList.size(); }

    @Override
    public Object getItem(int position) { return this.arrayList.get(position).getMarkGrade(); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        TextView category = convertView.findViewById(R.id.grade_detail_category);
        TextView subject = convertView.findViewById(R.id.grade_detail_subject);
        TextView credit = convertView.findViewById(R.id.grade_detail_credit);
        TextView avgGrade = convertView.findViewById(R.id.grade_detail_avg_grade);
        TextView markGrade = convertView.findViewById(R.id.grade_detail_mark_grade);

        category.setText(arrayList.get(position).getCategory());
        subject.setText(arrayList.get(position).getSubject());
        credit.setText(arrayList.get(position).getCredit());
        avgGrade.setText(arrayList.get(position).getAvgGrade());
        markGrade.setText(arrayList.get(position).getMarkGrade());

        return convertView;
    }
}

class GradeCurAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<WKUGradeCurData> arrayList;
    private int layout;

    public GradeCurAdapter(Context context, int layout, ArrayList<WKUGradeCurData> arrayList) {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() { return arrayList.size(); }

    @Override
    public Object getItem(int position) { return this.arrayList.get(position).getMarkGrade(); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        TextView category = convertView.findViewById(R.id.cur_grade_category);
        TextView subject = convertView.findViewById(R.id.cur_grade_subject);
        TextView credit = convertView.findViewById(R.id.cur_grade_credit);
        TextView midExam = convertView.findViewById(R.id.cur_grade_midExam);
        TextView finalExam = convertView.findViewById(R.id.cur_grade_finalExam);
        TextView attendAssign = convertView.findViewById(R.id.cur_grade_attendAssign);
        TextView other = convertView.findViewById(R.id.cur_grade_other);
        TextView markGrade = convertView.findViewById(R.id.cur_grade_markGrade);

        category.setText(arrayList.get(position).getCategory());
        subject.setText(arrayList.get(position).getSubject());
        credit.setText(arrayList.get(position).getCredit());
        midExam.setText(arrayList.get(position).getMidExam());
        finalExam.setText(arrayList.get(position).getFinalExam());
        attendAssign.setText(arrayList.get(position).getAttendAssign());
        other.setText(arrayList.get(position).getOther());
        markGrade.setText(arrayList.get(position).getMarkGrade());

        return convertView;
    }
}