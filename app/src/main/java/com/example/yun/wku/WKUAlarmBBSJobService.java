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
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class WKUAlarmBBSJobService extends Service implements Runnable {
    private static final int REBOOT_DELAY_TIMER = 10 * 1000;
    private static final int LOCATION_UPDATE_DELAY = 1000 * 60 * 60 * 12;
//    private static final int LOCATION_UPDATE_DELAY = 1000 * 30;

    private Handler mHandler;
    private boolean mIsRunning;
    private int mStartId = 0;

    private WKUScrapingEngine wkuScrapingEngine;
    private WKUDatabase wkuDatabase;

    private String wkuId, wkuPw;
    private String jSessionId = "";
    private String wkuTokenKey = "";

    private ArrayList<WKUBBSData> oldWkuBbsJobDatas;
    private ArrayList<WKUBBSData> newWkuBbsJobDatas;
    private ArrayList<WKUBBSData> updatedWkuBbsJobDatas;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {
//
//                getBbsJobData(wkuDatabase);
//                newWkuBbsJobDatas = wkuScrapingEngine.getWkuData().getWkuBbsJobDatas();

                sampleData();

                if(isUpdated(oldWkuBbsJobDatas, newWkuBbsJobDatas, updatedWkuBbsJobDatas)) {
                    Log.i("WKU", "updatedWkuBbsJobDatas.size() : " + updatedWkuBbsJobDatas.size());
                    for(int i=0; i<updatedWkuBbsJobDatas.size(); i++) {
                        Log.i("WKU", updatedWkuBbsJobDatas.get(i).showData());
                        wkuDatabase.insertAlarmBBSData(updatedWkuBbsJobDatas, i);
                    }

                    startNotification(updatedWkuBbsJobDatas.get(0));
                }

                Collections.reverse(updatedWkuBbsJobDatas);

                WKUDBThread wkudbThread = new WKUDBThread(WKUDBThread.BBS_JOB_FLAG, wkuDatabase, wkuScrapingEngine, handler);
                wkudbThread.start();
            }
        }
    };

    private void sampleData() {
        oldWkuBbsJobDatas.add(new WKUBBSData(0, 0, 1, "job - title1", "job - author1", "job - date1", 0, 0, 0));
        oldWkuBbsJobDatas.add(new WKUBBSData(0, 0, 2, "job - title2", "job - author2", "job - date2", 0, 0, 0));
        oldWkuBbsJobDatas.add(new WKUBBSData(0, 0, 3, "job - title3", "job - author3", "job - date3", 0, 0, 0));
        oldWkuBbsJobDatas.add(new WKUBBSData(0, 0, 4, "job - title4", "job - author4", "job - date4", 0, 0, 0));
        oldWkuBbsJobDatas.add(new WKUBBSData(0, 0, 5, "job - title5", "job - author5", "job - date5", 0, 0, 0));

        newWkuBbsJobDatas.add(new WKUBBSData(0, 0, 1, "job - title1", "job - author1", "job - date1", 0, 0, 0));
        newWkuBbsJobDatas.add(new WKUBBSData(0, 0, 2, "job - title2", "job - author2", "job - date2", 0, 0, 0));
        newWkuBbsJobDatas.add(new WKUBBSData(0, 0, 3, "job - title3", "job - author3", "job - date3", 0, 0, 0));
        newWkuBbsJobDatas.add(new WKUBBSData(0, 0, 4, "job - title4", "job - author4", "job - date4", 0, 0, 0));
        newWkuBbsJobDatas.add(new WKUBBSData(0, 0, 5, "job - title5", "job - author5", "job - date5", 0, 0, 0));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        unregisterRestartAlarm();

        super.onCreate();
        startForeground(1,new Notification());

        mIsRunning = false;
    }

    @Override
    public void onDestroy() {
        registerRestartAlarm();

        super.onDestroy();

        mIsRunning = false;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        mStartId = startId;

        mHandler = new Handler();
        mHandler.postDelayed(this, LOCATION_UPDATE_DELAY);
        mIsRunning = true;
    }

    @Override
    public void run() {
        wkuDatabase = new WKUDatabase(this);

        if(!mIsRunning) {
            return;
        } else {
            if(isServiceOn()) {
                alarmFunc();
            }

            mHandler.postDelayed(this, LOCATION_UPDATE_DELAY);
            mIsRunning = true;
        }
    }

    private void alarmFunc() {

        getScrapingData();

        oldWkuBbsJobDatas = new ArrayList<>();
        newWkuBbsJobDatas = new ArrayList<>();
        updatedWkuBbsJobDatas = new ArrayList<>();

        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_BBS_JOB_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, 1);
        wkuScrapingEngine.start();
    }

    private boolean isServiceOn() {
        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT power FROM wkuSettingData WHERE setting_id = " + WKUSettingData.BBS_ALARM_JOB, null);

        int power = 0;

        while(cursor.moveToNext()) {
            power = cursor.getInt(0);
        }

        if(power == 1) {
            return true;
        } else {
            return false;
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

    private void getBbsJobData(WKUDatabase wkuDatabase) {
        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT id, title, author, date, hits, cid FROM wkuBbsJobData", null);

        while (cursor.moveToNext()) {
            oldWkuBbsJobDatas.add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_JOB, Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)), Long.parseLong(cursor.getString(5)), 0));
        }
    }

    private boolean isUpdated(ArrayList<WKUBBSData> oldWkuBbsDatas, ArrayList<WKUBBSData> newWkuBbsDatas, ArrayList<WKUBBSData> updatedWkuBbsDatas) {
        for(int i=0; i<newWkuBbsDatas.size(); i++) {
            boolean IS_UPDATED_FLAG = false;

            for(int j=0; j<oldWkuBbsDatas.size(); j++) {

                if(newWkuBbsDatas.get(i).getIdForComp().equals(oldWkuBbsDatas.get(j).getIdForComp())) {
                    break;

                } else if(j == oldWkuBbsDatas.size() - 1){
                    IS_UPDATED_FLAG = true;
                }
            }

            if(IS_UPDATED_FLAG) {
                updatedWkuBbsDatas.add(newWkuBbsDatas.get(i));
            }
        }

        if(!updatedWkuBbsDatas.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void registerRestartAlarm() {

        Intent intent = new Intent(WKUAlarmBBSJobService.this, WKUAlarmBBSJobImmortalService.class);
        intent.setAction(WKUAlarmBBSJobImmortalService.ACTION_RESTART_WKUALARMBBSJOBSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(WKUAlarmBBSJobService.this, 0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += REBOOT_DELAY_TIMER;

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, REBOOT_DELAY_TIMER, sender);
    }

    private void unregisterRestartAlarm() {

        Intent intent = new Intent(WKUAlarmBBSJobService.this, WKUAlarmBBSJobImmortalService.class);
        intent.setAction(WKUAlarmBBSJobImmortalService.ACTION_RESTART_WKUALARMBBSJOBSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(WKUAlarmBBSJobService.this, 0, intent, 0);

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
