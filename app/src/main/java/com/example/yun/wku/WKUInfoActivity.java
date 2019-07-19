package com.example.yun.wku;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WKUInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;

    private ImageView wkuLogoImg;
    private ProgressBar loadingBar;

    private DrawerLayout infoDrawer;
    private LinearLayout infoRightDrawer;

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

    private WKUDatabase wkuDatabase;

    private WebView webView;

    private String isDorm = "";

    private String url = "file:///android_asset/WKUInfo/WKUInfoWebview.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkuinfo);

        prevBtn = findViewById(R.id.info_prev_btn);
        wkuLogoBtn = findViewById(R.id.info_wkulogo_btn);
        bookmarkBtn = findViewById(R.id.info_bookmark_btn);

        wkuLogoImg = findViewById(R.id.info_wkulogo_img);
        loadingBar = findViewById(R.id.info_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        infoDrawer = findViewById(R.id.info_drawer);
        infoRightDrawer = findViewById(R.id.info_rightDrawer);

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

        wkuDatabase = new WKUDatabase(this);

        getIDPWData();

        loadingBar.setVisibility(View.VISIBLE);
        wkuLogoImg.setVisibility(View.GONE);

        webView = findViewById(R.id.info_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);

                loadingBar.setVisibility(View.GONE);
                wkuLogoImg.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.info_prev_btn:
                finish();
                break;
            case R.id.info_wkulogo_btn:
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;
            case R.id.info_bookmark_btn:
                infoDrawer.openDrawer(infoRightDrawer);
                break;


            case R.id.bookmark_attend_menu :
                infoDrawer.closeDrawer(infoRightDrawer);
                finish();
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                infoDrawer.closeDrawer(infoRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                infoDrawer.closeDrawer(infoRightDrawer);
                finish();
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                infoDrawer.closeDrawer(infoRightDrawer);
                finish();
                Intent scoreIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(scoreIntent);
                break;

            case R.id.bookmark_dorm_menu :
                infoDrawer.closeDrawer(infoRightDrawer);
                if(isDorm.equals("YES")) {
                    finish();
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                infoDrawer.closeDrawer(infoRightDrawer);
                Toast.makeText(WKUInfoActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                infoDrawer.closeDrawer(infoRightDrawer);
                Toast.makeText(WKUInfoActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_complain_menu :
                infoDrawer.closeDrawer(infoRightDrawer);
                Toast.makeText(WKUInfoActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void getIDPWData() {
        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT isDorm FROM wkuPrivateData", null);

        while(cursor.moveToNext()) {
            isDorm = cursor.getString(0);
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
