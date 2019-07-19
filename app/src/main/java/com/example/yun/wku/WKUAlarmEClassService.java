package com.example.yun.wku;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yun.wku.WKUScrapingEngine;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WKUAlarmEClassService extends Service implements Runnable {
    private static final int REBOOT_DELAY_TIMER = 10 * 1000;
    private static final int LOCATION_UPDATE_DELAY = 30 * 1000;

    private Handler mHandler;
    private boolean mIsRunning;
    private int mStartId = 0;

    private WKUScrapingEngine wkuScrapingEngine;
    private WKUDatabase wkuDatabase;

    private String studentNo;

    private ArrayList<WKUEClassData> oldWkuEClassDatas;
    private ArrayList<WKUEClassData> newWkuEClassDatas;
    private ArrayList<WKUEClassData> updatedEClassDatas;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {

                newWkuEClassDatas = wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas();
                getEclassDetailData(wkuDatabase, newWkuEClassDatas);

                for(int i=0; i<newWkuEClassDatas.size(); i++) {
                    for(int j=0; j<newWkuEClassDatas.get(i).getWkuEClassDetailNotiDatas().size(); j++) {
                        Log.i("WKU", "NEW : " + newWkuEClassDatas.get(i).getWkuEClassDetailNotiDatas().get(j).showData());
                        wkuDatabase.insertAlarmEClassDetailNotiData(newWkuEClassDatas.get(i).getWkuEClassDetailNotiDatas(), j);
                    }

                    for(int j=0; j<newWkuEClassDatas.get(i).getWkuEClassDetailPdsDatas().size(); j++) {
                        Log.i("WKU", "NEW : " + newWkuEClassDatas.get(i).getWkuEClassDetailPdsDatas().get(j).showData());
                        wkuDatabase.insertAlarmEClassDetailPdsData(newWkuEClassDatas.get(i).getWkuEClassDetailPdsDatas(), j);
                    }

                    for(int j=0; j<newWkuEClassDatas.get(i).getWkuEClassDetailReportDatas().size(); j++) {
                        Log.i("WKU", "NEW : " + newWkuEClassDatas.get(i).getWkuEClassDetailReportDatas().get(j).showData());
                        wkuDatabase.insertAlarmEClassDetailReportData(newWkuEClassDatas.get(i).getWkuEClassDetailReportDatas(), j);
                    }

                    for(int j=0; j<newWkuEClassDatas.get(i).getWkuEClassDetailLectureDatas().size(); j++) {
                        Log.i("WKU", "NEW : " + newWkuEClassDatas.get(i).getWkuEClassDetailLectureDatas().get(j).showData());
                        wkuDatabase.insertAlarmEClassDetailLectureData(newWkuEClassDatas.get(i).getWkuEClassDetailLectureDatas(), j);
                    }
                }

                for(int i=0; i<oldWkuEClassDatas.size(); i++) {
                    for(int j=0; j<oldWkuEClassDatas.get(i).getWkuEClassDetailNotiDatas().size(); j++) {
                        Log.i("WKU", "OLD : " + oldWkuEClassDatas.get(i).getWkuEClassDetailNotiDatas().get(j).showData());
                        wkuDatabase.insertAlarmEClassDetailNotiData(oldWkuEClassDatas.get(i).getWkuEClassDetailNotiDatas(), j);
                    }

                    for(int j=0; j<oldWkuEClassDatas.get(i).getWkuEClassDetailPdsDatas().size(); j++) {
                        Log.i("WKU", "OLD : " + oldWkuEClassDatas.get(i).getWkuEClassDetailPdsDatas().get(j).showData());
                        wkuDatabase.insertAlarmEClassDetailPdsData(oldWkuEClassDatas.get(i).getWkuEClassDetailPdsDatas(), j);
                    }

                    for(int j=0; j<oldWkuEClassDatas.get(i).getWkuEClassDetailReportDatas().size(); j++) {
                        Log.i("WKU", "OLD : " + oldWkuEClassDatas.get(i).getWkuEClassDetailReportDatas().get(j).showData());
                        wkuDatabase.insertAlarmEClassDetailReportData(oldWkuEClassDatas.get(i).getWkuEClassDetailReportDatas(), j);
                    }

                    for(int j=0; j<oldWkuEClassDatas.get(i).getWkuEClassDetailLectureDatas().size(); j++) {
                        Log.i("WKU", "OLD : " + oldWkuEClassDatas.get(i).getWkuEClassDetailLectureDatas().get(j).showData());
                        wkuDatabase.insertAlarmEClassDetailLectureData(oldWkuEClassDatas.get(i).getWkuEClassDetailLectureDatas(), j);
                    }
                }

                /////////////////////////////////////////////////////////////////////////////////////////////////

                if(isUpdated(oldWkuEClassDatas, newWkuEClassDatas, updatedEClassDatas)) {
                    Log.i("WKU", "WKUEClassService.handler / THERE ARE NEW DATAS...");

                    for(int i=0; i<updatedEClassDatas.size(); i++) {
                        for(int j=0; j<updatedEClassDatas.get(i).getWkuEClassDetailNotiDatas().size(); j++) {
                            Log.i("WKU", "UPDATED : " + updatedEClassDatas.get(i).getWkuEClassDetailNotiDatas().get(j).showData());
                            wkuDatabase.insertAlarmEClassDetailNotiData(updatedEClassDatas.get(i).getWkuEClassDetailNotiDatas(), j);
                        }

                        for(int j=0; j<updatedEClassDatas.get(i).getWkuEClassDetailPdsDatas().size(); j++) {
                            Log.i("WKU", "UPDATED : " + updatedEClassDatas.get(i).getWkuEClassDetailPdsDatas().get(j).showData());
                            wkuDatabase.insertAlarmEClassDetailPdsData(updatedEClassDatas.get(i).getWkuEClassDetailPdsDatas(), j);
                        }

                        for(int j=0; j<updatedEClassDatas.get(i).getWkuEClassDetailReportDatas().size(); j++) {
                            Log.i("WKU", "UPDATED : " + updatedEClassDatas.get(i).getWkuEClassDetailReportDatas().get(j).showData());
                            wkuDatabase.insertAlarmEClassDetailReportData(updatedEClassDatas.get(i).getWkuEClassDetailReportDatas(), j);
                        }

                        for(int j=0; j<updatedEClassDatas.get(i).getWkuEClassDetailLectureDatas().size(); j++) {
                            Log.i("WKU", "UPDATED : " + updatedEClassDatas.get(i).getWkuEClassDetailLectureDatas().get(j).showData());
                            wkuDatabase.insertAlarmEClassDetailLectureData(updatedEClassDatas.get(i).getWkuEClassDetailLectureDatas(), j);
                        }

//                        startNotification(updatedEClassDatas.get(0).getWkuEClassDetailNotiDatas().get(0));
                    }
                } else {
                    Log.i("WKU", "WKUEClassService.handler / THERE AREN'T NEW DATAS...");
                }

                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.ECLASS_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("WKU", "WKUAlarmEClassService.onCreate()");

        unregisterRestartAlarm();

        super.onCreate();
        startForeground(1,new Notification());

        mIsRunning = false;
    }

    @Override
    public void onDestroy() {
        Log.i("WKU", "WKUAlarmEClassService.onDestory()");
        registerRestartAlarm();

        super.onDestroy();

        mIsRunning = false;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i("WKU", "WKUAlarmEClassService.onStart()");

        super.onStart(intent, startId);

        mStartId = startId;

        mHandler = new Handler();
        mHandler.postDelayed(this, LOCATION_UPDATE_DELAY);
        mIsRunning = true;
    }

    @Override
    public void run() {
        Log.i("WKU", "WKUAlarmEClassService.run()");
        wkuDatabase = new WKUDatabase(this);
        getScrapingData();

        if(!mIsRunning) {
            return;
        } else {
            alarmFunc();

            mHandler.postDelayed(this, LOCATION_UPDATE_DELAY);
            mIsRunning = true;
        }
    }

    private void alarmFunc() {
        oldWkuEClassDatas = new ArrayList<>();
        newWkuEClassDatas = new ArrayList<>();
        updatedEClassDatas = new ArrayList<>();

        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_ECLASS_MODE, handler, studentNo);
        wkuScrapingEngine.start();
    }

    private void getScrapingData() {
        try {
            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT wkuId, wkuPw, jSessionId, wkuTokenKey, studentNo, isDorm FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
                this.studentNo = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(4), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getEclassDetailData(WKUDatabase wkuDatabase, ArrayList<WKUEClassData> newWkuEClassData) {

        oldWkuEClassDatas = newWkuEClassData;
        updatedEClassDatas = newWkuEClassData;

//        for(int i=0; i<newWkuEClassDatas.size(); i++) {
//            oldWkuEClassDatas.get(i).getWkuEClassDetailNotiDatas().clear();
//            oldWkuEClassDatas.get(i).getWkuEClassDetailPdsDatas().clear();
//            oldWkuEClassDatas.get(i).getWkuEClassDetailReportDatas().clear();
//            oldWkuEClassDatas.get(i).getWkuEClassDetailLectureDatas().clear();
//
//            updatedEClassDatas.get(i).getWkuEClassDetailNotiDatas().clear();
//            updatedEClassDatas.get(i).getWkuEClassDetailPdsDatas().clear();
//            updatedEClassDatas.get(i).getWkuEClassDetailReportDatas().clear();
//            updatedEClassDatas.get(i).getWkuEClassDetailLectureDatas().clear();
//        }

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT eclass_detail_noti_id, author, date, title, file, hit, eclass_id FROM wkuEClassDetailNotiData", null);

        while (cursor.moveToNext()) {

            boolean file_flag = false;

            if(cursor.getString(4).equals("")) {
                file_flag = true;
            }

            oldWkuEClassDatas.get(cursor.getInt(6)).getWkuEClassDetailNotiDatas().add(new WKUEClassDetailNotiData(0, cursor.getString(1), cursor.getString(2), cursor.getString(3), file_flag, cursor.getString(5), 0));
        }

        cursor = wkuDatabase.getDB().rawQuery("SELECT eclass_detail_pds_id, title, file, size, date, author, hit, eclass_id FROM wkuEClassDetailPdsData", null);

        while (cursor.moveToNext()) {
            oldWkuEClassDatas.get(cursor.getInt(7)).getWkuEClassDetailPdsDatas().add(new WKUEClassDetailPdsData(0, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), 0));
        }

        cursor = wkuDatabase.getDB().rawQuery("SELECT eclass_detail_report_id, title, date, grade, submit, eclass_id FROM wkuEClassDetailReportData", null);

        while (cursor.moveToNext()) {
            boolean submit_flag = false;

            if(cursor.getString(4).equals("")) {
                submit_flag = true;
            }

            oldWkuEClassDatas.get(cursor.getInt(5)).getWkuEClassDetailReportDatas().add(new WKUEClassDetailReportData(0, cursor.getString(1), cursor.getString(2), cursor.getString(3),submit_flag, 0));
        }

        cursor = wkuDatabase.getDB().rawQuery("SELECT eclass_detail_lecture_id, week, title, eclass_id FROM wkuEClassDetailLectureData", null);

        while (cursor.moveToNext()) {
            oldWkuEClassDatas.get(cursor.getInt(3)).getWkuEClassDetailLectureDatas().add(new WKUEClassDetailLectureData(0, cursor.getString(1), cursor.getString(2), 0));
        }

    }

    private boolean isUpdated(ArrayList<WKUEClassData> oldWkuEClassDatas, ArrayList<WKUEClassData> newWkuEClassDatas, ArrayList<WKUEClassData> updatedWkuEClassDatas) {
        boolean IS_UPDATED_FLAG = false;

        for(int w=0; w<newWkuEClassDatas.size(); w++) {
            // NOTI DATA......
            for(int i=0; i<newWkuEClassDatas.get(w).getWkuEClassDetailNotiDatas().size(); i++) {
                IS_UPDATED_FLAG = false;

                if(oldWkuEClassDatas.get(w).getWkuEClassDetailNotiDatas().isEmpty()) {
                    IS_UPDATED_FLAG = true;

                } else {
                    for(int j=0; j<oldWkuEClassDatas.get(w).getWkuEClassDetailNotiDatas().size(); j++) {
//                        Log.i("WKU", "WKUEClassService.isUpdated.Notidata / w : " + w +  ", i : " + i + ", j : " + j);

                        if(newWkuEClassDatas.get(w).getWkuEClassDetailNotiDatas().get(i).getId().equals(oldWkuEClassDatas.get(w).getWkuEClassDetailNotiDatas().get(j).getId())) {
//                            Log.i("WKU", "WKUEClassService.isUpdated.Notidata / new[" + i + "] is already existed...");
                            break;

                        } else if(j == oldWkuEClassDatas.get(w).getWkuEClassDetailNotiDatas().size() - 1) {
//                            Log.i("WKU", "WKUEClassService.isUpdated.Notidata / new[" + i + "] is new");
                            IS_UPDATED_FLAG = true;
                        }
                    }
                }

                if(IS_UPDATED_FLAG) {
                    updatedWkuEClassDatas.get(w).getWkuEClassDetailNotiDatas().add(newWkuEClassDatas.get(w).getWkuEClassDetailNotiDatas().get(i));
                }
            }
        }

        for(int w=0; w<newWkuEClassDatas.size(); w++) {
            // PDS DATA......
            for(int i=0; i<newWkuEClassDatas.get(w).getWkuEClassDetailPdsDatas().size(); i++) {
                IS_UPDATED_FLAG = false;

                if(oldWkuEClassDatas.get(w).getWkuEClassDetailPdsDatas().isEmpty()) {
                    IS_UPDATED_FLAG = true;
                } else {
                    for(int j=0; j<oldWkuEClassDatas.get(w).getWkuEClassDetailPdsDatas().size(); j++) {
//                        Log.i("WKU", "WKUEClassService.isUpdated.PdsData / i : " + i + " j : " + j);

                        if(newWkuEClassDatas.get(w).getWkuEClassDetailPdsDatas().get(i).getId().equals(oldWkuEClassDatas.get(w).getWkuEClassDetailPdsDatas().get(j).getId())) {
//                            Log.i("WKU", "WKUEClassService.isUpdated.PdsData / new[" + i + "] is already existed...");
                            break;

                        } else if(j == oldWkuEClassDatas.get(w).getWkuEClassDetailPdsDatas().size() - 1){
//                            Log.i("WKU", "WKUEClassService.isUpdated.PdsData / new[" + i + "] is new");
                            IS_UPDATED_FLAG = true;
                        }
                    }
                }

                if(IS_UPDATED_FLAG) {
                    updatedWkuEClassDatas.get(w).getWkuEClassDetailPdsDatas().add(newWkuEClassDatas.get(w).getWkuEClassDetailPdsDatas().get(i));
                }
            }
        }

        for(int w=0; w<newWkuEClassDatas.size(); w++) {
            // REPORT DATA......
            for(int i=0; i<newWkuEClassDatas.get(w).getWkuEClassDetailReportDatas().size(); i++) {
                IS_UPDATED_FLAG = false;

                if(oldWkuEClassDatas.get(w).getWkuEClassDetailReportDatas().isEmpty()) {
                    IS_UPDATED_FLAG = true;
                } else {
                    for(int j=0; j<oldWkuEClassDatas.get(w).getWkuEClassDetailReportDatas().size(); j++) {
//                        Log.i("WKU", "WKUEClassService.isUpdated.ReportData / i : " + i + " j : " + j);

                        if(newWkuEClassDatas.get(w).getWkuEClassDetailReportDatas().get(i).getId().equals(oldWkuEClassDatas.get(w).getWkuEClassDetailReportDatas().get(j).getId())) {
//                            Log.i("WKU", "WKUEClassService.isUpdated.ReportData / new[" + i + "] is already existed...");
                            break;

                        } else if(j == oldWkuEClassDatas.get(w).getWkuEClassDetailReportDatas().size() - 1){
//                            Log.i("WKU", "WKUEClassService.isUpdated.ReportData / new[" + i + "] is new");
                            IS_UPDATED_FLAG = true;
                        }
                    }
                }

                if(IS_UPDATED_FLAG) {
                    updatedWkuEClassDatas.get(w).getWkuEClassDetailReportDatas().add(newWkuEClassDatas.get(w).getWkuEClassDetailReportDatas().get(i));
                }
            }
        }

        for(int w=0; w<newWkuEClassDatas.size(); w++) {
            // LECTURE DATA......
            for(int i=0; i<newWkuEClassDatas.get(w).getWkuEClassDetailLectureDatas().size(); i++) {
                IS_UPDATED_FLAG = false;

                if(oldWkuEClassDatas.get(w).getWkuEClassDetailLectureDatas().isEmpty()) {
                    IS_UPDATED_FLAG = true;
                } else {
                    for(int j=0; j<oldWkuEClassDatas.get(w).getWkuEClassDetailLectureDatas().size(); j++) {
//                        Log.i("WKU", "WKUEClassService.isUpdated.LectureData / i : " + i + " j : " + j);

                        if(newWkuEClassDatas.get(w).getWkuEClassDetailLectureDatas().get(i).getId().equals(oldWkuEClassDatas.get(w).getWkuEClassDetailLectureDatas().get(j).getId())) {
//                            Log.i("WKU", "WKUEClassService.isUpdated.LectureData / new[" + i + "] is already existed...");
                            break;

                        } else if(j == oldWkuEClassDatas.get(w).getWkuEClassDetailLectureDatas().size() - 1){
//                            Log.i("WKU", "WKUEClassService.isUpdated.LectureData / new[" + i + "] is new");
                            IS_UPDATED_FLAG = true;
                        }
                    }
                }

                if(IS_UPDATED_FLAG) {
                    updatedWkuEClassDatas.get(w).getWkuEClassDetailLectureDatas().add(newWkuEClassDatas.get(w).getWkuEClassDetailLectureDatas().get(i));
                }
            }
        }

        boolean flag = false;

        for(int i=0; i<updatedWkuEClassDatas.size(); i++) {
            if(!updatedWkuEClassDatas.get(i).getWkuEClassDetailNotiDatas().isEmpty()) flag = true;
            if(!updatedWkuEClassDatas.get(i).getWkuEClassDetailReportDatas().isEmpty()) flag = true;
            if(!updatedWkuEClassDatas.get(i).getWkuEClassDetailPdsDatas().isEmpty()) flag = true;
            if(!updatedWkuEClassDatas.get(i).getWkuEClassDetailLectureDatas().isEmpty()) flag = true;
        }

        return flag;
    }

    private void registerRestartAlarm() {
        Log.i("WKU", "WKUAlarmEClassService.registerRestartAlarm()");

        Intent intent = new Intent(WKUAlarmEClassService.this, WKUAlarmEClassImmortalService.class);
        intent.setAction(WKUAlarmEClassImmortalService.ACTION_RESTART_WKUALARMECLASSSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(WKUAlarmEClassService.this, 0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += REBOOT_DELAY_TIMER;

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, REBOOT_DELAY_TIMER, sender);
    }

    private void unregisterRestartAlarm() {
        Log.i("WKU", "WKUAlarmEClassService.unregisterRestartAlarm()");

        Intent intent = new Intent(WKUAlarmEClassService.this, WKUAlarmEClassImmortalService.class);
        intent.setAction(WKUAlarmEClassImmortalService.ACTION_RESTART_WKUALARMECLASSSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(WKUAlarmEClassService.this, 0, intent, 0);

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    public void startNotification(WKUAlarmData wkuAlarmData) {
        Resources res = getResources();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notify");
        Intent intent = new Intent(getApplicationContext(), WKUMainActivity.class);
        intent.putExtra("curPage", "alarm");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setContentTitle("새로운 글이 등록되었습니다.")
                .setContentText(wkuAlarmData.getTitle())
                .setTicker("상태바 한줄 메시지")
                .setSmallIcon(R.mipmap.wku_icon)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.wku_icon_blue))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());
    }
}
