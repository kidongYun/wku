package com.example.yun.wku;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Message;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WKUDatabase extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private WKURSASecurity wkuRsaSecurity;

    public WKUDatabase(Context context) {
        super(context, "WKU.db",null, 53);
        db = this.getWritableDatabase();

        wkuRsaSecurity = new WKURSASecurity(context.getAssets());
        initTable(db);
    }

    public SQLiteDatabase getDB() { return this.db; }
    public WKURSASecurity getWkuRsaSecurity() { return wkuRsaSecurity; }

    @Override
    public void onCreate(SQLiteDatabase db) {
        initTable(db);
    }

    public boolean isExistTable(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE name ='" + tableName + "'", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void initTable(SQLiteDatabase db) {
        if(!isExistTable(db,"wkuPrivateData")) {
            db.execSQL("CREATE TABLE wkuPrivateData ( studentNo BLOB, " +
                    "name BLOB, " +
                    "department BLOB, " +
                    "major BLOB," +
                    "year BLOB, " +
                    "tel BLOB, " +
                    "address BLOB, " +
                    "isDorm TEXT, " +
                    "wkuId BLOB, " +
                    "wkuPw BLOB, " +
                    "curGradeYear TEXT, " +
                    "curGradeSemester TEXT, " +
                    "isStartService TEXT, " +
                    "jSessionId TEXT, " +
                    "wkuTokenKey TEXT, " +
                    "graduationType TEXT)");
        }

        if(!isExistTable(db,"wkuScheduleData")) {
            db.execSQL("CREATE TABLE wkuScheduleData ( schedule_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "subject TEXT," +
                    "place TEXT," +
                    "dayOfWeek INTEGER," +
                    "period INTEGER," +
                    "subjectType TEXT," +
                    "classNo TEXT," +
                    "credit TEXT)");
        }

        if(!isExistTable(db,"wkuAttendData")) {
            db.execSQL("CREATE TABLE wkuAttendData ( attend_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "subject TEXT," +
                    "professor TEXT," +
                    "attend_detail_length INTEGER)");
        }

        if(!isExistTable(db,"wkuAttendDetailData")) {
            db.execSQL("CREATE TABLE wkuAttendDetailData ( attend_detail_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "week INTEGER," +
                    "date TEXT," +
                    "period INTEGER," +
                    "attend TEXT," +
                    "attend_id INTEGER)");
        }

        if(!isExistTable(db,"wkuScholarData")) {
            db.execSQL("CREATE TABLE wkuScholarData ( scholar_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "year INTEGER, " +
                    "term INTERGER, " +
                    "title TEXT," +
                    "money INTEGER)");
        }

        if(!isExistTable(db,"wkuGradeData")) {
            db.execSQL("CREATE TABLE wkuGradeData ( grade_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "year INTEGER, " +
                    "schoolYear INTEGER, " +
                    "term INTEGER, " +
                    "reqCredit DOUBLE," +
                    "ackCredit DOUBLE, " +
                    "totalGrade DOUBLE, " +
                    "avgGrade DOUBLE, " +
                    "grade_length INTEGER)");
        }

        if(!isExistTable(db,"wkuGradeDetailData")) {
            db.execSQL("CREATE TABLE wkuGradeDetailData ( grade_detail_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "category TEXT, " +
                    "code TEXT, " +
                    "subject TEXT, " +
                    "credit DOUBLE, " +
                    "avgGrade DOUBLE," +
                    "markGrade TEXT, " +
                    "grade_id INTEGER)");
        }

        if(!isExistTable(db,"wkuGradeCurData")) {
            db.execSQL("CREATE TABLE wkuGradeCurData ( grade_cur_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "category TEXT, " +
                    "subject TEXT, " +
                    "credit DOUBLE, " +
                    "midExam DOUBLE, " +
                    "finalExam DOUBLE, " +
                    "attendAssign DOUBLE, " +
                    "other DOUBLE, " +
                    "avgGrade DOUBLE, " +
                    "markGrade TEXT)");
        }

        if(!isExistTable(db,"wkuBbsNotiData")) {
            db.execSQL("CREATE TABLE wkuBbsNotiData ( bbs_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id INTEGER," +
                    "title TEXT," +
                    "author TEXT," +
                    "date TEXT," +
                    "hits INTEGER," +
                    "cid INTEGER)");
        }

        if(!isExistTable(db,"wkuBbsRoomData")) {
            db.execSQL("CREATE TABLE wkuBbsRoomData ( bbs_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id INTEGER," +
                    "title TEXT," +
                    "author TEXT," +
                    "date TEXT," +
                    "hits INTEGER," +
                    "cid INTEGER)");
        }

        if(!isExistTable(db,"wkuBbsJobData")) {
            db.execSQL("CREATE TABLE wkuBbsJobData ( bbs_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id INTEGER," +
                    "title TEXT," +
                    "author TEXT," +
                    "date TEXT," +
                    "hits INTEGER," +
                    "cid INTEGER)");
        }

        if(!isExistTable(db,"wkuBbsMarketData")) {
            db.execSQL("CREATE TABLE wkuBbsMarketData ( bbs_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id INTEGER," +
                    "title TEXT," +
                    "author TEXT," +
                    "date TEXT," +
                    "hits INTEGER," +
                    "cid INTEGER)");
        }

        if(!isExistTable(db,"wkuEClassData")) {
            db.execSQL("CREATE TABLE wkuEClassData ( eclass_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "professor TEXT," +
                    "mp TEXT," +
                    "sp TEXT," +
                    "ci TEXT," +
                    "ui TEXT," +
                    "sn TEXT)");
        }

        if(!isExistTable(db,"wkuEClassDetailNotiData")) {
            db.execSQL("CREATE TABLE wkuEClassDetailNotiData ( eclass_detail_noti_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "author TEXT," +
                    "date TEXT," +
                    "title TEXT," +
                    "file INTEGER," +
                    "hit TEXT, " +
                    "eclass_id INTEGER)");
        }

        if(!isExistTable(db,"wkuEClassDetailPdsData")) {
            db.execSQL("CREATE TABLE wkuEClassDetailPdsData ( eclass_detail_pds_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "file TEXT," +
                    "size TEXT," +
                    "date TEXT," +
                    "author TEXT, " +
                    "hit TEXT, " +
                    "eclass_id INTEGER)");
        }

        if(!isExistTable(db,"wkuEClassDetailReportData")) {
            db.execSQL("CREATE TABLE wkuEClassDetailReportData ( eclass_detail_report_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "date TEXT," +
                    "grade TEXT," +
                    "submit INTEGER," +
                    "eclass_id INTEGER)");
        }

        if(!isExistTable(db,"wkuEClassDetailLectureData")) {
            db.execSQL("CREATE TABLE wkuEClassDetailLectureData ( eclass_detail_lecture_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "week TEXT," +
                    "title TEXT," +
                    "eclass_id INTEGER)");
        }

        if(!isExistTable(db,"wkuEClassDetailLectureContentData")) {
            db.execSQL("CREATE TABLE wkuEClassDetailLectureContentData ( eclass_detail_lecture_content_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "period TEXT," +
                    "attend INTEGER," +
                    "url TEXT," +
                    "eclass_id INTEGER, " +
                    "detail_id INTEGER)");
        }

        if(!isExistTable(db,"wkuDormData")) {
            db.execSQL("CREATE TABLE wkuDormData ( dorm_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "date TEXT," +
                    "reason TEXT," +
                    "location TEXT," +
                    "emgTel TEXT)");
        }

        if(!isExistTable(db, "wkuAlarmData")) {
            db.execSQL("CREATE TABLE wkuAlarmData ( alarm_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "category INTEGER, " +
                    "title TEXT, " +
                    "date TEXT, " +
                    "author TEXT, " +
                    "id INTEGER, " +
                    "hits INTEGER, " +
                    "cid INTEGER, " +
                    "file TEXT, " +
                    "size TEXT, " +
                    "grade TEXT, " +
                    "submit INTEGER, " +
                    "week TEXT, " +
                    "isClick INTEGER)");
        }

        if(!isExistTable(db, "wkuSettingData")) {
            db.execSQL("CREATE TABLE wkuSettingData ( setting_id INTEGER," +
                    "title TEXT, " +
                    "detail TEXT, " +
                    "power INTEGER)");
        }

        if(!isExistTable(db, "wkuGraduationData")) {
            db.execSQL("CREATE TABLE wkuGraduationData ( graduation_id INTEGER, " +
                    "minGraduationCredit INTEGER, " +
                    "minMajorCredit INTEGER, " +
                    "minMajorCreditForDouble INTEGER, " +
                    "minMajorBasisCredit INTEGER, " +
                    "minGeneralCredit INTEGER, " +
                    "minGeneralCompulsoryCredit INTEGER, " +
                    "minGeneralAffiliationCredit INTEGER, " +
                    "minGeneralLanguageCredit INTEGER, " +
                    "minGeneralEnglishCredit INTEGER, " +
                    "minGeneralSoftwareCredit INTEGER, " +
                    "minGeneralHumanityCredit INTEGER, " +
                    "minGeneralStartUpCredit INTEGER, " +
                    "minGeneralCreativityCredit INTEGER, " +
                    "maxGeneralCredit INTEGER)");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void initPrivateData() { db.execSQL("DELETE FROM wkuPrivateData"); }
    public void initScheduleData() { db.execSQL("DELETE FROM wkuScheduleData"); }
    public void initAttendData() { db.execSQL("DELETE FROM wkuAttendData"); }
    public void initAttendDetailData() { db.execSQL("DELETE FROM wkuAttendDetailData"); }
    public void initScholarData() { db.execSQL("DELETE FROM wkuScholarData"); }
    public void initGradeData() { db.execSQL("DELETE FROM wkuGradeData"); }
    public void initGradeDetailData() { db.execSQL("DELETE FROM wkuGradeDetailData"); }
    public void initGradeCurData() { db.execSQL("DELETE FROM wkuGradeCurData"); }
    public void initBbsNotiData() { db.execSQL("DELETE FROM wkuBbsNotiData"); }
    public void initBbsRoomData() { db.execSQL("DELETE FROM wkuBbsRoomData"); }
    public void initBbsJobData() { db.execSQL("DELETE FROM wkuBbsJobData"); }
    public void initBbsMarketData() { db.execSQL("DELETE FROM wkuBbsMarketData"); }
    public void initEClassData() { db.execSQL("DELETE FROM wkuEClassData"); }
    public void initEClassDetailNotiData() { db.execSQL("DELETE FROM wkuEClassDetailNotiData"); }
    public void initEClassDetailPdsData() { db.execSQL("DELETE FROM wkuEClassDetailPdsData"); }
    public void initEClassDetailReportData() { db.execSQL("DELETE FROM wkuEClassDetailReportData"); }
    public void initEClassDetailLectureData() { db.execSQL("DELETE FROM wkuEClassDetailLectureData"); }
    public void initEClassDetailLectureContentData() { db.execSQL("DELETE FROM wkuEClassDetailLectureContentData"); }
    public void initDormData() { db.execSQL("DELETE FROM wkuDormData"); }
    public void initAlarmData() { db.execSQL("DELETE FROM wkuAlarmData"); }
    public void initSettingData() {
        db.execSQL("DELETE FROM wkuSettingData");

        this.insertSettingData(new WKUSettingData(WKUSettingData.BBS_ALARM_NOTI, "BBS 알림", "공지사항", true));
        this.insertSettingData(new WKUSettingData(WKUSettingData.BBS_ALARM_ROOM, "BBS 알림", "원룸, 자취, 하숙", false));
        this.insertSettingData(new WKUSettingData(WKUSettingData.BBS_ALARM_MARKET, "BBS 알림", "만물장터", true));
        this.insertSettingData(new WKUSettingData(WKUSettingData.BBS_ALARM_JOB, "BBS 알림", "아르바이트, 구인, 구직", true));
    }
    public void initGraduationData() {
        db.execSQL("DELETE FROM wkuGraduationData");
    }

    public void initAllData() {
        initPrivateData();
        initScheduleData();
        initAttendData();
        initAttendDetailData();
        initScholarData();
        initGradeData();
        initGradeDetailData();
        initGradeCurData();
        initBbsNotiData();
        initBbsRoomData();
        initBbsJobData();
        initBbsMarketData();
        initEClassData();
        initEClassDetailNotiData();
        initEClassDetailPdsData();
        initEClassDetailReportData();
        initEClassDetailLectureData();
        initEClassDetailLectureContentData();
        initAlarmData();
        initSettingData();
        initGraduationData();
    }

    public void insertPrivateData(WKUScrapingEngine wkuScrapingEngine, int graduationType) {
        try {
            SQLiteStatement sqLiteStatement = getDB().compileStatement("INSERT INTO wkuPrivateData VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sqLiteStatement.bindBlob(1, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getStudentNo(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindBlob(2, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getName(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindBlob(3, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getDepartment(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindBlob(4, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getMajor(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindBlob(5, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getYear(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindBlob(6, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getTel(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindBlob(7, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getAddress(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindString(8, wkuScrapingEngine.getWkuData().getWkuPrivateData().getIsDorm());
            sqLiteStatement.bindBlob(9, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuId(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindBlob(10, wkuRsaSecurity.encryptRSA(wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuPw(), wkuRsaSecurity.getPublicKey()));
            sqLiteStatement.bindString(11, "");
            sqLiteStatement.bindString(12, "");
            sqLiteStatement.bindString(13, "0");
            sqLiteStatement.bindString(14, wkuScrapingEngine.getWkuData().getWkuPrivateData().getJSessionId());
            sqLiteStatement.bindString(15, wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuTokenKey());
            sqLiteStatement.bindString(16, graduationType + "");

            sqLiteStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertScheduleData(WKUScrapingEngine wkuScrapingEngine) {
        for(int index = 0; index<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScheduleDatas().size(); index++) {
            db.execSQL("INSERT INTO wkuScheduleData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScheduleDatas().get(index).getSubject() + "', " +
                    " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScheduleDatas().get(index).getPlace() + "', " +
                    " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScheduleDatas().get(index).getDayOfWeek() + "', " +
                    " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScheduleDatas().get(index).getPeriod() + "', " +
                    " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScheduleDatas().get(index).getSubjectType() + "', " +
                    " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScheduleDatas().get(index).getClassNo() + "', " +
                    " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScheduleDatas().get(index).getCredit() + "')");
        }
    }

    public void insertAttendData(WKUScrapingEngine wkuScrapingEngine, int index) {
        db.execSQL("INSERT INTO wkuAttendData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().get(index).getSubject() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().get(index).getProfessor() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().size() + "')");
    }

    public void insertAttendDetailData(WKUScrapingEngine wkuScrapingEngine, int index, int attendIndex) {

        db.execSQL("INSERT INTO wkuAttendDetailData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().get(attendIndex).getWkuAttendDetailDatas().get(index).getWeek() + "'," +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().get(attendIndex).getWkuAttendDetailDatas().get(index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().get(attendIndex).getWkuAttendDetailDatas().get(index).getPeriod() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().get(attendIndex).getWkuAttendDetailDatas().get(index).getAttend() + "'," +
                " '" + attendIndex + "')");
    }

    public void insertScholarData(WKUScrapingEngine wkuScrapingEngine, int index) {

        db.execSQL("INSERT INTO wkuScholarData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScholarDatas().get(index).getYear() + "'," +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScholarDatas().get(index).getTerm() + "'," +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScholarDatas().get(index).getTitle() + "'," +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScholarDatas().get(index).getMoney() + "')");
    }

    public void insertGradeData(WKUScrapingEngine wkuScrapingEngine, int index) {

        db.execSQL("INSERT INTO wkuGradeData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(index).getYear()+ "'," +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(index).getSchoolYear() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(index).getTerm() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(index).getReqCredit() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(index).getAckCredit() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(index).getTotalGrade() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(index).getAvgGrade() + "'," +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().size() + "')");
    }

    public void insertGradeDetailData(WKUScrapingEngine wkuScrapingEngine, int index, int gradeIndex) {

        db.execSQL("INSERT INTO wkuGradeDetailData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(gradeIndex).getWkuGradeDetailDatas().get(index).getCategory() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(gradeIndex).getWkuGradeDetailDatas().get(index).getCode() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(gradeIndex).getWkuGradeDetailDatas().get(index).getSubject() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(gradeIndex).getWkuGradeDetailDatas().get(index).getCredit() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(gradeIndex).getWkuGradeDetailDatas().get(index).getAvgGrade() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(gradeIndex).getWkuGradeDetailDatas().get(index).getMarkGrade() + "', " +
                " '" + gradeIndex + "')");
    }

    public void insertGradeCurData(WKUScrapingEngine wkuScrapingEngine, int index) {
        db.execSQL("INSERT INTO wkuGradeCurData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getCategory() +"', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getSubject() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getCredit() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getMidExam() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getFinalExam() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getAttendAssign() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getOther() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getAvgGrade() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().get(index).getMarkGrade() + "')");
    }

    public void insertBbsNotiData(WKUScrapingEngine wkuScrapingEngine, int index) {
        db.execSQL("INSERT INTO wkuBbsNotiData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuBbsNotiDatas().get(index).getId() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsNotiDatas().get(index).getTitle() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsNotiDatas().get(index).getAuthor() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsNotiDatas().get(index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsNotiDatas().get(index).getHits() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsNotiDatas().get(index).getCid() + "')");
    }

    public void insertBbsRoomData(WKUScrapingEngine wkuScrapingEngine, int index) {
        db.execSQL("INSERT INTO wkuBbsRoomData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuBbsRoomDatas().get(index).getId() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsRoomDatas().get(index).getTitle() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsRoomDatas().get(index).getAuthor() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsRoomDatas().get(index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsRoomDatas().get(index).getHits() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsRoomDatas().get(index).getCid() + "')");
    }

    public void insertBbsJobData(WKUScrapingEngine wkuScrapingEngine, int index) {

        db.execSQL("INSERT INTO wkuBbsJobData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuBbsJobDatas().get(index).getId() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsJobDatas().get(index).getTitle() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsJobDatas().get(index).getAuthor() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsJobDatas().get(index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsJobDatas().get(index).getHits() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsJobDatas().get(index).getCid() + "')");
    }

    public void insertBbsMarketData(WKUScrapingEngine wkuScrapingEngine, int index) {
        db.execSQL("INSERT INTO wkuBbsMarketData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(index).getId() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(index).getTitle() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(index).getAuthor() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(index).getHits() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(index).getCid() + "')");
    }

    public void insertEClassData(WKUScrapingEngine wkuScrapingEngine, int index) {
        db.execSQL("INSERT INTO wkuEClassData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(index).getTitle() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(index).getProfessor() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(index).getMp() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(index).getSp() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(index).getCi() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(index).getUi() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(index).getSn() + "')");
    }

    public void insertEClassDetailNotiData(WKUScrapingEngine wkuScrapingEngine, int eclass_index, int detail_index) {
        db.execSQL("INSERT INTO wkuEClassDetailNotiData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailNotiDatas().get(detail_index).getAuthor() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailNotiDatas().get(detail_index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailNotiDatas().get(detail_index).getTitle() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailNotiDatas().get(detail_index).isFile() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailNotiDatas().get(detail_index).getHits() + "', " +
                " '" + eclass_index + "')");
    }

    public void insertEClassDetailPdsData(WKUScrapingEngine wkuScrapingEngine, int eclass_index, int detail_index) {
        db.execSQL("INSERT INTO wkuEClassDetailPdsData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailPdsDatas().get(detail_index).getTitle() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailPdsDatas().get(detail_index).getFile() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailPdsDatas().get(detail_index).getSize() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailPdsDatas().get(detail_index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailPdsDatas().get(detail_index).getAuthor() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailPdsDatas().get(detail_index).getHits() + "', " +
                " '" + eclass_index + "')");
    }

    public void insertEClassDetailReportData(WKUScrapingEngine wkuScrapingEngine, int eclass_index, int detail_index) {
        db.execSQL("INSERT INTO wkuEClassDetailReportData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailReportDatas().get(detail_index).getTitle() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailReportDatas().get(detail_index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailReportDatas().get(detail_index).getGrade() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailReportDatas().get(detail_index).isSubmit() + "', " +
                " '" + eclass_index + "')");
    }

    public void insertEClassDetailLectureData(WKUScrapingEngine wkuScrapingEngine, int eclass_index, int detail_index) {
        db.execSQL("INSERT INTO wkuEClassDetailLectureData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailLectureDatas().get(detail_index).getWeek() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailLectureDatas().get(detail_index).getTitle() + "', " +
                " '" + eclass_index + "')");
    }

    public void insertEClassDetailLectureContentData(WKUScrapingEngine wkuScrapingEngine, int eclass_index, int detail_index, int content_index) {
        db.execSQL("INSERT INTO wkuEClassDetailLectureContentData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailLectureDatas().get(detail_index).getWkuEClassDetailLectureContentDatas().get(content_index).getPeriod() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailLectureDatas().get(detail_index).getWkuEClassDetailLectureContentDatas().get(content_index).isAttend() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailLectureDatas().get(detail_index).getWkuEClassDetailLectureContentDatas().get(content_index).getUrl() + "', " +
                " '" + eclass_index + "', " +
                " '" + detail_index + "')");
    }

    public void insertDormData(WKUScrapingEngine wkuScrapingEngine, int index) {
        db.execSQL("INSERT INTO wkuDormData VALUES (null, '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuDormDatas().get(index).getDate() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuDormDatas().get(index).getReason() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuDormDatas().get(index).getLocation() + "', " +
                " '" + wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuDormDatas().get(index).getEmgTel()+ "')");
    }

    public void insertAlarmBBSData(ArrayList<WKUBBSData> wkubbsData, int index) {
        // FOR BBS...
        db.execSQL("INSERT INTO wkuAlarmData VALUES (null, '" + wkubbsData.get(index).getCategory() + "', " +
                " '" + wkubbsData.get(index).getTitle() + "', " +
                " '" + wkubbsData.get(index).getDate() + "', " +
                " '" + wkubbsData.get(index).getAuthor() + "', " +
                " '" + wkubbsData.get(index).getId() + "', " +
                " '" + wkubbsData.get(index).getHits() + "', " +
                " '" + wkubbsData.get(index).getCid() + "', " +
                " '', " +
                " '', " +
                " '', " +
                " -1, " +
                " '', " +
                " 0)");
    }

    public void insertAlarmEClassDetailNotiData(ArrayList<WKUEClassDetailNotiData> wkueClassDetailNotilData, int index) {
        // FOR ECLASS DETAIL NOTI...
        int flag = 0;

        if(wkueClassDetailNotilData.get(index).isFile()) {
            flag = 1;
        }
        db.execSQL("INSERT INTO wkuAlarmData VALUES (null, '" + wkueClassDetailNotilData.get(index).getCategory() + "', " +
                " '" + wkueClassDetailNotilData.get(index).getTitle() + "', " +
                " '" + wkueClassDetailNotilData.get(index).getDate() + "', " +
                " '" + wkueClassDetailNotilData.get(index).getAuthor() + "', " +
                " '" + wkueClassDetailNotilData.get(index).getId() + "', " +
                " '" + wkueClassDetailNotilData.get(index).getHits() + "', " +
                " -1, " +
                " " + flag + ", " +
                " '', " +
                " '', " +
                " -1, " +
                " '', " +
                " 0)");
    }

    public void insertAlarmEClassDetailPdsData(ArrayList<WKUEClassDetailPdsData> wkueClassDetailPdsData, int index) {
        // FOR ECLASS DETAIL PDS...
        db.execSQL("INSERT INTO wkuAlarmData VALUES (null, '" + wkueClassDetailPdsData.get(index).getCategory() + "', " +
                " '" + wkueClassDetailPdsData.get(index).getTitle() + "', " +
                " '" + wkueClassDetailPdsData.get(index).getDate() + "', " +
                " '" + wkueClassDetailPdsData.get(index).getAuthor() + "', " +
                " '" + wkueClassDetailPdsData.get(index).getId() + "', " +
                " '" + wkueClassDetailPdsData.get(index).getHits() + "', " +
                " 0, " +
                " '" + wkueClassDetailPdsData.get(index).getFile() + "', " +
                " '" + wkueClassDetailPdsData.get(index).getSize() + "', " +
                " '', " +
                " -1, " +
                " '', " +
                " 0)");
    }

    public void insertAlarmEClassDetailReportData(ArrayList<WKUEClassDetailReportData> wkueClassDetailReportData, int index) {
        // FOR ECLASS...
        int flag = 0;

        if(wkueClassDetailReportData.get(index).isSubmit()) {
            flag = 1;
        }
        db.execSQL("INSERT INTO wkuAlarmData VALUES (null, '" + wkueClassDetailReportData.get(index).getCategory() + "', " +
                " '" + wkueClassDetailReportData.get(index).getTitle() + "', " +
                " '" + wkueClassDetailReportData.get(index).getDate() + "', " +
                " '', " +
                " '" + wkueClassDetailReportData.get(index).getId() + "', " +
                " -1, " +
                " -1, " +
                " '', " +
                " '', " +
                " '" + wkueClassDetailReportData.get(index).getGrade() + "', " +
                " " + flag + ", " +
                " '', " +
                " 0)");
    }

    public void insertAlarmEClassDetailLectureData(ArrayList<WKUEClassDetailLectureData> wkueClassDetailLectureData, int index) {
        // FOR ECLASS...
        db.execSQL("INSERT INTO wkuAlarmData VALUES (null, '" + wkueClassDetailLectureData.get(index).getCategory() + "', " +
                " '" + wkueClassDetailLectureData.get(index).getTitle() + "', " +
                " '', " +
                " '', " +
                " '" + wkueClassDetailLectureData.get(index).getId() + "', " +
                " -1, " +
                " -1, " +
                " '', " +
                " '', " +
                " '', " +
                " -1, " +
                " '" + wkueClassDetailLectureData.get(index).getWeek() + "', " +
                " 0)");
    }

    public void insertSettingData(WKUSettingData wkuSettingData) {
        db.execSQL("INSERT INTO wkuSettingData VALUES ('" + wkuSettingData.getId() + "', " +
                " '" + wkuSettingData.getTitle() + "', " +
                " '" + wkuSettingData.getDetail() + "', " +
                " '" + wkuSettingData.getPowerToInt() + "')");
    }

    public void insertGraduationData(WKUGraduationData wkuGraduationData) {
        db.execSQL("INSERT INTO wkuGraduationData VALUES ('" + wkuGraduationData.getGraduationType() + "', " +
                " '" + wkuGraduationData.getMinGraduationCredit() + "', " +
                " '" + wkuGraduationData.getMinMajorCredit() + "', " +
                " '" + wkuGraduationData.getMinMajorCreditForDouble() + "', " +
                " '" + wkuGraduationData.getMinMajorBasisCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralCompulsoryCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralAffiliationCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralLanguageCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralEnglishCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralSoftwareCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralHumanityCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralStartUpCredit() + "', " +
                " '" + wkuGraduationData.getMinGeneralCreativityCredit() + "', " +
                " '" + wkuGraduationData.getMaxGeneralCredit() + "')");
    }

    public void logPrivateData() {
        try {
            Cursor cursor;

            cursor = db.rawQuery("SELECT studentNo, name, department, major, year, tel, address, isDorm, wkuId, wkuPw, curGradeYear, curGradeSemester, isStartService, graduationType  FROM wkuPrivateData", null);

            while(cursor.moveToNext()) {
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 학번 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(0), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 이름 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(1), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 대학 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(2), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 전공 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(3), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 학년 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(4), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 전화 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(5), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 주소 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(6), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 기숙사 : " + cursor.getString(7));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 아이디 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(8), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 비밀번호 : " + wkuRsaSecurity.decryptRSA(cursor.getBlob(9), wkuRsaSecurity.getPrivateKey()));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 이번성적년도 : " + cursor.getString(10));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 이번성적학기 : " + cursor.getString(11));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 서비스시작여부 : " + cursor.getString(12));
                Log.i("WKU", "WKUDatabase.WKUPrivateData / 졸업정보타입 : " + cursor.getString(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logScheduleData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT subject, place, dayOfWeek, period, subjectType, classNo, credit FROM wkuScheduleData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUScheduleData / 교과목명 : " + cursor.getString(0) + " 강의실 : " + cursor.getString(1) + " 몇요일 : " + cursor.getString(2) + " 몇교시 : " + cursor.getString(3) + " 구분 : " + cursor.getString(4) + " 분반 : " + cursor.getString(5) + " 학점 : " + cursor.getString(6));
        }
    }

    public void logBbsNotiData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT id, title, author, date, hits, cid FROM wkuBbsNotiData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUBbsNotiData / ID : " + cursor.getInt(0) + " TITLE : " + cursor.getString(1) + " AUTHOR : " + cursor.getString(2) + " DATE : " + cursor.getString(3) + " HITS : " + cursor.getInt(4) + " CID : " + cursor.getInt(5));
        }
    }

    public void logBbsRoomData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT id, title, author, date, hits, cid FROM wkuBbsRoomData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUBbsRoomData / ID : " + cursor.getInt(0) + " TITLE : " + cursor.getString(1) + " AUTHOR : " + cursor.getString(2) + " DATE : " + cursor.getString(3) + " HITS : " + cursor.getInt(4) + " CID : " + cursor.getInt(5));
        }
    }

    public void logEClassData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT eclass_id, title, professor, mp, sp, ci, ui, sn FROM wkuEClassData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUEClassData / ECLASS_ID : " + cursor.getInt(0) + " TITLE : " + cursor.getString(1) + " PROFESSOR : " + cursor.getString(2) + " MP : " + cursor.getString(3) + " SP : " + cursor.getString(4) + " CI : " + cursor.getString(5) + " UI : " + cursor.getString(6) + " SN : " + cursor.getString(7));
        }
    }

    public void logEClassDetailNotiData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT author, date, title, file, hit, eclass_id FROM wkuEClassDetailNotiData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUEClassDetailNotiData / AUTHOR : " + cursor.getString(0) + " DATE : " + cursor.getString(1) + " TITLE : " + cursor.getString(2) + " FILE : " + cursor.getInt(3) + " HIT : " + cursor.getString(4) + " ECLASS_ID : " + cursor.getInt(5));
        }
    }

    public void logEClassDetailPdsData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT title, file, size, date, author, hit, eclass_id FROM wkuEClassDetailPdsData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUEClassDetailPdsData / TITLE : " + cursor.getString(0) + " FILE : " + cursor.getString(1) + " SIZE : " + cursor.getString(2) + " DATE : " + cursor.getString(3) + " AUTHOR : " + cursor.getString(4) + " HIT : " + cursor.getString(5) + " ECLASS_ID : " + cursor.getInt(6));
        }
    }

    public void logEClassDetailReportData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT title, date, grade, submit, eclass_id FROM wkuEClassDetailReportData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUEClassDetailReportData / TITLE : " + cursor.getString(0) + " DATE : " + cursor.getString(1) + " GRADE : " + cursor.getString(2) + " SUBMIT : " + cursor.getInt(3) + " ECLASS_ID : " + cursor.getInt(4));
        }
    }

    public void logEClassDetailLectureData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT eclass_detail_lecture_id, week, title, eclass_id FROM wkuEClassDetailLectureData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUEClassDetailLectureData / DETAIL_ID : " + cursor.getInt(0) + " WEEK : " + cursor.getString(1) + " TITLE : " + cursor.getString(2) + " ECLASS_ID : " + cursor.getInt(3));
        }
    }

    public void logEClassDetailLectureContentData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT period, attend, url, eclass_id, detail_id FROM wkuEClassDetailLectureContentData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUEClassDetailLectureContentData / PERIOD : " + cursor.getString(0) + " ATTEND : " + cursor.getInt(1) + " URL : " + cursor.getString(2) + " ECLASS_ID : " + cursor.getInt(3) + " DETAIL_ID : " + cursor.getInt(4));
        }
    }

    public void logDormData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT date, reason, location, emgTel FROM wkuDormData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUDormData / DATE : " + cursor.getString(0) + " REASON : " + cursor.getString(1) + " LOCATION : " + cursor.getString(2) + " EMGTEL : " + cursor.getString(3));
        }
    }

    public void logAlarmData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT category, title, date, author, id, hits, cid, file, size, grade, submit, week, isClick, alarm_id FROM wkuAlarmData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUAlarmData / " +
                    " ALARM_ID : " + cursor.getInt(13) +
                    " CATEGORY : " + cursor.getInt(0) +
                    " TITLE : " + cursor.getString(1) +
                    " AUTHOR : " + cursor.getString(3) +
                    " ID : " + cursor.getInt(4) +
                    " HITS : " + cursor.getInt(5) +
                    " CID : " + cursor.getInt(6) +
                    " FILE : " + cursor.getString(7) +
                    " SIZE : " + cursor.getString(8) +
                    " GRADE : " + cursor.getString(9) +
                    " SUBMIT : " + cursor.getInt(10) +
                    " WEEK : " + cursor.getString(11) +
                    " ISCLICK : " + cursor.getInt(12));
        }
    }

    public void logSettingData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT setting_id, title, detail, power FROM wkuSettingData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUSettingData / ID : " + cursor.getInt(0) + " TITLE : " + cursor.getString(1) + " DETAIL : " + cursor.getString(2) + " POWER : " + cursor.getInt(3));
        }
    }

    public void logGraduationData() {
        Cursor cursor;

        cursor = db.rawQuery("SELECT " +
                "graduation_id, " +
                "minGraduationCredit, " +
                "minMajorCredit, " +
                "minMajorCreditForDouble, " +
                "minMajorBasisCredit, " +
                "minGeneralCredit, " +
                "minGeneralCompulsoryCredit, " +
                "minGeneralAffiliationCredit, " +
                "minGeneralLanguageCredit, " +
                "minGeneralEnglishCredit, " +
                "minGeneralSoftwareCredit, " +
                "minGeneralHumanityCredit, " +
                "minGeneralStartUpCredit, " +
                "minGeneralCreativityCredit, " +
                "maxGeneralCredit FROM wkuGraduationData", null);

        while(cursor.moveToNext()) {
            Log.i("WKU", "WKUDatabase.WKUGraduationData / " +
                    " GRADUATION_ID : " + cursor.getInt(0) +
                    " minGraduationCredit : " + cursor.getInt(1) +
                    " minMajorCredit : " + cursor.getInt(2) +
                    " minMajorCreditForDouble : " + cursor.getInt(3) +
                    " minMajorBasisCredit : " + cursor.getInt(4) +
                    " minGeneralCredit : " + cursor.getInt(5) +
                    " minGeneralCompulsoryCredit : " + cursor.getInt(6) +
                    " minGeneralAffiliationCredit : " + cursor.getInt(7) +
                    " minGeneralLanguageCredit : " + cursor.getInt(8) +
                    " minGeneralEnglishCredit : " + cursor.getInt(9) +
                    " minGeneralSoftwareCredit : " + cursor.getInt(10) +
                    " minGeneralHumanityCredit : " + cursor.getInt(11) +
                    " minGeneralStartUpCredit : " + cursor.getInt(12) +
                    " minGeneralCreativityCredit : " + cursor.getInt(13) +
                    " maxGeneralCredit : " + cursor.getInt(14));
        }
    }
}
class WKUDBThread extends Thread {
    public static final int ATTEND_FLAG = 1;
    public static final int SCHOLAR_FLAG = 2;
    public static final int GRADE_FLAG = 3;
    public static final int BBS_FLAG = 4;
    public static final int BBS_NOTI_FLAG = 20;
    public static final int BBS_ROOM_FLAG = 21;
    public static final int BBS_MARKET_FLAG = 22;
    public static final int BBS_JOB_FLAG = 23;
    public static final int ECLASS_FLAG = 5;
    public static final int DORM_FLAG = 6;
    public static final int ALARM_FLAG = 7;

