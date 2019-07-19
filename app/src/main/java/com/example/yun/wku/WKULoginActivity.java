package com.example.yun.wku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.TrustAnchor;

public class WKULoginActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean AUTO_LOGIN_FLAG;

    private Context context;
    private EditText wkuId;
    private EditText wkuPw;
    private TextView loginLog;
    private ImageView loginBtn;
    private WebView webView;

    Bitmap wkuPrivateBitmap;
    private ProgressBar progressBar;

    private Bundle source;

    private String jSessionId = "";
    private String wkuTokenKey = "";

    private WKUScrapingEngine wkuScrapingEngine;
    private WKUDatabase wkuDatabase;
    private WKUGraduation wkuGraduation;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {

                wkuGraduation = new WKUGraduation(context, wkuScrapingEngine.getWkuData().getWkuPrivateData().getMajor(), Integer.parseInt(wkuScrapingEngine.getWkuData().getWkuPrivateData().getStudentNo().substring(0, 4)));

                wkuDatabase.initPrivateData();
                wkuDatabase.initScheduleData();
                wkuDatabase.initSettingData();

                wkuDatabase.insertPrivateData(wkuScrapingEngine, wkuGraduation.getType());
                wkuDatabase.insertScheduleData(wkuScrapingEngine);

                new LoadImage()
                        .execute("http://intra.wku.ac.kr/SWupis/ViewPicture?sNo=" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getStudentNo());

                finish();
                Intent intent = new Intent(WKULoginActivity.this, WKUMainActivity.class);
                intent.putExtra("curPage", "main");
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkulogin);

        context = this;

        source = getIntent().getExtras();

        wkuId = findViewById(R.id.wku_id);
        wkuPw = findViewById(R.id.wku_pw);
        loginLog = findViewById(R.id.login_log);
        loginBtn = findViewById(R.id.login_btn);
        webView = findViewById(R.id.login_webview);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        wkuDatabase = new WKUDatabase(this);

        loginBtn.setOnClickListener(this);

        initWebView();
        initKeyListener();
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        clearCookie();

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.startsWith("http://intra.wku.ac.kr/SWupis/V005/login.jsp?error")) {
                    progressBar.setVisibility(View.GONE);
                    loginLog.setText("웹정보서비스 계정으로 로그인 해주세요");

                    Toast.makeText(WKULoginActivity.this, "유효하지 않은 아이디 혹은 비밀번호 입니다.", Toast.LENGTH_SHORT).show();

                } else if(url.startsWith("http://intra.wku.ac.kr/SWupis/V005/index.jsp")) {
                    String cookies = CookieManager.getInstance().getCookie(url);
                    splitCookieStr(cookies);
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                webView.loadUrl("javascript:(function(){ var my_script = document.createElement('script'); " +
                        "my_script.setAttribute('src','http://s2mos2.dothome.co.kr/cookieCallback.js'); " +
                        "document.body.appendChild(my_script); })()");

                if(jSessionId != "" && url.startsWith("http://intra.wku.ac.kr/SWupis/V005/index.jsp")) {
                    wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_LOGIN_MODE, handler, wkuId.getText().toString(), wkuPw.getText().toString(), jSessionId, wkuTokenKey);
                    wkuScrapingEngine.start();

                } else if(url.startsWith("http://intra.wku.ac.kr/SWupis/V005/login.jsp")) {
                    loginLog.setText("웹정보서비스 계정으로 로그인 해주세요");
                    wkuDatabase.initAllData();
                }
            }
        });

        webView.loadUrl("http://intra.wku.ac.kr/SWupis/V005/login.jsp");
    }

    private void initKeyListener() {
        wkuPw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    loginBtn.callOnClick();
                    return true;
                }
                return false;
            }
        });
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

    @Override
    public void onClick(View v) {
        if(exceptionIDPW()) {
            progressBar.setVisibility(View.VISIBLE);
            loginLog.setText("잠시만 기다려 주세요.");

            webView.loadUrl("javascript:initIDPW(\"" + wkuId.getText().toString() + "\", \"" + wkuPw.getText().toString() + "\")");
        }
    }

    private boolean exceptionIDPW() {
        if(wkuId.getText().toString().equals("")) {
            Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if(wkuPw.getText().toString().equals("")) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(WKULoginActivity.this);
            pDialog.setMessage("학사 이미지 로딩중입니다...");
            pDialog.show();
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
                    pDialog.dismiss();
                } catch (Exception e) {

                }
            } else {
                pDialog.dismiss();
                Toast.makeText(WKULoginActivity.this, "학사 이미지가 존재하지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
