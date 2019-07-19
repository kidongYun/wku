package com.example.yun.wku;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WKUAlarmFragment extends Fragment {
    private LinearLayout noAlarmIcon;

    private ListView alarmList;
    private ArrayList<WKUAlarmData> arrayList;
    private AlarmAdapter alarmAdapter;

    private View rootView;

    private WKUDatabase wkuDatabase;

    public WKUAlarmFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wkualarm, container, false);

        noAlarmIcon = rootView.findViewById(R.id.alarm_no_icon);

        arrayList = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(getActivity(), R.layout.list_wkunoti, arrayList);

        alarmList = rootView.findViewById(R.id.alarm_list);

        alarmList.setAdapter(alarmAdapter);
        alarmList.setDivider(null);

        wkuDatabase = new WKUDatabase(getActivity());

        alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateIsClick(position);

                Intent intent = new Intent(getActivity(), WKUBBSDetailActivity.class);
                if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_BBS_NOTI) {
                    intent.putExtra("url", "http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/view.jsp?bucket=5&lpage=1&gid=1115983888724&bid=1115985252888&cid=" + ((WKUBBSData)arrayList.get(position)).getCid() + "&sField=&sKey=");
                } else if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_BBS_ROOM) {
                    intent.putExtra("url", "http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/view.jsp?bucket=13&lpage=1&gid=1115983888724&bid=1203406502138&cid=" + ((WKUBBSData)arrayList.get(position)).getCid() + "&sField=&sKey=");
                } else if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_BBS_MARKET) {
                    intent.putExtra("url", "http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/view.jsp?bucket=7&lpage=1&gid=1115983888724&bid=1115985650747&cid=" + ((WKUBBSData)arrayList.get(position)).getCid() + "&sField=&sKey=");
                } else if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_BBS_JOB) {
                    intent.putExtra("url", "http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/view.jsp?bucket=5&lpage=2&gid=1115983888724&bid=1115985577405&cid=" + ((WKUBBSData)arrayList.get(position)).getCid() + "&sField=&sKey=");
                } else if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_ECLASS_DETAIL_NOTI) {

                } else if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_ECLASS_DETAIL_PDS) {

                } else if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_ECLASS_DETAIL_REPORT) {

                } else if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_ECLASS_DETAIL_LECTURE) {

                }

                startActivity(intent);
                refresh();
            }
        });

        if(wkuDatabase != null) {
            getAlarmData();
        }

        return rootView;
    }

    private void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void updateIsClick(int position) {
        wkuDatabase.getDB().execSQL("UPDATE wkuAlarmData SET isClick = 1 WHERE alarm_id = " + arrayList.get(position).getAlarmId());

//        wkuDatabase.logAlarmData();
        alarmAdapter.notifyDataSetChanged();
    }

    public void getAlarmData() {

        if(arrayList != null) {
            arrayList.clear();
        }

        if(alarmList == null) {
            alarmList = rootView.findViewById(R.id.alarm_list);
        }

        ArrayList<WKUAlarmData> reverseArrayList = new ArrayList<>();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT category, title, date, author, id, hits, cid, file, size, grade, submit, week, isClick, alarm_id FROM wkuAlarmData", null);

        while(cursor.moveToNext()) {
            WKUAlarmData alarm = new WKUAlarmData();

            if(cursor.getInt(0) == WKUAlarmData.CATEGORY_BBS_NOTI) {
                alarm = new WKUBBSData(cursor.getInt(13), WKUAlarmData.CATEGORY_BBS_NOTI, cursor.getInt(4), cursor.getString(1), cursor.getString(3), cursor.getString(2), cursor.getInt(5), cursor.getLong(6), cursor.getInt(12));
            } else if(cursor.getInt(0) == WKUAlarmData.CATEGORY_BBS_ROOM) {
                alarm = new WKUBBSData(cursor.getInt(13), WKUAlarmData.CATEGORY_BBS_ROOM, cursor.getInt(4), cursor.getString(1), cursor.getString(3), cursor.getString(2), cursor.getInt(5), cursor.getLong(6), cursor.getInt(12));
            } else if(cursor.getInt(0) == WKUAlarmData.CATEGORY_BBS_MARKET) {
                alarm = new WKUBBSData(cursor.getInt(13), WKUAlarmData.CATEGORY_BBS_MARKET, cursor.getInt(4), cursor.getString(1), cursor.getString(3), cursor.getString(2), cursor.getInt(5), cursor.getLong(6), cursor.getInt(12));
            } else if(cursor.getInt(0) == WKUAlarmData.CATEGORY_BBS_JOB) {
                alarm = new WKUBBSData(cursor.getInt(13), WKUAlarmData.CATEGORY_BBS_JOB, cursor.getInt(4), cursor.getString(1), cursor.getString(3), cursor.getString(2), cursor.getInt(5), cursor.getLong(6), cursor.getInt(12));
            } else if(cursor.getInt(0) == WKUAlarmData.CATEGORY_ECLASS_DETAIL_NOTI) {
                boolean fileFlag = false;
                if(cursor.getString(7).equals("true")) { fileFlag = true; }

                alarm = new WKUEClassDetailNotiData(cursor.getInt(13), cursor.getString(3), cursor.getString(2), cursor.getString(1), fileFlag, cursor.getString(6), cursor.getInt(12));
            } else if(cursor.getInt(0) == WKUAlarmData.CATEGORY_ECLASS_DETAIL_PDS) {
                alarm = new WKUEClassDetailPdsData(cursor.getInt(13), cursor.getString(1), cursor.getString(7), cursor.getString(8), cursor.getString(2), cursor.getString(3), cursor.getString(5), cursor.getInt(12));
            } else if(cursor.getInt(0) == WKUAlarmData.CATEGORY_ECLASS_DETAIL_REPORT) {
                boolean submitFlag = false;
                if(cursor.getInt(10) == 1) { submitFlag = true; }
                alarm = new WKUEClassDetailReportData(cursor.getInt(13), cursor.getString(1), cursor.getString(2), cursor.getString(9), submitFlag, cursor.getInt(12));
            } else if(cursor.getInt(0) == WKUAlarmData.CATEGORY_ECLASS_DETAIL_LECTURE) {
                alarm = new WKUEClassDetailLectureData(cursor.getInt(13), cursor.getString(11), cursor.getString(1), cursor.getInt(12));
            }

            reverseArrayList.add(alarm);
        }

        // 알림 목록이 없을 때
        if(reverseArrayList.isEmpty()) {
            noAlarmIcon.setVisibility(View.VISIBLE);
            alarmList.setVisibility(View.GONE);
        } else {
            alarmList.setVisibility(View.VISIBLE);
            noAlarmIcon.setVisibility(View.GONE);
        }

        for(int i=0; i<reverseArrayList.size(); i++) {
            arrayList.add(reverseArrayList.get(reverseArrayList.size() - (i+1)));
        }

        alarmAdapter.notifyDataSetChanged();
    }
}

class AlarmAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<WKUAlarmData> arrayList;
    private int layout;

    public AlarmAdapter(Context context, int layout, ArrayList<WKUAlarmData> arrayList) {
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
        return arrayList.get(getCount() - position - 1).getCategory();
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

        // For Click Effect...
        LinearLayout alarmItemLayout = convertView.findViewById(R.id.alarm_item_layout);

        if(arrayList.get(position).getIsClick() == 0) {
            alarmItemLayout.setBackgroundColor(Color.argb(0x15,0x00,0x43, 0x86));
        } else {
            alarmItemLayout.setBackgroundColor(Color.argb(0x15,0xff,0xff, 0xff));
        }

        TextView title = convertView.findViewById(R.id.alarm_title);
        TextView date = convertView.findViewById(R.id.alarm_date);
        TextView author = convertView.findViewById(R.id.alarm_author);

        title.setText(arrayList.get(position).getTitle());

        if(arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_BBS_NOTI || arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_BBS_ROOM || arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_BBS_MARKET || arrayList.get(position).getCategory() == WKUAlarmData.CATEGORY_BBS_JOB) {
            date.setText(((WKUBBSData)arrayList.get(position)).getDate());
            author.setText(((WKUBBSData)arrayList.get(position)).getAuthor());
        }

        return convertView;
    }
}
