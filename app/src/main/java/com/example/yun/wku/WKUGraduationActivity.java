package com.example.yun.wku;

        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Color;
        import android.graphics.PorterDuff;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class WKUGraduationActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout prevBtn;
    private LinearLayout wkuLogoBtn;
    private LinearLayout bookmarkBtn;

    private ImageView wkuLogoImg;
    private ProgressBar loadingBar;

    private DrawerLayout graduationDrawer;
    private LinearLayout graduationRightDrawer;

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

    private ListView graduationCompulsoryGradeList;
    private ArrayList<WKUGraduationCompulsoryGradeData> graduationCompulsoryGradeArrayList;
    private GraduationCompulsoryGradeAdapter graduationCompulsoryGradeAdapter;

    private ListView graduationCompulsorySubjectList;
    private ArrayList<WKUGraduationCompulsorySubjectData> graduationCompulsorySubjectArrayList;
    private GraduationCompulsorySubjectAdapter graduationCompulsorySubjectAdapter;

    private WKUGraduation wkuGraduation;

    private WKUDatabase wkuDatabase;

    private String isDorm = "";
    private String major, studentNo = "";
    private String wkuId, wkuPw;
    private String graduationType;

    private String jSessionId = "";
    private String wkuTokenKey = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wkugraduation);

        prevBtn = findViewById(R.id.graduation_prev_btn);
        wkuLogoBtn = findViewById(R.id.graduation_wkulogo_btn);
        bookmarkBtn = findViewById(R.id.graduation_bookmark_btn);

        wkuLogoImg = findViewById(R.id.graduation_wkulogo_img);
        loadingBar = findViewById(R.id.graduation_loading_bar);
        loadingBar.setIndeterminate(true);
        loadingBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        graduationDrawer = findViewById(R.id.graduation_drawer);
        graduationRightDrawer = findViewById(R.id.graduation_rightDrawer);

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

        wkuDatabase = new WKUDatabase(this);

        getScrapingData();
        wkuGraduation = new WKUGraduation(this, major, Integer.parseInt(studentNo.substring(0, 4)));
        getGraduationData();

        initLists();
        getCompulsoryGradeData();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.graduation_prev_btn:
                finish();
                break;

            case R.id.graduation_wkulogo_btn:
                Intent mainIntent = new Intent(this, WKUMainActivity.class);
                mainIntent.putExtra("curPage", "main");
                startActivity(mainIntent);
                break;

            case R.id.graduation_bookmark_btn:
                graduationDrawer.openDrawer(graduationRightDrawer);
                break;

            case R.id.bookmark_attend_menu :
                graduationDrawer.closeDrawer(graduationRightDrawer);
                finish();
                Intent presentIntent = new Intent(this, WKUAttendActivity.class);
                startActivity(presentIntent);
                break;

            case R.id.bookmark_schedule_menu :
                graduationDrawer.closeDrawer(graduationRightDrawer);
                finish();
                Intent scheduleIntent = new Intent(this, WKUMainActivity.class);
                scheduleIntent.putExtra("curPage", "schedule");
                startActivity(scheduleIntent);
                break;

            case R.id.bookmark_scholar_menu :
                graduationDrawer.closeDrawer(graduationRightDrawer);
                finish();
                Intent scholarIntent = new Intent(this, WKUScholarActivity.class);
                startActivity(scholarIntent);
                break;

            case R.id.bookmark_grade_menu :
                graduationDrawer.closeDrawer(graduationRightDrawer);
                finish();
                Intent scoreIntent = new Intent(this, WKUGradeActivity.class);
                startActivity(scoreIntent);
                break;

            case R.id.bookmark_dorm_menu :
                graduationDrawer.closeDrawer(graduationRightDrawer);
                if(isDorm.equals("YES")) {
                    finish();
                    Intent dormIntent = new Intent(this, WKUDormActivity.class);
                    startActivity(dormIntent);
                } else if(isDorm.equals("NO")) {
                    Toast.makeText(this, "사생이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bookmark_menst_menu :
                graduationDrawer.closeDrawer(graduationRightDrawer);
                Toast.makeText(WKUGraduationActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_setting_menu :
                graduationDrawer.closeDrawer(graduationRightDrawer);
                Toast.makeText(WKUGraduationActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;

            case R.id.bookmark_complain_menu :
                graduationDrawer.closeDrawer(graduationRightDrawer);
                Toast.makeText(WKUGraduationActivity.this, "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void initLists() {
        graduationCompulsoryGradeList = findViewById(R.id.graduation_compulsory_grade_list);
        graduationCompulsorySubjectList = findViewById(R.id.graduation_compulsory_subject_list);

        graduationCompulsoryGradeArrayList = new ArrayList<>();
        graduationCompulsorySubjectArrayList = new ArrayList<>();

        graduationCompulsoryGradeAdapter = new GraduationCompulsoryGradeAdapter(this, R.layout.list_wkucompulsorygrade, graduationCompulsoryGradeArrayList);
        graduationCompulsorySubjectAdapter = new GraduationCompulsorySubjectAdapter(this, R.layout.list_wkucompulsorysubject, graduationCompulsorySubjectArrayList);

        graduationCompulsoryGradeList.setAdapter(graduationCompulsoryGradeAdapter);
        graduationCompulsorySubjectList.setAdapter(graduationCompulsorySubjectAdapter);

        graduationCompulsoryGradeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 : getGraduationCreditListsData(); break;
                    case 1 : getMajorCreditListsData(); break;
                    case 2 : getMajorBasisCreditListsData(); break;
                    case 3 : getGeneralCreditListsData(); break;
                    case 4 : getGeneralCreditListsData(); break;
                    case 5 : getGeneralAffiliationCreditListsData(); break;
                }
            }
        });
    }

    private void getCompulsoryGradeData() {
        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("졸업시 필수 이수 학점", "졸업시 꼭 이수 해야하는 전체 학점", wkuGraduation.getWkuGraduationData().getCurGraduationCredit(), wkuGraduation.getWkuGraduationData().getMinGraduationCredit()));
        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 전공 이수 학점", "최소한 꼭 이수 해야하는 모든 전공 학점", wkuGraduation.getWkuGraduationData().getCurMajorCredit(), wkuGraduation.getWkuGraduationData().getMinMajorCredit()));
        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 기본 전공 이수 학점", "전공 중 선전,응전을 제외한 최소 기본전공 학점", wkuGraduation.getWkuGraduationData().getCurMajorBasisCredit(), wkuGraduation.getWkuGraduationData().getMinMajorBasisCredit()));
        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 교양 이수 학점", "최소한 꼭 들어야 하는 교양 학점", wkuGraduation.getWkuGraduationData().getCurGeneralCredit(), wkuGraduation.getWkuGraduationData().getMinGeneralCredit()));
        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최대 교양 제한 학점", "최대로 들을 수 있는 교양 학점", wkuGraduation.getWkuGraduationData().getCurGeneralCredit(), wkuGraduation.getWkuGraduationData().getMaxGeneralCredit()));
        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 계열 필수 영역 이수 학점", "최소한 꼭 이수 해야하는 계열 필수 영역 학점", wkuGraduation.getWkuGraduationData().getCurGeneralAffiliationCredit(), wkuGraduation.getWkuGraduationData().getMinGeneralAffiliationCredit()));
