package com.example.yun.wku;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;

public class WKUWidgetProvider extends AppWidgetProvider {

    static String[] arrayDayOfWeek = {"일", "월", "화", "수", "목", "금", "토"};

    static String[] arraySubject = new String[10];
    static String[] arrayPlace = new String[10];

    static int preset = 0;

    private static final String prevBtn = "prevBtnTag";
    private static final String nextBtn = "nextBtnTag";

    private static final int DIFF_DAY_OF_WEEK = -2;

    static void UpdateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        Log.i("WKU", "UpdateWidget");
        WKUDatabase wkuDatabase = new WKUDatabase(context);

        Calendar cal = Calendar.getInstance();

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_wku);

        Intent prevIntent = new Intent(context, WKUWidgetProvider.class);
        prevIntent.setAction(prevBtn);
        prevIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(context, widgetId, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(context, WKUWidgetProvider.class);
        nextIntent.setAction(nextBtn);
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, widgetId, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.widget_prev, prevPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.widget_next, nextPendingIntent);

        Log.i("WKU", "arrayDatOfWeek : " + arrayDayOfWeek[(cal.get(Calendar.DAY_OF_WEEK) - 1 + preset) % arrayDayOfWeek.length]);
        remoteViews.setTextViewText(R.id.widget_day_of_week, arrayDayOfWeek[(cal.get(Calendar.DAY_OF_WEEK) - 1 + preset) % arrayDayOfWeek.length]);

        for(int i=0; i<10; i++) {
            arraySubject[i] = "";
            arrayPlace[i] = "";
        }

        getScheduleData((cal.get(Calendar.DAY_OF_WEEK) + DIFF_DAY_OF_WEEK + preset), wkuDatabase);
        updateSchedule(remoteViews);

        appWidgetManager.updateAppWidget(widgetId, remoteViews);
}

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i("WKU", "onUpdate!!! : length : " + appWidgetIds.length);
        for(int i=0; i<appWidgetIds.length; i++) {
            UpdateWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (prevBtn.equals(intent.getAction())) {
            int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            preset = presetController(false, preset);
            UpdateWidget(context, AppWidgetManager.getInstance(context), id);
        } else if (nextBtn.equals(intent.getAction())) {
            int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            preset = presetController(true, preset);
            UpdateWidget(context, AppWidgetManager.getInstance(context), id);
        }
    }

    private int presetController(boolean flag, int preset) {
        if(flag) {
            // next
            preset++;
            if(preset >= 7) preset = 0;
        } else {
            // prev
            preset--;
            if(preset < 0) preset = 6;
        }

        return preset;
    }

    private static void updateSchedule(RemoteViews remoteViews) {
        remoteViews.setTextViewText(R.id.widget_subject_1, arraySubject[0]);
        remoteViews.setTextViewText(R.id.widget_place_1, arrayPlace[0]);

        remoteViews.setTextViewText(R.id.widget_subject_2, arraySubject[1]);
        remoteViews.setTextViewText(R.id.widget_place_2, arrayPlace[1]);

        remoteViews.setTextViewText(R.id.widget_subject_3, arraySubject[2]);
        remoteViews.setTextViewText(R.id.widget_place_3, arrayPlace[2]);

        remoteViews.setTextViewText(R.id.widget_subject_4, arraySubject[3]);
        remoteViews.setTextViewText(R.id.widget_place_4, arrayPlace[3]);

        remoteViews.setTextViewText(R.id.widget_subject_5, arraySubject[4]);
        remoteViews.setTextViewText(R.id.widget_place_5, arrayPlace[4]);

        remoteViews.setTextViewText(R.id.widget_subject_6, arraySubject[5]);
        remoteViews.setTextViewText(R.id.widget_place_6, arrayPlace[5]);

        remoteViews.setTextViewText(R.id.widget_subject_7, arraySubject[6]);
        remoteViews.setTextViewText(R.id.widget_place_7, arrayPlace[6]);

        remoteViews.setTextViewText(R.id.widget_subject_8, arraySubject[7]);
        remoteViews.setTextViewText(R.id.widget_place_8, arrayPlace[7]);

        remoteViews.setTextViewText(R.id.widget_subject_9, arraySubject[8]);
        remoteViews.setTextViewText(R.id.widget_place_9, arrayPlace[8]);

        remoteViews.setTextViewText(R.id.widget_subject_10, arraySubject[9]);
        remoteViews.setTextViewText(R.id.widget_place_10, arrayPlace[9]);
    }

    private static void getScheduleData(int dayOfWeek, WKUDatabase wkuDatabase) {
        Log.i("WKU", "getScheduleData. dayOfWeek : " + dayOfWeek);
        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT subject, place, dayOfWeek, period FROM wkuScheduleData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "getScheduleData : " + cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3));
            if((dayOfWeek % 7) == Integer.parseInt(cursor.getString(2))) {
//                Log.i("WKU", "WKUWidgetProvider -> uploadScheduleDataFromSQLite -> wkuScheduleData -> 스케줄번호 : " + cursor.getString(0) + " 과목 : " + cursor.getString(1) + " 강의실 : " + cursor.getString(2) + " 요일 : " + cursor.getString(3) + " 교시 : " + cursor.getString(4));
                arraySubject[Integer.parseInt(cursor.getString(3))] = cursor.getString(0);
                arrayPlace[Integer.parseInt(cursor.getString(3))] = cursor.getString(1);
            }
        }
    }
}
