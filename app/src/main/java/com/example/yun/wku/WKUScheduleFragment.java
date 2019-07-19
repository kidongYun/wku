package com.example.yun.wku;

import android.animation.ObjectAnimator;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WKUScheduleFragment extends Fragment implements View.OnClickListener{
    private final int SCHEDULES_MAX_X = 5;
    private final int SCHEDULES_MAX_Y = 10;

    private final int SCHEDULE_SUBJECT = 0;
    private final int SCHEDULE_DETAIL_NULL = -100;

    private boolean SCHEDULE_DETAIL_FLAG = false;
    private boolean IS_EXIST = false;

    private int SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
    private int SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;

    private TextView scheduleDetailSubject;
    private TextView scheduleDetailSubjectType;
    private TextView scheduleDetailPlace;
    private TextView scheduleDetailClassNo;
    private TextView scheduleDetailCredit;

    private LinearLayout schedulesId[][];
    private ArrayList<WKUScheduleData> schedules;
    private LinearLayout scheduleDetailBox;

    private WKUDatabase wkuDatabase;

    public WKUScheduleFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wkuschedule, container, false);

        schedulesId = new LinearLayout[SCHEDULES_MAX_X][SCHEDULES_MAX_Y];
        schedules = new ArrayList<>();
        scheduleDetailBox = rootView.findViewById(R.id.schedule_detail_box);

        scheduleDetailSubject = rootView.findViewById(R.id.schedule_detail_subject);
        scheduleDetailSubjectType = rootView.findViewById(R.id.schedule_detail_subjectType);
        scheduleDetailPlace = rootView.findViewById(R.id.schedule_detail_place);
        scheduleDetailClassNo = rootView.findViewById(R.id.schedule_detail_classNo);
        scheduleDetailCredit = rootView.findViewById(R.id.schedule_detail_credit);

        wkuDatabase = new WKUDatabase(getActivity());

        initSchedule(rootView);
        getScheduleData();
        syncScheduleToDisplay();

        return rootView;
    }

    private void initSchedule(View rootView) {
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_1] = rootView.findViewById(R.id.schedule00);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_2] = rootView.findViewById(R.id.schedule01);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_3] = rootView.findViewById(R.id.schedule02);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_4] = rootView.findViewById(R.id.schedule03);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_5] = rootView.findViewById(R.id.schedule04);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_6] = rootView.findViewById(R.id.schedule05);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_7] = rootView.findViewById(R.id.schedule06);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_8] = rootView.findViewById(R.id.schedule07);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_9] = rootView.findViewById(R.id.schedule08);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_10] = rootView.findViewById(R.id.schedule09);

        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_1] = rootView.findViewById(R.id.schedule10);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_2] = rootView.findViewById(R.id.schedule11);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_3] = rootView.findViewById(R.id.schedule12);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_4] = rootView.findViewById(R.id.schedule13);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_5] = rootView.findViewById(R.id.schedule14);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_6] = rootView.findViewById(R.id.schedule15);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_7] = rootView.findViewById(R.id.schedule16);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_8] = rootView.findViewById(R.id.schedule17);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_9] = rootView.findViewById(R.id.schedule18);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_10] = rootView.findViewById(R.id.schedule19);

        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_1] = rootView.findViewById(R.id.schedule20);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_2] = rootView.findViewById(R.id.schedule21);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_3] = rootView.findViewById(R.id.schedule22);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_4] = rootView.findViewById(R.id.schedule23);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_5] = rootView.findViewById(R.id.schedule24);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_6] = rootView.findViewById(R.id.schedule25);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_7] = rootView.findViewById(R.id.schedule26);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_8] = rootView.findViewById(R.id.schedule27);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_9] = rootView.findViewById(R.id.schedule28);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_10] = rootView.findViewById(R.id.schedule29);

        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_1] = rootView.findViewById(R.id.schedule30);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_2] = rootView.findViewById(R.id.schedule31);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_3] = rootView.findViewById(R.id.schedule32);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_4] = rootView.findViewById(R.id.schedule33);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_5] = rootView.findViewById(R.id.schedule34);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_6] = rootView.findViewById(R.id.schedule35);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_7] = rootView.findViewById(R.id.schedule36);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_8] = rootView.findViewById(R.id.schedule37);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_9] = rootView.findViewById(R.id.schedule38);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_10] = rootView.findViewById(R.id.schedule39);

        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_1] = rootView.findViewById(R.id.schedule40);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_2] = rootView.findViewById(R.id.schedule41);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_3] = rootView.findViewById(R.id.schedule42);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_4] = rootView.findViewById(R.id.schedule43);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_5] = rootView.findViewById(R.id.schedule44);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_6] = rootView.findViewById(R.id.schedule45);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_7] = rootView.findViewById(R.id.schedule46);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_8] = rootView.findViewById(R.id.schedule47);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_9] = rootView.findViewById(R.id.schedule48);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_10] = rootView.findViewById(R.id.schedule49);

        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_1].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_2].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_3].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_4].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_5].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_6].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_7].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_8].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_9].setOnClickListener(this);
        schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_10].setOnClickListener(this);

        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_1].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_2].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_3].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_4].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_5].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_6].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_7].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_8].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_9].setOnClickListener(this);
        schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_10].setOnClickListener(this);

        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_1].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_2].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_3].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_4].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_5].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_6].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_7].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_8].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_9].setOnClickListener(this);
        schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_10].setOnClickListener(this);

        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_1].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_2].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_3].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_4].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_5].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_6].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_7].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_8].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_9].setOnClickListener(this);
        schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_10].setOnClickListener(this);

        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_1].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_2].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_3].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_4].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_5].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_6].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_7].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_8].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_9].setOnClickListener(this);
        schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_10].setOnClickListener(this);
    }

    public void syncScheduleToDisplay() {
        Log.i("WKU", "schedules.size() : " + schedules.size());
        for (int i = 0; i < schedules.size(); i++) {
            TextView subjectTV = (TextView) schedulesId[schedules.get(i).getDayOfWeek()][schedules.get(i).getPeriod()].getChildAt(SCHEDULE_SUBJECT);

            subjectTV.setText(schedules.get(i).getSubject());
        }
    }

    public void getScheduleData() {
        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT * FROM wkuScheduleData", null);

        WKUScheduleData wkuScheduleData;

        while(cursor.moveToNext()) {
//            Log.i("WKU", "스케줄번호 : " + cursor.getString(0) + " 과목 : " + cursor.getString(1) + " 강의실 : " + cursor.getString(2) + " 요일 : " + cursor.getString(3) + " 교시 : " + cursor.getString(4) + " 구분 : " + cursor.getString(5) + " 분반 : " + cursor.getString(6) + " 학점 : " + cursor.getString(7));
            wkuScheduleData = new WKUScheduleData(cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            schedules.add(wkuScheduleData);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.schedule00 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule00");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_1)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_1]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                    }

                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_1) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_1]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_1)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_1]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule01 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule01");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_2)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_2]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                    }

                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_2) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_2]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_2)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_2]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule02 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule02");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_3)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_3]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_3) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_3]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_3)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_3]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule03 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule03");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_4)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_4]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_4) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_4]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_4)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_4]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule04 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule04");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_5)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_5]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_5) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_5]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_5)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_5]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule05 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule05");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_6)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_6]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_6) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_6]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_6)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_6]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule06 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule06");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_7)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_7]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_7) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_7]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_7)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_7]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule07 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule07");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_8)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_8]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_8) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_8]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_8)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_8]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule08 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule08");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_9)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_9]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_9) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_9]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_9)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_9]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule09 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule09");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_10)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_10]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.MON && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_10) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_10]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.MON, WKUScheduleData.PERIOD_10)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.MON][WKUScheduleData.PERIOD_10]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.MON;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule10 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule10");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_1)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_1]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_1) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_1]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_1)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_1]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule11 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule11");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_2)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_2]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_2) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_2]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_2)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_2]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule12 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule12");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_3)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_3]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_3) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_3]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_3)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_3]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule13 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule13");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_4)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_4]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_4) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_4]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_4)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_4]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule14 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule14");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_5)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_5]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_5) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_5]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_5)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_5]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule15 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule15");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_6)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_6]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_6) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_6]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_6)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_6]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule16 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule16");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_7)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_7]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_7) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_7]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_7)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_7]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule17 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule17");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_8)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_8]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_8) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_8]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_8)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_8]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule18 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule18");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_9)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_9]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_9) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_9]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_9)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_9]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule19 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule19");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_10)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_10]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.TUE && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_10) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_10]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.TUE, WKUScheduleData.PERIOD_10)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.TUE][WKUScheduleData.PERIOD_10]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.TUE;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule20 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule20");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_1)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_1]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_1) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_1]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_1)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_1]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule21 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule21");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_2)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_2]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_2) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_2]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_2)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_2]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule22 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule22");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_3)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_3]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_3) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_3]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_3)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_3]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule23 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule23");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_4)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_4]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_4) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_4]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_4)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_4]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule24 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule24");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_5)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_5]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_5) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_5]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_5)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_5]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule25 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule25");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_6)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_6]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_6) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_6]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_6)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_6]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule26 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule26");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_7)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_7]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_7) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_7]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_7)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_7]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule27 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule27");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_8)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_8]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_8) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_8]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_8)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_8]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule28 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule28");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_9)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_9]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_9) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_9]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_9)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_9]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule29 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule29");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_10)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_10]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.WED && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_10) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_10]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.WED, WKUScheduleData.PERIOD_10)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.WED][WKUScheduleData.PERIOD_10]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.WED;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule30 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule30");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_1)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_1]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_1) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_1]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_1)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_1]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule31 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule31");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if (setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_2)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_2]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_2) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_2]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_2)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_2]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule32 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule32");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_3)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_3]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_3) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_3]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_3)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_3]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule33 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule33");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_4)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_4]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_4) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_4]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_4)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_4]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule34 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule34");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_5)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_5]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_5) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_5]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_5)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_5]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule35 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule35");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_6)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_6]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_6) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_6]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_6)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_6]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule36 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule36");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_7)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_7]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_7) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_7]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_7)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_7]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule37 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule37");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if (setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_8)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_8]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_8) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_8]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_8)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_8]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;

                        }
                    }
                }
                break;

            case R.id.schedule38 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule38");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_9)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_9]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_9) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_9]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_9)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_9]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule39 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule39");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_10)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_10]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.THU && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_10) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_10]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.THU, WKUScheduleData.PERIOD_10)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.THU][WKUScheduleData.PERIOD_10]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.THU;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule40 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule40");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_1)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_1]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_1) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_1]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_1)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_1]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_1;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule41 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule41");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_2)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_2]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_2) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_2]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_2)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_2]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_2;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule42 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule42");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_3)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_3]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_3) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_3]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_3)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_3]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_3;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule43 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule43");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_4)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_4]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_4) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_4]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_4)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_4]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_4;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule44 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule44");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_5)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_5]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_5) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_5]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_5)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_5]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_5;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule45 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule45");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_6)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_6]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_6) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_6]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_6)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_6]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_6;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule46 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule46");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_7)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_7]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_7) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_7]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_7)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_7]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_7;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule47 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule47");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_8)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_8]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_8) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_8]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_8)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_8]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_8;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule48 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule48");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_9)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_9]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_9) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_9]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_9)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_9]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_9;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;

            case R.id.schedule49 :
                Log.i("WKU", "WKUScheduleFragment -> onClick() -> schedule49");

                if(SCHEDULE_DETAIL_FLAG == false) {
                    if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_10)) {
                        onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_10]);

                        SCHEDULE_DETAIL_FLAG = true;
                        SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                        SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                    }
                } else {
                    if(SCHEDULE_DETAIL_DAYOFWEEK == WKUScheduleData.FRI && SCHEDULE_DETAIL_PERIOD == WKUScheduleData.PERIOD_10) {
                        offScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_10]);
                        SCHEDULE_DETAIL_FLAG = false;

                        SCHEDULE_DETAIL_DAYOFWEEK = SCHEDULE_DETAIL_NULL;
                        SCHEDULE_DETAIL_PERIOD = SCHEDULE_DETAIL_NULL;
                    } else {
                        if(setScheduleDetailContent(WKUScheduleData.FRI, WKUScheduleData.PERIOD_10)) {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            onScheduleDetailBox(schedulesId[WKUScheduleData.FRI][WKUScheduleData.PERIOD_10]);

                            SCHEDULE_DETAIL_DAYOFWEEK = WKUScheduleData.FRI;
                            SCHEDULE_DETAIL_PERIOD = WKUScheduleData.PERIOD_10;
                        } else {
                            offScheduleDetailBox(schedulesId[SCHEDULE_DETAIL_DAYOFWEEK][SCHEDULE_DETAIL_PERIOD]);
                            SCHEDULE_DETAIL_FLAG = false;
                        }
                    }
                }
                break;
        }
    }

    private boolean setScheduleDetailContent(int dayOfWeek, int period) {
        IS_EXIST = false;

        for(int i=0; i<schedules.size(); i++) {
            if(schedules.get(i).getDayOfWeek() == dayOfWeek && schedules.get(i).getPeriod() == period) {
                IS_EXIST = true;

                scheduleDetailSubject.setText(schedules.get(i).getSubject());
                scheduleDetailSubjectType.setText(schedules.get(i).getSubjectType());
                scheduleDetailPlace.setText(schedules.get(i).getPlace());
                scheduleDetailClassNo.setText(schedules.get(i).getClassNo());
                scheduleDetailCredit.setText(schedules.get(i).getCredit());
            }
        }

        return IS_EXIST;
    }

    private void onScheduleDetailBox(LinearLayout layout) {
        layout.setBackgroundColor(Color.parseColor("#33aaaaaa"));
        scheduleDetailBox.setVisibility(View.VISIBLE);
        scheduleDetailBox.setPivotY(400);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(scheduleDetailBox, "scaleY", 0f, 1f);
        animator1.setDuration(500);
        animator1.start();
    }

    private void offScheduleDetailBox(LinearLayout layout) {
        layout.setBackgroundResource(R.drawable.wkuschedulefragment_border);
        scheduleDetailBox.setVisibility(View.VISIBLE);
        scheduleDetailBox.setPivotY(400);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(scheduleDetailBox, "scaleY", 1f, 0f);
        animator2.setDuration(500);
        animator2.start();
    }
}