    public static final int DB_FINISH_FLAG = 10;
    public static final int DB_ALARM_FINISH_FLAG = 11;

    private int mode;
    private WKUDatabase wkuDatabase;
    private WKUScrapingEngine wkuScrapingEngine;
    private Handler handler;

    public WKUDBThread(int mode, WKUDatabase wkuDatabase, WKUScrapingEngine wkuScrapingEngine, Handler handler) {
        this.mode = mode;
        this.wkuDatabase = wkuDatabase;
        this.wkuScrapingEngine = wkuScrapingEngine;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();

        switch (mode) {
            case ATTEND_FLAG :
                attendMode();
                break;
            case SCHOLAR_FLAG :
                scholarMode();
                break;
            case GRADE_FLAG :
                gradeMode();
                break;
            case BBS_FLAG :
                bbsMode();
                break;
            case BBS_NOTI_FLAG:
                bbsNotiMode();
                break;
            case BBS_ROOM_FLAG:
                bbsRoomMode();
                break;
            case BBS_MARKET_FLAG:
                bbsMarketMode();
                break;
            case BBS_JOB_FLAG:
                bbsJobMode();
                break;
            case ECLASS_FLAG:
                eClassMode();
                break;
            case DORM_FLAG:
                dormMode();
                break;
            case ALARM_FLAG:
                alarmMode();
                break;
        }
    }

