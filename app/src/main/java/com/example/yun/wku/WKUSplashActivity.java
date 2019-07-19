package com.example.yun.wku;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class WKUSplashActivity extends AppCompatActivity {
    private boolean AUTO_LOGIN_FLAG;
    private boolean ERROR_LOG_FLAG = true;

    private Context context;

    private WebView webView;

    private String wkuId;
    private String wkuPw;

    private String jSessionId = "";
    private String wkuTokenKey = "";

    Bitmap wkuPrivateBitmap;
    private ProgressBar loadingBar;

    private WKUScrapingEngine wkuScrapingEngine;
    private WKUDatabase wkuDatabase;
    private WKUGraduation wkuGraduation;

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            Log.i("WKU", "WKUSplashActivity.onReceivedHttpError()... ");

            webView.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                        Log.i("WKU", "WKUSplashActivity.post() / runnable...");
                        if(AUTO_LOGIN_FLAG) {
                            webView.loadUrl("javascript:initIDPW(\"" + wkuId + "\", \"" + wkuPw + "\")");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("WKU", "WKUSplashActivity.shouldOverrideUrlLoading()... ");

            if(url.startsWith("http://intra.wku.ac.kr/SWupis/V005/index.jsp")) {
                String cookies = CookieManager.getInstance().getCookie(url);
                splitCookieStr(cookies);
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("WKU", "WKUSplashActivity.onPageFinished / url : " + url);

            if(url.startsWith("http://intra.wku.ac.kr/SWupis/V005/login.jsp")) {
                webView.loadUrl("javascript:(function(){ var my_script = document.createElement('script'); " +
                        "my_script.setAttribute('src','http://s2mos2.dothome.co.kr/cookieCallback.js'); " +
                        "document.body.appendChild(my_script); })()");
                autoLogin();

            } else if(jSessionId != "" && url.startsWith("http://intra.wku.ac.kr/SWupis/V005/index.jsp")) {
                ERROR_LOG_FLAG = false;
                wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_LOGIN_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey);
                wkuScrapingEngine.start();

            } else if(url.startsWith("http://intra.wku.ac.kr/SWupis/V005/login.jsp?error")) {
                ERROR_LOG_FLAG = false;
                Intent intent = new Intent(WKUSplashActivity.this, WKULoginActivity.class);
                intent.putExtra("source", "splash");
                startActivity(intent);
                finish();

                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {

                wkuGraduation = new WKUGraduation(context, wkuScrapingEngine.getWkuData().getWkuPrivateData().getMajor(), Integer.parseInt(wkuScrapingEngine.getWkuData().getWkuPrivateData().getStudentNo().substring(0, 4)));

                wkuDatabase.initPrivateData();
                wkuDatabase.initScheduleData();

                wkuDatabase.insertPrivateData(wkuScrapingEngine, wkuGraduation.getType());
                wkuDatabase.insertScheduleData(wkuScrapingEngine);

                new WKUSplashActivity.LoadImage()
                        .execute("http://intra.wku.ac.kr/SWupis/ViewPicture?sNo=" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getStudentNo());

                Intent intent = new Intent(WKUSplashActivity.this, WKUMainActivity.class);
                intent.putExtra("curPage", "main");
                startActivity(intent);
                finish();

                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
            }
        }
    };

    Handler errorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(ERROR_LOG_FLAG) {
                loadingBar.setVisibility(View.GONE);
                Toast.makeText(WKUSplashActivity.this, "죄송합니다. 네트워크 에러가 발생했습니다. LTE 환경으로 접속하시면 보다 원활한 이용 가능합니다.", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkusplash);

        context = this;

        webView = findViewById(R.id.splash_webview);
        wkuDatabase = new WKUDatabase(this);

        loadingBar = findViewById(R.id.splash_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        initWebView();

        Thread errorRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                    Log.i("WKU", "WKUSplashActivitiy.run() / error!!!");
                    Message msgError = new Message();
                    errorHandler.sendMessage(msgError);

                    Thread.sleep(3000);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        errorRequest.start();
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        clearCookie();

        webView.setWebViewClient(webViewClient);

        webView.loadUrl("http://intra.wku.ac.kr/SWupis/V005/login.jsp");
        loadingBar.setVisibility(View.VISIBLE);
    }

    private void autoLogin() {

        try {
            AUTO_LOGIN_FLAG = false;

            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT wkuId, wkuPw FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
                wkuId = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(0), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                wkuPw = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(1), wkuDatabase.getWkuRsaSecurity().getPrivateKey());

                AUTO_LOGIN_FLAG = true;
            }

            if(AUTO_LOGIN_FLAG) {
                webView.loadUrl("javascript:initIDPW(\"" + wkuId + "\", \"" + wkuPw + "\")");
            } else {
                loadLoginActivity();
            }
        } catch (SQLiteException e) {
            loadLoginActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLoginActivity() {
        ERROR_LOG_FLAG = false;
        Intent intent = new Intent(WKUSplashActivity.this, WKULoginActivity.class);
        intent.putExtra("source", "splash");
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
    }

    private void clearCookie() {
        webView.clearHistory();

        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
        cookieSyncManager.sync();
    }

    private void splitCookieStr(String str) {
        String[] splitStr = str.split("; ");

        for(int i=0; i<splitStr.length; i++) {
            String[] splitSplitStr = splitStr[i].split("=");

            if(splitSplitStr[0].equals("JSESSIONID")) {
                jSessionId = splitSplitStr[1];
            } else if(splitSplitStr[0].equals("wkuTokenKey")) {
                wkuTokenKey = splitSplitStr[1];
            }
        }
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                wkuPrivateBitmap = BitmapFactory
                        .decodeStream((InputStream) new URL(args[0])
                                .getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return wkuPrivateBitmap;
        }

        protected void onPostExecute(Bitmap privateImage) {

            if (privateImage != null) {
                try {
                    FileOutputStream fos = openFileOutput("wkuprivateimage.png",  Context.MODE_PRIVATE);
                    privateImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