//        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 언어 영역 이수 학점", "최소한 꼭 이수 해야하는 언어 영역 학점", 0, wkuGraduation.getWkuGraduationData().getMinGeneralLanguageCredit()));
//        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 영어 영역 이수 학점", "최소한 꼭 이수 해야하는 영어 영역 학점", 0, wkuGraduation.getWkuGraduationData().getMinGeneralEnglishCredit()));
//        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 SW 영역 이수 학점", "최소한 꼭 이수 해야하는 SW 영역 학점", 0, wkuGraduation.getWkuGraduationData().getMinGeneralSoftwareCredit()));
//        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 인문소양 영역 이수 학점", "최소한 꼭 이수 해야하는 인문소양 영역 학점", 0, wkuGraduation.getWkuGraduationData().getMinGeneralHumanityCredit()));
//        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 창업 영역 이수 학점", "최소한 꼭 이수 해야하는 창업 영역 학점", 0, wkuGraduation.getWkuGraduationData().getMinGeneralStartUpCredit()));
//        graduationCompulsoryGradeArrayList.add(new WKUGraduationCompulsoryGradeData("최소 창의 영역 이수 학점", "최소한 꼭 이수 해야하는 창의 영역 학점", 0, wkuGraduation.getWkuGraduationData().getMinGeneralCreativityCredit()));

        graduationCompulsoryGradeAdapter.notifyDataSetChanged();
    }

    private void getGraduationCreditListsData() {
        graduationCompulsorySubjectArrayList.clear();

        for(int i=0 ; i<wkuGraduation.getWkuGraduationData().getGraduationCreditLists().size(); i++) {
            graduationCompulsorySubjectArrayList.add(new WKUGraduationCompulsorySubjectData(
                    wkuGraduation.getWkuGraduationData().getGraduationCreditLists().get(i).getCode(),
                    wkuGraduation.getWkuGraduationData().getGraduationCreditLists().get(i).getCategory(),
                    wkuGraduation.getWkuGraduationData().getGraduationCreditLists().get(i).getCredit(),
                    wkuGraduation.getWkuGraduationData().getGraduationCreditLists().get(i).getTitle()
            ));
        }

        graduationCompulsorySubjectAdapter.notifyDataSetChanged();
    }

    private void getMajorCreditListsData() {
        graduationCompulsorySubjectArrayList.clear();

        for(int i=0 ; i<wkuGraduation.getWkuGraduationData().getMajorCreditLists().size(); i++) {
            graduationCompulsorySubjectArrayList.add(new WKUGraduationCompulsorySubjectData(
                    wkuGraduation.getWkuGraduationData().getMajorCreditLists().get(i).getCode(),
                    wkuGraduation.getWkuGraduationData().getMajorCreditLists().get(i).getCategory(),
                    wkuGraduation.getWkuGraduationData().getMajorCreditLists().get(i).getCredit(),
                    wkuGraduation.getWkuGraduationData().getMajorCreditLists().get(i).getTitle()
            ));
        }

        graduationCompulsorySubjectAdapter.notifyDataSetChanged();
    }

    private void getMajorBasisCreditListsData() {
        graduationCompulsorySubjectArrayList.clear();

        for(int i=0 ; i<wkuGraduation.getWkuGraduationData().getMajorBasisCreditLists().size(); i++) {
            graduationCompulsorySubjectArrayList.add(new WKUGraduationCompulsorySubjectData(
                    wkuGraduation.getWkuGraduationData().getMajorBasisCreditLists().get(i).getCode(),
                    wkuGraduation.getWkuGraduationData().getMajorBasisCreditLists().get(i).getCategory(),
                    wkuGraduation.getWkuGraduationData().getMajorBasisCreditLists().get(i).getCredit(),
                    wkuGraduation.getWkuGraduationData().getMajorBasisCreditLists().get(i).getTitle()
            ));
        }

        graduationCompulsorySubjectAdapter.notifyDataSetChanged();
    }

    private void getGeneralCreditListsData() {
        graduationCompulsorySubjectArrayList.clear();

        for(int i=0 ; i<wkuGraduation.getWkuGraduationData().getGeneralCreditLists().size(); i++) {
            graduationCompulsorySubjectArrayList.add(new WKUGraduationCompulsorySubjectData(
                    wkuGraduation.getWkuGraduationData().getGeneralCreditLists().get(i).getCode(),
                    wkuGraduation.getWkuGraduationData().getGeneralCreditLists().get(i).getCategory(),
                    wkuGraduation.getWkuGraduationData().getGeneralCreditLists().get(i).getCredit(),
                    wkuGraduation.getWkuGraduationData().getGeneralCreditLists().get(i).getTitle()
            ));
        }

        graduationCompulsorySubjectAdapter.notifyDataSetChanged();
    }

    private void getGeneralAffiliationCreditListsData() {
        graduationCompulsorySubjectArrayList.clear();

        for(int i=0 ; i<wkuGraduation.getWkuGraduationData().getGeneralAffiliationCreditLists().size(); i++) {
            graduationCompulsorySubjectArrayList.add(new WKUGraduationCompulsorySubjectData(
                    wkuGraduation.getWkuGraduationData().getGeneralAffiliationCreditLists().get(i).getCode(),
                    wkuGraduation.getWkuGraduationData().getGeneralAffiliationCreditLists().get(i).getCategory(),
                    wkuGraduation.getWkuGraduationData().getGeneralAffiliationCreditLists().get(i).getCredit(),
                    wkuGraduation.getWkuGraduationData().getGeneralAffiliationCreditLists().get(i).getTitle()
            ));
        }

        graduationCompulsorySubjectAdapter.notifyDataSetChanged();
    }

    private void getScrapingData() {
        try {
            Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT wkuId, wkuPw, jSessionId, wkuTokenKey, studentNo, isDorm, major, graduationType FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
                this.wkuId = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(0), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.wkuPw = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(1), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.jSessionId = cursor.getString(2);
                this.wkuTokenKey = cursor.getString(3);
                this.studentNo = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(4), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.isDorm = cursor.getString(5);
                this.major = wkuDatabase.getWkuRsaSecurity().decryptRSA(cursor.getBlob(6), wkuDatabase.getWkuRsaSecurity().getPrivateKey());
                this.graduationType = cursor.getString(7);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getGraduationData() {
        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT * FROM wkuGraduationData WHERE graduation_id =" + graduationType, null);

        while(cursor.moveToNext()) {
            this.wkuGraduation.getWkuGraduationData().set(Integer.parseInt(graduationType), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10), cursor.getInt(11), cursor.getInt(12), cursor.getInt(13), cursor.getInt(14));
        }
    }
}

class GraduationCompulsoryGradeAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<WKUGraduationCompulsoryGradeData> arrayList;
    private int layout;

    public GraduationCompulsoryGradeAdapter(Context context, int layout, ArrayList<WKUGraduationCompulsoryGradeData> arrayList) {
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
        return this.arrayList.get(position).getTitle();
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

        TextView title = convertView.findViewById(R.id.graduation_compulsory_grade_title);
        TextView explain = convertView.findViewById(R.id.graduation_compulsory_grade_explain);
        TextView curGrade = convertView.findViewById(R.id.graduation_compulsory_grade_curGrade);
        TextView aimGrade = convertView.findViewById(R.id.graduation_compulsory_grade_aimGrade);

        if(position != 4) {
            if(arrayList.get(position).getCurGrade() >= arrayList.get(position).getAimGrade()) {
                curGrade.setTextColor(Color.BLUE);
                aimGrade.setTextColor(Color.BLUE);
            } else {
                curGrade.setTextColor(Color.RED);
                aimGrade.setTextColor(Color.RED);
            }
        } else {
            if(arrayList.get(position).getCurGrade() >= arrayList.get(position).getAimGrade()) {
                curGrade.setTextColor(Color.RED);
                aimGrade.setTextColor(Color.RED);
            } else {
                curGrade.setTextColor(Color.BLUE);
                aimGrade.setTextColor(Color.BLUE);
            }
        }

        title.setText(arrayList.get(position).getTitle());
        explain.setText(arrayList.get(position).getExplain());
        curGrade.setText(arrayList.get(position).getCurGrade() + "");
        aimGrade.setText(arrayList.get(position).getAimGrade() + "");

        return convertView;
    }
}

class GraduationCompulsorySubjectAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<WKUGraduationCompulsorySubjectData> arrayList;
    private int layout;

    public GraduationCompulsorySubjectAdapter(Context context, int layout, ArrayList<WKUGraduationCompulsorySubjectData> arrayList) {
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
        return this.arrayList.get(position).getTitle();
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

        TextView code = convertView.findViewById(R.id.graduation_compulsory_subject_code);
        TextView category = convertView.findViewById(R.id.graduation_compulsory_subject_category);
        TextView title = convertView.findViewById(R.id.graduation_compulsory_subject_title);
        TextView credit = convertView.findViewById(R.id.graduation_compulsory_subject_credit);
        TextView status = convertView.findViewById(R.id.graduation_compulsory_subject_status);


        code.setText(arrayList.get(position).getCode() + "");
        category.setText(arrayList.get(position).getCategory());
        title.setText(arrayList.get(position).getTitle());
        credit.setText(arrayList.get(position).getCredit() + "");
        status.setText("O");

        status.setTextColor(Color.BLUE);

        return convertView;
    }
}