    private void attendMode() {
        wkuDatabase.initAttendData();
        wkuDatabase.initAttendDetailData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().size(); i++) {
            wkuDatabase.insertAttendData(wkuScrapingEngine, i);

            for(int j=0; j<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuAttendDatas().get(i).getWkuAttendDetailDatas().size(); j++) {
                wkuDatabase.insertAttendDetailData(wkuScrapingEngine, j, i);
            }
        }

        Message msg = new Message();
        msg.what = DB_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void scholarMode() {
        wkuDatabase.initScholarData();

        for(int i=0; i < wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuScholarDatas().size(); i++) {
            wkuDatabase.insertScholarData(wkuScrapingEngine, i);
        }

        Message msg = new Message();
        msg.what = DB_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void gradeMode() {
        wkuDatabase.initGradeData();
        wkuDatabase.initGradeDetailData();
        wkuDatabase.initGradeCurData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().size(); i++) {
            wkuDatabase.insertGradeData(wkuScrapingEngine, i);

            for(int j=0; j < wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeDatas().get(i).getWkuGradeDetailDatas().size(); j++) {
                wkuDatabase.insertGradeDetailData(wkuScrapingEngine, j, i);
            }
        }

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuGradeCurDatas().size(); i++) {
            wkuDatabase.insertGradeCurData(wkuScrapingEngine, i);
        }

