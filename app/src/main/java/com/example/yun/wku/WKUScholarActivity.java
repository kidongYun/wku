package com.example.yun.wku;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WKUScholarActivity extends AppCompatActivity implements View.OnClickListener {
    private final int ANIMATION_MAX_SIZE = 600;
    private YunAnimationController yunAnimationController;

    private LinearLayout scholarTotalLayout;

    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;

    private ImageView wkuLogoImg;
    private ProgressBar loadingBar;

    private DrawerLayout scholarDrawer;
    private LinearLayout scholarRightDrawer;

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

    private TextView sumLabel;
    private TextView sumMoney;

    private ListView scholarList;
    private ArrayList<WKUScholarData> arrayList;
    private ScholarAdapter scholarAdapter;

    private WKUDatabase wkuDatabase;

    private WKUScrapingEngine wkuScrapingEngine;

    private String isDorm = "";
    private String wkuId, wkuPw;

    private String jSessionId = "";
    private String wkuTokenKey = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {
                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.SCHOLAR_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();

            } else if(msg.what == WKUDBThread.DB_FINISH_FLAG) {
                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);

                getScholarData();
                sumMoney.setText(parseMoneyForm(calSumScholarMoney()+"") + "원");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkuscholar);

        scholarTotalLayout = findViewById(R.id.scholar_total_layout);

        prevBtn = findViewById(R.id.scholar_prev_btn);
        wkuLogoBtn = findViewById(R.id.scholar_wkulogo_btn);
        bookmarkBtn = findViewById(R.id.scholar_bookmark_btn);

        wkuLogoImg = findViewById(R.id.scholar_wkulogo_img);
        loadingBar = findViewById(R.id.scholar_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

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

        scholarDrawer = findViewById(R.id.scholar_drawer);
        scholarRightDrawer = findViewById(R.id.scholar_rightDrawer);

        sumLabel = findViewById(R.id.scholar_sum_label);
        sumMoney = findViewById(R.id.scholar_sum_money);

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

        initImageResource();
        initLists();

        wkuDatabase = new WKUDatabase(this);

        yunAnimationController = new YunAnimationController(scholarList);

        yunAnimationController.addAnimation(
                new YunScaleAnimation(scholarTotalLayout, 0, ANIMATION_MAX_SIZE, 0, 0),
                new YunScaleAnimation(scholarTotalLayout, 0, 0, 0, ANIMATION_MAX_SIZE),
                null,
                null
        );
        yunAnimationController.start();


        getScrapingData();

        getScholarData();
        sumMoney.setText(parseMoneyForm(calSumScholarMoney()+"") + "원");

        loadingBar.setVisibility(View.VISIBLE);
        wkuLogoImg.setVisibility(View.GONE);

        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_SCHOLAR_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey);
        wkuScrapingEngine.start();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.scholar_prev_btn:
                finish();
                break;

            case R.id.scholar_wkulogo_btn:
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;

            case R.id.scholar_bookmark_btn:
                scholarDrawer.openDrawer(scholarRightDrawer);
                break;

            case R.id.bookmark_attend_menu :
                scholarDrawer.closeDrawer(scholarRightDrawer);
                finish();
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                scholarDrawer.closeDrawer(scholarRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                scholarDrawer.closeDrawer(scholarRightDrawer);
                break;

            case R.id.bookmark_grade_menu :
                scholarDrawer.closeDrawer(scholarRightDrawer);
                finish();
                Intent scoreIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(scoreIntent);
                break;

            case R.id.bookmark_dorm_menu :
                scholarDrawer.closeDrawer(scholarRightDrawer);
                if(isDorm.equals("YES")) {
                    finish();
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                scholarDrawer.closeDrawer(scholarRightDrawer);
                Toast.makeText(WKUScholarActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                scholarDrawer.closeDrawer(scholarRightDrawer);
                Toast.makeText(WKUScholarActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_complain_menu :
                scholarDrawer.closeDrawer(scholarRightDrawer);
                Toast.makeText(WKUScholarActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initLists() {
        scholarList = findViewById(R.id.scholar_list);

        arrayList = new ArrayList<>();

        scholarAdapter = new ScholarAdapter(this, R.layout.list_wkuscholar, arrayList);
        scholarList.setAdapter(scholarAdapter);
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

    private void getScholarData() {
        arrayList.clear();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT year, term, title, money FROM wkuScholarData", null);

        WKUScholarData scholar;

        while(cursor.moveToNext()) {
            scholar = new WKUScholarData(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
            arrayList.add(scholar);
        }

        scholarAdapter.notifyDataSetChanged();
    }

    private int calSumScholarMoney() {
        int sum = 0;
        for(int i=0; i<arrayList.size(); i++) {
            sum += arrayList.get(i).getMoneyToInt();
        }

        return sum;
    }

    private String parseMoneyForm(String strMoney) {
        StringBuffer strBufMoney = new StringBuffer(strMoney);
        int length = strMoney.length();

        for(int i = length; i >= 0; i -= 3) {
            if(i == length || i == 0) {
                continue;
            }
            strBufMoney.insert(i, ",");
        }

        return strBufMoney.toString();
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

class ScholarAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<WKUScholarData> arrayList;
    private int layout;

    public ScholarAdapter(Context context, int layout, ArrayList<WKUScholarData> arrayList) {
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

        TextView title = convertView.findViewById(R.id.scholar_title);
        TextView money = convertView.findViewById(R.id.scholar_money);
        TextView date = convertView.findViewById(R.id.scholar_date);

        title.setText(arrayList.get(position).getTitle());
        money.setText(arrayList.get(position).getMoneyToString());
        date.setText(arrayList.get(position).getDate());

        return convertView;
    }
}
