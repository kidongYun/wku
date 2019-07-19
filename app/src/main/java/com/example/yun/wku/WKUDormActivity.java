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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WKUDormActivity extends AppCompatActivity implements View.OnClickListener {
    public final int REQUEST_FLAG = 0;
    public final int CANCEL_FLAG = 1;

    private final String[] week = { "일", "월", "화", "수", "목", "금", "토" };
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private TextView sunday;

    private String wkuId, wkuPw;

    private String jSessionId = "";
    private String wkuTokenKey = "";

    private int curClickPosition = -1;

    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;

    private ImageView wkuLogoImg;
    private ProgressBar loadingBar;

    private ListView dormList;
    private ArrayList<WKUDormData> arrayList;
    private DormAdapter dormAdapter;

    private DrawerLayout dormDrawer;
    private LinearLayout dormRightDrawer;

    private LinearLayout bookmarkPresentMenu;
    private LinearLayout bookmarkScheduleMenu;
    private LinearLayout bookmarkScholarMenu;
    private LinearLayout bookmarkScoreMenu;
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

    private DatePicker datePicker;

    private String day;

    private String date;
    private EditText reason;
    private EditText location;
    private EditText emgTel;

    private LinearLayout submitBtn;
    private int submitState = REQUEST_FLAG;

    private TextView submitTextView;

    private WKUScrapingEngine wkuScrapingEngine;
    private WKUDatabase wkuDatabase;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == WKUScrapingEngine.SCRAPING_DORM_FINISH_MODE) {
                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.DORM_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();
            } else if(msg.what == WKUScrapingEngine.SCRAPING_DORM_ADD_FINISH_MODE) {
                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.DORM_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();

                Toast.makeText(WKUDormActivity.this, "외박 신청이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            } else if(msg.what == WKUScrapingEngine.SCRAPING_DORM_CANCEL_FINISH_MODE) {
                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.DORM_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();

                Toast.makeText(WKUDormActivity.this, "외박 취소가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            } else if(msg.what == WKUScrapingEngine.SCRAPING_ERROR_MODE) {
                Toast.makeText(WKUDormActivity.this, "해당 일에 외박 내역이 있습니다.", Toast.LENGTH_SHORT).show();

            } else if(msg.what == WKUDBThread.DB_FINISH_FLAG) {
                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);

                getDormData();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkudorm);

        monday = findViewById(R.id.dorm_monday);
        tuesday = findViewById(R.id.dorm_tuesday);
        wednesday = findViewById(R.id.dorm_wednesday);
        thursday = findViewById(R.id.dorm_thursday);
        friday = findViewById(R.id.dorm_friday);
        saturday = findViewById(R.id.dorm_saturday);
        sunday = findViewById(R.id.dorm_sunday);

        prevBtn =  findViewById(R.id.dorm_prev_btn);
        wkuLogoBtn = findViewById(R.id.dorm_wkulogo_btn);
        bookmarkBtn = findViewById(R.id.dorm_bookmark_btn);

        wkuLogoImg = findViewById(R.id.dorm_wkulogo_img);
        loadingBar = findViewById(R.id.dorm_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        dormDrawer = findViewById(R.id.dorm_drawer);
        dormRightDrawer = findViewById(R.id.dorm_rightDrawer);

        bookmarkPresentMenu = findViewById(R.id.bookmark_attend_menu);
        bookmarkScheduleMenu = findViewById(R.id.bookmark_schedule_menu);
        bookmarkScholarMenu = findViewById(R.id.bookmark_scholar_menu);
        bookmarkScoreMenu = findViewById(R.id.bookmark_grade_menu);
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

        datePicker = findViewById(R.id.dorm_insert_date);
        reason = findViewById(R.id.dorm_insert_reason);
        location = findViewById(R.id.dorm_insert_location);
        emgTel = findViewById(R.id.dorm_insert_emgTel);

        submitBtn = findViewById(R.id.submitBtn);
        submitTextView = findViewById(R.id.submit_textView);

        prevBtn.setOnClickListener(this);
        wkuLogoBtn.setOnClickListener(this);
        bookmarkBtn.setOnClickListener(this);

        bookmarkPresentMenu.setOnClickListener(this);
        bookmarkScheduleMenu.setOnClickListener(this);
        bookmarkScholarMenu.setOnClickListener(this);
        bookmarkScoreMenu.setOnClickListener(this);
        bookmarkDormMenu.setOnClickListener(this);
        bookmarkMenstMenu.setOnClickListener(this);
        bookmarkSettingMenu.setOnClickListener(this);
        bookmarkComplainMenu.setOnClickListener(this);

        submitBtn.setOnClickListener(this);

        location.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    reason.requestFocus();
                    return true;
                }
                return false;
            }
        });

        reason.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    emgTel.requestFocus();
                    return true;
                }
                return false;
            }
        });

        emgTel.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submitBtn.callOnClick();
                    return true;
                }
                return false;
            }
        });

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                initDate();
            }
        });

        dormList = findViewById(R.id.dorm_list);
        arrayList = new ArrayList<>();
        dormAdapter = new DormAdapter(this, R.layout.list_wkudorm, arrayList);
        dormList.setAdapter(dormAdapter);
        dormList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        wkuDatabase = new WKUDatabase(this);

        dormList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(curClickPosition == position) {
                    parent.getChildAt(position).setBackgroundColor(Color.parseColor("#ffffff"));
                    submitState = REQUEST_FLAG;
                    curClickPosition = -1;
                    submitTextView.setText("외박신청");
                } else {
                    submitState = CANCEL_FLAG;
                    if(curClickPosition != -1) {
                        parent.getChildAt(curClickPosition).setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                    parent.getChildAt(position).setBackgroundColor(Color.parseColor("#eeeeee"));
                    curClickPosition = position;
                    submitTextView.setText("외박취소");
                }
            }
        });

        String date = getDate(datePicker);

        try {
            day = getDateDay(date, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initImageResource();
        getScrapingData();
        getDormData();

        loadingBar.setVisibility(View.VISIBLE);
        wkuLogoImg.setVisibility(View.GONE);

        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_DORM_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey);
        wkuScrapingEngine.start();

        initDormTextValue();

        initDate();
    }

    private void initDate() {
        date = getDate(datePicker);

        try {
            day = getDateDay(date, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        dayDesigning(day);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dorm_prev_btn :
                finish();
                break;

            case R.id.dorm_wkulogo_btn :
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;

            case R.id.dorm_bookmark_btn :
                dormDrawer.openDrawer(dormRightDrawer);
                break;

            case R.id.submitBtn :
                if(submitState == REQUEST_FLAG) {
                    date = getDateYYYYMMDD(datePicker);

                    if(day.equals("금") || day.equals("토") || day.equals("일")) {
                        Toast.makeText(this, "해당일은 주말입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        if(checkDormData(date, reason.getText().toString(), location.getText().toString(), emgTel.getText().toString())) {
                            loadingBar.setVisibility(View.GONE);
                            wkuLogoImg.setVisibility(View.VISIBLE);

                            wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_DORM_ADD_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, date, reason.getText().toString(), location.getText().toString(), emgTel.getText().toString());
                            wkuScrapingEngine.start();
                        }
                    }
                } else if(submitState == CANCEL_FLAG) {
                    if(checkDormCancelData(arrayList.get(curClickPosition).getDateToYYYYMMDD())) {
                        loadingBar.setVisibility(View.GONE);
                        wkuLogoImg.setVisibility(View.VISIBLE);

                        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_DORM_CANCEL_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, arrayList.get(curClickPosition).getDateToYYYYMMDD(), arrayList.get(curClickPosition).getReason(), arrayList.get(curClickPosition).getLocation(), arrayList.get(curClickPosition).getEmgTel());
                        wkuScrapingEngine.start();
                    }
                }
                break;
            case R.id.bookmark_attend_menu :
                dormDrawer.closeDrawer(dormRightDrawer);
                finish();
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                dormDrawer.closeDrawer(dormRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                dormDrawer.closeDrawer(dormRightDrawer);
                finish();
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                dormDrawer.closeDrawer(dormRightDrawer);
                finish();
                Intent scoreIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(scoreIntent);
                break;

            case R.id.bookmark_dorm_menu :
                dormDrawer.closeDrawer(dormRightDrawer);
                break;

            case R.id.bookmark_menst_menu :
                dormDrawer.closeDrawer(dormRightDrawer);
                Toast.makeText(WKUDormActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                dormDrawer.closeDrawer(dormRightDrawer);
                Toast.makeText(WKUDormActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_complain_menu :
                dormDrawer.closeDrawer(dormRightDrawer);
                Toast.makeText(WKUDormActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initDormTextValue() {
        if(!arrayList.isEmpty()) {
            location.setText(arrayList.get(0).getLocation());
            reason.setText(arrayList.get(0).getReason());
            emgTel.setText(arrayList.get(0).getEmgTel());
        }
    }

    private void getScrapingData() {
        try {
            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT wkuId, wkuPw, jSessionId, wkuTokenKey, studentNo, isDorm FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
                this.wkuId = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(0), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.wkuPw = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(1), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.jSessionId = cursor.getString(2);
                this.wkuTokenKey = cursor.getString(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDormData() {
        arrayList.clear();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT date, reason, location, emgTel FROM wkuDormData", null);

        WKUDormData dorm;

        while(cursor.moveToNext()) {
            dorm = new WKUDormData(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            arrayList.add(dorm);
        }

        dormAdapter.notifyDataSetChanged();
    }

    private void dayDesigning(String day) {
        switch (day) {
            case "월" :
                monday.setTextSize(20); monday.setTextColor(Color.parseColor("#aa000000"));
                tuesday.setTextSize(14); tuesday.setTextColor(Color.parseColor("#55000000"));
                wednesday.setTextSize(14); wednesday.setTextColor(Color.parseColor("#55000000"));
                thursday.setTextSize(14); thursday.setTextColor(Color.parseColor("#55000000"));
                friday.setTextSize(14); friday.setTextColor(Color.parseColor("#55000000"));
                saturday.setTextSize(14); saturday.setTextColor(Color.parseColor("#55000000"));
                sunday.setTextSize(14); sunday.setTextColor(Color.parseColor("#55000000"));
                break;

            case "화" :
                monday.setTextSize(14); monday.setTextColor(Color.parseColor("#55000000"));
                tuesday.setTextSize(20); tuesday.setTextColor(Color.parseColor("#aa000000"));
                wednesday.setTextSize(14); wednesday.setTextColor(Color.parseColor("#55000000"));
                thursday.setTextSize(14); thursday.setTextColor(Color.parseColor("#55000000"));
                friday.setTextSize(14); friday.setTextColor(Color.parseColor("#55000000"));
                saturday.setTextSize(14); saturday.setTextColor(Color.parseColor("#55000000"));
                sunday.setTextSize(14); sunday.setTextColor(Color.parseColor("#55000000"));
                break;

            case "수" :
                monday.setTextSize(14); monday.setTextColor(Color.parseColor("#55000000"));
                tuesday.setTextSize(14); tuesday.setTextColor(Color.parseColor("#55000000"));
                wednesday.setTextSize(20); wednesday.setTextColor(Color.parseColor("#aa000000"));
                thursday.setTextSize(14); thursday.setTextColor(Color.parseColor("#55000000"));
                friday.setTextSize(14); friday.setTextColor(Color.parseColor("#55000000"));
                saturday.setTextSize(14); saturday.setTextColor(Color.parseColor("#55000000"));
                sunday.setTextSize(14); sunday.setTextColor(Color.parseColor("#55000000"));
                break;

            case "목" :
                monday.setTextSize(14); monday.setTextColor(Color.parseColor("#55000000"));
                tuesday.setTextSize(14); tuesday.setTextColor(Color.parseColor("#55000000"));
                wednesday.setTextSize(14); wednesday.setTextColor(Color.parseColor("#55000000"));
                thursday.setTextSize(20); thursday.setTextColor(Color.parseColor("#aa000000"));
                friday.setTextSize(14); friday.setTextColor(Color.parseColor("#55000000"));
                saturday.setTextSize(14); saturday.setTextColor(Color.parseColor("#55000000"));
                sunday.setTextSize(14); sunday.setTextColor(Color.parseColor("#55000000"));
                break;

            case "금" :
                monday.setTextSize(14); monday.setTextColor(Color.parseColor("#55000000"));
                tuesday.setTextSize(14); tuesday.setTextColor(Color.parseColor("#55000000"));
                wednesday.setTextSize(14); wednesday.setTextColor(Color.parseColor("#55000000"));
                thursday.setTextSize(14); thursday.setTextColor(Color.parseColor("#55000000"));
                friday.setTextSize(20); friday.setTextColor(Color.parseColor("#aaaa0000"));
                saturday.setTextSize(14); saturday.setTextColor(Color.parseColor("#55000000"));
                sunday.setTextSize(14); sunday.setTextColor(Color.parseColor("#55000000"));
                break;

            case "토" :
                monday.setTextSize(14); monday.setTextColor(Color.parseColor("#55000000"));
                tuesday.setTextSize(14); tuesday.setTextColor(Color.parseColor("#55000000"));
                wednesday.setTextSize(14); wednesday.setTextColor(Color.parseColor("#55000000"));
                thursday.setTextSize(14); thursday.setTextColor(Color.parseColor("#55000000"));
                friday.setTextSize(14); friday.setTextColor(Color.parseColor("#55000000"));
                saturday.setTextSize(20); saturday.setTextColor(Color.parseColor("#aaaa0000"));
                sunday.setTextSize(14); sunday.setTextColor(Color.parseColor("#55000000"));
                break;

            case "일" :
                monday.setTextSize(14);monday.setTextColor(Color.parseColor("#55000000"));
                tuesday.setTextSize(14); tuesday.setTextColor(Color.parseColor("#55000000"));
                wednesday.setTextSize(14); wednesday.setTextColor(Color.parseColor("#55000000"));
                thursday.setTextSize(14); thursday.setTextColor(Color.parseColor("#55000000"));
                friday.setTextSize(14); friday.setTextColor(Color.parseColor("#55000000"));
                saturday.setTextSize(14); saturday.setTextColor(Color.parseColor("#55000000"));
                sunday.setTextSize(20); sunday.setTextColor(Color.parseColor("#aaaa0000"));
                break;
        }
    }

    public String getDateDay(String date, String dateType) throws Exception {
        String day = "" ;

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
        Date nDate = dateFormat.parse(date) ;

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

        switch(dayNum){
            case 1: day = "일"; break ;
            case 2: day = "월"; break ;
            case 3: day = "화"; break ;
            case 4: day = "수"; break ;
            case 5: day = "목"; break ;
            case 6: day = "금"; break ;
            case 7: day = "토"; break ;
        }

        return day ;
    }

    private String getDate(DatePicker datePicker) {
        String strDate = "";
        int year = datePicker.getYear();
        int month = datePicker.getMonth()+1;
        int dayOfMonth = datePicker.getDayOfMonth();

        if(dayOfMonth < 10 && month < 10) {
            strDate = "" + year + "-0" + month + "-0" + dayOfMonth;
        } else if(dayOfMonth >= 10 && month < 10 ) {
            strDate = "" + year + "-0" + month + "-" + dayOfMonth;
        } else if(dayOfMonth < 10 && month >= 10) {
            strDate = "" + year + "-" + month + "-0" + dayOfMonth;
        } else if(dayOfMonth >= 10 && month >= 10) {
            strDate = "" + year + "-" + month + "-" + dayOfMonth;
        }

        return strDate;
    }

    private String getDateYYYYMMDD(DatePicker datePicker) {
        String strDate = "";
        int year = datePicker.getYear();
        int month = datePicker.getMonth()+1;
        int dayOfMonth = datePicker.getDayOfMonth();

        if(dayOfMonth < 10 && month < 10) {
            strDate = "" + year + "0" + month + "0" + dayOfMonth;
        } else if(dayOfMonth >= 10 && month < 10 ) {
            strDate = "" + year + "0" + month + "" + dayOfMonth;
        } else if(dayOfMonth < 10 && month >= 10) {
            strDate = "" + year + "" + month + "0" + dayOfMonth;
        } else if(dayOfMonth >= 10 && month >= 10) {
            strDate = "" + year + "" + month + "" + dayOfMonth;
        }

        return strDate;
    }

    private boolean checkDormData(String date, String reason, String location, String emgTel) {

        long now = System.currentTimeMillis();
        Date curDate = new Date(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String curDateStr = simpleDateFormat.format(curDate);

//        Log.i("WKU", "curDateStr : " + curDateStr);

        if(location.equals("")) {
            Toast.makeText(WKUDormActivity.this, "숙박지를 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(reason.equals("")) {
            Toast.makeText(WKUDormActivity.this, "외박사유를 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (emgTel.equals("")) {
            Toast.makeText(WKUDormActivity.this, "비상연락처를 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(Integer.parseInt(curDateStr) > Integer.parseInt(date)) {
            Toast.makeText(WKUDormActivity.this, "지난 과거는 잊어버리세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean checkDormCancelData(String date) {
        Log.i("WKU", "WKUDormActivity.checkDormCancelData / date : " + date);
        long now = System.currentTimeMillis();
        Date curDate = new Date(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String curDateStr = simpleDateFormat.format(curDate);

        if(Integer.parseInt(curDateStr) > Integer.parseInt(date)) {
            Toast.makeText(WKUDormActivity.this, "지난 과거는 잊어버리세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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

class DormAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<WKUDormData> arrayList;
    private int layout;

    public DormAdapter(Context context, int layout, ArrayList<WKUDormData> arrayList) {
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
        return this.arrayList.get(position).getDate();
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

        TextView date = convertView.findViewById(R.id.dorm_date);
        TextView reason = convertView.findViewById(R.id.dorm_reason);
        TextView location = convertView.findViewById(R.id.dorm_location);
        TextView emgTel = convertView.findViewById(R.id.dorm_emg_tel);

        date.setText(arrayList.get(position).getDate());
        reason.setText(arrayList.get(position).getReason());
        location.setText(arrayList.get(position).getLocation());
        emgTel.setText(arrayList.get(position).getEmgTel());

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }
}