        Message msg = new Message();
        msg.what = DB_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void bbsMode() {
        wkuDatabase.initBbsNotiData();
        wkuDatabase.initBbsRoomData();
        wkuDatabase.initBbsJobData();
        wkuDatabase.initBbsMarketData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsNotiDatas().size(); i++) {
            wkuDatabase.insertBbsNotiData(wkuScrapingEngine, i);
        }

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsRoomDatas().size(); i++) {
            wkuDatabase.insertBbsRoomData(wkuScrapingEngine, i);
        }

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsJobDatas().size(); i++) {
            wkuDatabase.insertBbsJobData(wkuScrapingEngine, i);
        }

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().size(); i++) {
            wkuDatabase.insertBbsMarketData(wkuScrapingEngine, i);
        }

        Message msg = new Message();
        msg.what = DB_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void bbsNotiMode() {
        wkuDatabase.initBbsNotiData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsNotiDatas().size(); i++) {
            wkuDatabase.insertBbsNotiData(wkuScrapingEngine, i);
        }

        Message msg = new Message();
        msg.what = DB_ALARM_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void bbsRoomMode() {
        wkuDatabase.initBbsRoomData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsRoomDatas().size(); i++) {
            wkuDatabase.insertBbsRoomData(wkuScrapingEngine, i);
        }

        Message msg = new Message();
        msg.what = DB_ALARM_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void bbsMarketMode() {
        wkuDatabase.initBbsMarketData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().size(); i++) {
            wkuDatabase.insertBbsMarketData(wkuScrapingEngine, i);
        }

        Message msg = new Message();
        msg.what = DB_ALARM_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void bbsJobMode() {
        wkuDatabase.initBbsJobData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsJobDatas().size(); i++) {
            wkuDatabase.insertBbsJobData(wkuScrapingEngine, i);
        }

        Message msg = new Message();
        msg.what = DB_ALARM_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void eClassMode() {
        wkuDatabase.initEClassData();
        wkuDatabase.initEClassDetailNotiData();
        wkuDatabase.initEClassDetailPdsData();
        wkuDatabase.initEClassDetailReportData();
        wkuDatabase.initEClassDetailLectureData();
        wkuDatabase.initEClassDetailLectureContentData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().size(); i++) {
            wkuDatabase.insertEClassData(wkuScrapingEngine, i);

            for(int j=0; j<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(i).getWkuEClassDetailNotiDatas().size(); j++) {
                wkuDatabase.insertEClassDetailNotiData(wkuScrapingEngine, i, j);
            }

            for(int j=0; j<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(i).getWkuEClassDetailPdsDatas().size(); j++) {
                wkuDatabase.insertEClassDetailPdsData(wkuScrapingEngine, i, j);
            }

            for(int j=0; j<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(i).getWkuEClassDetailReportDatas().size(); j++) {
                wkuDatabase.insertEClassDetailReportData(wkuScrapingEngine, i, j);
            }

            for(int j=0; j<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(i).getWkuEClassDetailLectureDatas().size(); j++) {
                wkuDatabase.insertEClassDetailLectureData(wkuScrapingEngine, i, j);

                for(int l=0; l<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuEClassDatas().get(i).getWkuEClassDetailLectureDatas().get(j).getWkuEClassDetailLectureContentDatas().size(); l++) {
                    wkuDatabase.insertEClassDetailLectureContentData(wkuScrapingEngine, i, j, l);
                }
            }
        }

        Message msg = new Message();
        msg.what = DB_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void dormMode() {
        wkuDatabase.initDormData();

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuPrivateData().getWkuDormDatas().size(); i++) {
            wkuDatabase.insertDormData(wkuScrapingEngine, i);
        }

        Message msg = new Message();
        msg.what = DB_FINISH_FLAG;
        handler.sendMessage(msg);
    }

    private void alarmMode() {

    }
}