package com.example.yun.wku;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WKUBBSDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;

    private WebView webView;
    private WebView webViewForDownload;

    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;

    private DrawerLayout bbsdetailDrawer;
    private LinearLayout bbsdetailRightDrawer;

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
    private WKUScrapingEngine wkuScrapingEngine;

    private String cId;
    private String url;

    private String isDorm = "";
    private String wkuId, wkuPw;

    private String jSessionId = "";
    private String wkuTokenKey = "";

    private String downloadUrl = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            downloadUrl = (String)msg.obj;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkubbsdetail);

        prevBtn = findViewById(R.id.bbs_detail_prev_btn);
        wkuLogoBtn = findViewById(R.id.bbs_detail_wkulogo_btn);
        bookmarkBtn = findViewById(R.id.bbs_detail_bookmark_btn);

        bbsdetailDrawer = findViewById(R.id.bbs_detail_drawer);
        bbsdetailRightDrawer = findViewById(R.id.bbs_detail_rightDrawer);

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

        getScrapingData();

        intent = getIntent();
        url = intent.getExtras().getString("url");

        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_BBS_DETAIL_DOWNLOAD_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, url);
        wkuScrapingEngine.start();

        initWebView();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bbs_detail_prev_btn:
                finish();
                break;
            case R.id.bbs_detail_wkulogo_btn:
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;
            case R.id.bbs_detail_bookmark_btn:
                bbsdetailDrawer.openDrawer(bbsdetailRightDrawer);
                break;

            case R.id.bookmark_attend_menu :
                bbsdetailDrawer.closeDrawer(bbsdetailRightDrawer);
                finish();
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                bbsdetailDrawer.closeDrawer(bbsdetailRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                bbsdetailDrawer.closeDrawer(bbsdetailRightDrawer);
                finish();
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                bbsdetailDrawer.closeDrawer(bbsdetailRightDrawer);
                finish();
                Intent scoreIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(scoreIntent);
                break;

            case R.id.bookmark_dorm_menu :
                bbsdetailDrawer.closeDrawer(bbsdetailRightDrawer);
                if(isDorm.equals("YES")) {
                    finish();
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                bbsdetailDrawer.closeDrawer(bbsdetailRightDrawer);
                Toast.makeText(WKUBBSDetailActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                bbsdetailDrawer.closeDrawer(bbsdetailRightDrawer);
                Toast.makeText(WKUBBSDetailActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_complain_menu :
                bbsdetailDrawer.closeDrawer(bbsdetailRightDrawer);
                Toast.makeText(WKUBBSDetailActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initWebView() {
        webView = findViewById(R.id.bbs_detail_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.i("WKU", "WKUBBSDetailActivity.onPageFinished / url : " + url);

                view.loadUrl("javascript:(function(){ var my_awesome_script = document.createElement('script'); " +
                        "my_awesome_script.setAttribute('src','http://s2mos2.dothome.co.kr/callback.js'); " +
                        "document.body.appendChild(my_awesome_script); })()");
            }
        });

        webView.addJavascriptInterface(new JavascriptInterface(), "JSInterface");
        webView.loadUrl(url);
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

    final class JavascriptInterface {

        @android.webkit.JavascriptInterface
        public void callMethodName(final String fgubun){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("WKU", "callMethodName!!! fgubun : " + fgubun);

                    downloadUrl = downloadUrl.replaceAll("fgubun=", "fgubun=" + fgubun);

                    webViewForDownload = findViewById(R.id.bbs_detail_webview_download);
                    webViewForDownload.setDownloadListener(new DownloadListener() {
                        @Override
                        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                            try {
                                Log.i("WKU", "WKUBBSDetailActivity -> onDownloadStart()!!!");
                                Log.i("WKU", "WKUBBSDetailActivity -> onDownloadStart() -> contentDisposition : " + contentDisposition);
                                Log.i("WKU", "WKUBBSDetailActivity -> onDownloadStart() -> mimeType : " + mimeType);

                                String titleStr = URLUtil.guessFileName(url, contentDisposition, mimeType);
                                titleStr = titleStr.substring(0, titleStr.length()-1);

                                int pos = titleStr.lastIndexOf(".");
                                String ext = titleStr.substring(pos + 1);

                                long time = System.currentTimeMillis();
                                SimpleDateFormat dayTime = new SimpleDateFormat("yyyymmddhhmmss");
                                String timeStr = dayTime.format(new Date(time));

                                Log.i("WKU", "WKUBBSDetailActivity -> DownloadListener() -> titleStr : " + titleStr);
                                Log.i("WKU", "WKUBBSDetailActivity -> DownloadListener() -> ext : " + ext);
                                Log.i("WKU", "WKUBBSDetailActivity -> DownloadListener() -> timeStr : " + timeStr);

                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                                request.setMimeType(mimeType);
                                //------------------------COOKIE!!------------------------
                                String cookies = CookieManager.getInstance().getCookie(url);
                                request.addRequestHeader("cookie", cookies);
                                //------------------------COOKIE!!------------------------
                                request.addRequestHeader("User-Agent", userAgent);
                                request.setDescription("Downloading file...");
                                request.setTitle(timeStr +"." + ext);
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,  timeStr +"." + ext);
                                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                dm.enqueue(request);
                                Toast.makeText(getApplicationContext(), timeStr +"." + ext, Toast.LENGTH_LONG).show();

                            } catch (SecurityException e) {
                                if (ContextCompat.checkSelfPermission(WKUBBSDetailActivity.this,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    // Should we show an explanation?
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(WKUBBSDetailActivity.this,
                                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        Toast.makeText(getBaseContext(), "첨부파일 다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                                        ActivityCompat.requestPermissions(WKUBBSDetailActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                110);
                                    } else {
                                        Toast.makeText(getBaseContext(), "첨부파일 다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                                        ActivityCompat.requestPermissions(WKUBBSDetailActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                110);
                                    }
                                }
                            }
                        }
                    });

                    webViewForDownload.loadUrl(downloadUrl);
                }
            });
        }
    }
}
