package com.example.yun.wku;

import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WKUSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;

    private ImageView wkuLogoImg;
    private ProgressBar loadingBar;

    private DrawerLayout settingDrawer;
    private LinearLayout settingRightDrawer;

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
    private String isDorm = "";

    private ListView settingList;
    private ArrayList<WKUSettingData> arrayList;
    private SettingAdapter settingAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkusetting);

        prevBtn = findViewById(R.id.setting_prev_btn);
        wkuLogoBtn = findViewById(R.id.setting_wkulogo_btn);
        bookmarkBtn = findViewById(R.id.setting_bookmark_btn);

        wkuLogoImg = findViewById(R.id.setting_wkulogo_img);
        loadingBar = findViewById(R.id.setting_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        settingDrawer = findViewById(R.id.setting_drawer);
        settingRightDrawer = findViewById(R.id.setting_rightDrawer);

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
        initLists();

        wkuDatabase = new WKUDatabase(this);

        getIDPWData();
        getSettingData();
    }

    private void initLists() {
        settingList = findViewById(R.id.setting_listview);

        arrayList = new ArrayList<>();

        settingAdapter = new SettingAdapter(this, R.layout.list_wkusetting, arrayList);
        settingList.setAdapter(settingAdapter);
    }

    private void getSettingData() {
        arrayList.clear();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT setting_id, title, detail, power FROM wkuSettingData", null);

        WKUSettingData setting;

        while(cursor.moveToNext()) {
            boolean power_flag = false;

            if(cursor.getInt(3) == 1) {
                power_flag = true;
            }
            setting = new WKUSettingData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), power_flag);
            arrayList.add(setting);
        }

        settingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_prev_btn:
                finish();
                break;
            case R.id.setting_wkulogo_btn:
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;
            case R.id.setting_bookmark_btn:
                settingDrawer.openDrawer(settingRightDrawer);
                break;


            case R.id.bookmark_attend_menu :
                settingDrawer.closeDrawer(settingRightDrawer);
                finish();
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                settingDrawer.closeDrawer(settingRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                settingDrawer.closeDrawer(settingRightDrawer);
                finish();
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                settingDrawer.closeDrawer(settingRightDrawer);
                finish();
                Intent scoreIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(scoreIntent);
                break;

            case R.id.bookmark_dorm_menu :
                settingDrawer.closeDrawer(settingRightDrawer);
                if(isDorm.equals("YES")) {
                    finish();
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                settingDrawer.closeDrawer(settingRightDrawer);
                Toast.makeText(this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                settingDrawer.closeDrawer(settingRightDrawer);
                break;

            case R.id.bookmark_complain_menu :
                settingDrawer.closeDrawer(settingRightDrawer);
                Toast.makeText(this, "준비중입니다.", Toast.LENGTH_LONG).show();
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

class SettingAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<WKUSettingData> arrayList;
    private int layout;
    private WKUDatabase wkuDatabase;

    public SettingAdapter(Context context, int layout, ArrayList<WKUSettingData> arrayList) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.layout = layout;
        this.wkuDatabase = new WKUDatabase(context);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        TextView title = convertView.findViewById(R.id.setting_title);
        TextView detail = convertView.findViewById(R.id.setting_detail);
        Switch power = convertView.findViewById(R.id.setting_power);

        title.setText(arrayList.get(position).getTitle());
        detail.setText(arrayList.get(position).getDetail());
        power.setChecked(arrayList.get(position).isPower());

        power.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                int power_value = 0;
                if(b) {
                    power_value = 1;
                }
                updateSettingPower((position+1), power_value);

                toastDisplay(position+1, b);
            }
        });

        return convertView;
    }

    private void updateSettingPower(int id, int power_value) {
        wkuDatabase.getDB().execSQL("UPDATE wkuSettingData SET power = " + power_value + " WHERE setting_id = " + id);
    }

    private void toastDisplay(int id, boolean power) {
        if(id == WKUSettingData.BBS_ALARM_NOTI) {
            if(power) { Toast.makeText(context, "BBS 공지사항 관련 알림이 설정되었습니다.", Toast.LENGTH_SHORT).show(); }
            else { Toast.makeText(context, "BBS 공지사항 관련 알림이 해제되었습니다.", Toast.LENGTH_SHORT).show(); }
        } else if(id == WKUSettingData.BBS_ALARM_ROOM) {
            if(power) { Toast.makeText(context, "BBS 원룸,자취,하숙 관련 알림이 설정되었습니다.", Toast.LENGTH_SHORT).show(); }
            else { Toast.makeText(context, "BBS 원룸,자취,하숙 관련 알림이 해제되었습니다.", Toast.LENGTH_SHORT).show(); }
        } else if(id == WKUSettingData.BBS_ALARM_MARKET) {
            if(power) { Toast.makeText(context, "BBS 만물장터 관련 알림이 설정되었습니다.", Toast.LENGTH_SHORT).show(); }
            else { Toast.makeText(context, "BBS 만물장터 관련 알림이 해제되었습니다.", Toast.LENGTH_SHORT).show(); }
        } else if(id == WKUSettingData.BBS_ALARM_JOB) {
            if(power) { Toast.makeText(context, "BBS 아르바이트,구인,구직 관련 알림이 설정되었습니다.", Toast.LENGTH_SHORT).show(); }
            else { Toast.makeText(context, "BBS 아르바이트,구인,구직 관련 알림이 해제되었습니다.", Toast.LENGTH_SHORT).show(); }
        }
    }
}
