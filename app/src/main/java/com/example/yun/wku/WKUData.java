package com.example.yun.wku;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WKUData {
    private WKUPrivateData wkuPrivateData;
    private ArrayList<WKUBBSData> wkuBbsNotiDatas;
    private ArrayList<WKUBBSData> wkuBbsRoomDatas;
    private ArrayList<WKUBBSData> wkuBbsJobDatas;
    private ArrayList<WKUBBSData> wkuBbsMarketDatas;

    public WKUData() {
        wkuPrivateData = new WKUPrivateData();
        wkuBbsNotiDatas = new ArrayList<>();
        wkuBbsRoomDatas = new ArrayList<>();
        wkuBbsJobDatas = new ArrayList<>();
        wkuBbsMarketDatas = new ArrayList<>();
    }

    public WKUPrivateData getWkuPrivateData() { return wkuPrivateData; }
    public ArrayList<WKUBBSData> getWkuBbsNotiDatas() { return this.wkuBbsNotiDatas; }
    public ArrayList<WKUBBSData> getWkuBbsRoomDatas() { return this.wkuBbsRoomDatas; }
    public ArrayList<WKUBBSData> getWkuBbsJobDatas() { return this.wkuBbsJobDatas; }
    public ArrayList<WKUBBSData> getWkuBbsMarketDatas() { return this.wkuBbsMarketDatas; }
}

class WKUPrivateData {
    private String wkuId;
    private String wkuPw;
    private String name;
    private String department;
    private String major;
    private String studentNo;
    private String year;
    private String term;
    private String tel;
    private String address;
    private String isDorm;
    private String gradeCurYear;
    private String gradeCurTerm;
    private String jSessionId;
    private String wkuTokenKey;

    private ArrayList<WKUScholarData> wkuScholarDatas;
    private ArrayList<WKUGradeData> wkuGradeDatas;
    private ArrayList<WKUGradeCurData> wkuGradeCurDatas;
    private ArrayList<WKUScheduleData> wkuScheduleDatas;
    private ArrayList<WKUAttendData> wkuAttendDatas;
    private ArrayList<WKUDormData> wkuDormDatas;
    private ArrayList<WKUEClassData> wkuEClassDatas;

    public WKUPrivateData() {
        wkuScholarDatas = new ArrayList<>();
        wkuGradeDatas = new ArrayList<>();
        wkuGradeCurDatas = new ArrayList<>();
        wkuScheduleDatas = new ArrayList<>();
        wkuAttendDatas = new ArrayList<>();
        wkuDormDatas = new ArrayList<>();
        wkuEClassDatas = new ArrayList<>();
    }

    public WKUPrivateData setWkuId(String wkuId) { this.wkuId = wkuId; return this; }
    public String getWkuId() { return this.wkuId; }

    public WKUPrivateData setWkuPw(String wkuPw) { this.wkuPw = wkuPw; return this; }
    public String getWkuPw() { return this.wkuPw; }

    public WKUPrivateData setName(String name) { this.name = name; return this; }
    public String getName() { return this.name; }

    public WKUPrivateData setDepartment(String department) { this.department = department; return this; }
    public String getDepartment() { return this.department; }

    public WKUPrivateData setMajor(String major) { this.major = major; return this; }
    public String getMajor() { return this.major; }

    public WKUPrivateData setStudentNo(String gradeNumber) { this.studentNo = gradeNumber; return this; }
    public String getStudentNo() { return this.studentNo; }

    public WKUPrivateData setYear(String year) { this.year = year; return this; }
    public String getYear() { return this.year; }

    public WKUPrivateData setTerm(String term) { this.term = term; return this; }
    public String getTerm() { return this.term; }

    public WKUPrivateData setTel(String tel) { this.tel = tel; return this; }
    public String getTel() { return this.tel; }

    public WKUPrivateData setAddress(String address) { this.address = address; return this; }
    public String getAddress() { return this.address; }

    public WKUPrivateData setIsDorm(String isDorm) { this.isDorm = isDorm; return this; }
    public String getIsDorm() { return this.isDorm; }

    public WKUPrivateData setGradeCurYear(String gradeCurYear) { this.gradeCurYear = gradeCurYear; return this; }
    public String getGradeCurYear() { return this.gradeCurYear; }

    public WKUPrivateData setGradeCurTerm(String gradeCurTerm) { this.gradeCurTerm = gradeCurTerm; return this; }
    public String getGradeCurTerm() { return this.gradeCurTerm; }

    public WKUPrivateData setJSessionId(String jSessionId) { this.jSessionId = jSessionId; return this; }
    public String getJSessionId() { return this.jSessionId; }

    public WKUPrivateData setWkuTokenKey(String wkuTokenKey) { this.wkuTokenKey = wkuTokenKey; return this; }
    public String getWkuTokenKey() { return this.wkuTokenKey; }

    public ArrayList<WKUScholarData> getWkuScholarDatas() {
        return this.wkuScholarDatas;
    }
    public ArrayList<WKUGradeData> getWkuGradeDatas() { return this.wkuGradeDatas; }
    public ArrayList<WKUScheduleData> getWkuScheduleDatas() { return this.wkuScheduleDatas; }
    public ArrayList<WKUAttendData> getWkuAttendDatas() { return this.wkuAttendDatas; }
    public ArrayList<WKUDormData> getWkuDormDatas() { return this.wkuDormDatas; }
    public ArrayList<WKUGradeCurData> getWkuGradeCurDatas() { return this.wkuGradeCurDatas; }
    public ArrayList<WKUEClassData> getWkuEClassDatas() { return wkuEClassDatas; }

    public void logPrivateData() {
        Log.i("WKU", "WKUData.WKUPrivateData / 이름 : " + getName());
        Log.i("WKU", "WKUData.WKUPrivateData / 대학 : " + getDepartment());
        Log.i("WKU", "WKUData.WKUPrivateData / 전공 : " + getMajor());
        Log.i("WKU", "WKUData.WKUPrivateData / 학번 : " + getStudentNo());
        Log.i("WKU", "WKUData.WKUPrivateData / 학년 : " + getYear());
        Log.i("WKU", "WKUData.WKUPrivateData / 전화 : " + getTel());
        Log.i("WKU", "WKUData.WKUPrivateData / 주소 : " + getAddress());
    }

    public void logScheduleData() {
        for (int i = 0; i < getWkuScheduleDatas().size(); i++) {
            Log.i("WKU", getWkuScheduleDatas().get(i).showData());
        }
    }
}

class WKUScholarData {
    private int year;
    private int term;
    private String title;
    private int money;

    public WKUScholarData(int year, int term, String title, int money) {
        this.year = year;
        this.term = term;
        this.title = title;
        this.money = money;
    }

    public int getYear() { return this.year; }
    public int getTerm() { return this.term; }
    public String getTitle() { return this.title; }
    public int getMoney() { return this.money; }
    public String getDate() {
        return this.year + "년도 " + this.term + "학기";
    }

    public String showData() {
        return "WKUData.WKUScholarData / 년도 : " + getYear() + " 학기 : " + getTerm() + " 장학명 : " + getTitle() + " 장학금 : " + getMoney();
    }

    private String parseMoneyForm(String strMoney) {
        StringBuffer strBufMoney = new StringBuffer(strMoney);
        int length = strMoney.length();

        for(int i = length; i >= 0; i -= 3) {
            if(i == length || i == 0) {
                continue;
            }
            strBufMoney.insert(i, ",");
        }

        return strBufMoney.toString();
    }

    public String getMoneyToString() {
        String strMoney = Integer.toString(this.money);
        parseMoneyForm(strMoney);

        return parseMoneyForm(strMoney) + "원";
    }

    public int getMoneyToInt() {
        return this.money;
    }
}

class WKUScheduleData {
    public static final int MON = 0;
    public static final int TUE = 1;
    public static final int WED = 2;
    public static final int THU = 3;
    public static final int FRI = 4;

    public static final int PERIOD_1 = 0;
    public static final int PERIOD_2 = 1;
    public static final int PERIOD_3 = 2;
    public static final int PERIOD_4 = 3;
    public static final int PERIOD_5 = 4;
    public static final int PERIOD_6 = 5;
    public static final int PERIOD_7 = 6;
    public static final int PERIOD_8 = 7;
    public static final int PERIOD_9 = 8;
    public static final int PERIOD_10 = 9;

    private String subject;
    private String place;
    private int dayOfWeek;
    private int period;
    private String subjectType;
    private String classNo;
    private String credit;

    public WKUScheduleData(String subject, String place, int dayOfWeek, int period, String subjectType, String classNo, String credit) {
        this.subject = subject;
        this.place = place;
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.subjectType = subjectType;
        this.classNo = classNo;
        this.credit = credit;
    }

    public String getSubject() {
        return this.subject;
    }
    public String getPlace() {
        return this.place;
    }
    public int getDayOfWeek() {
        return this.dayOfWeek;
    }
    public int getPeriod() {
        return this.period;
    }
    public String getSubjectType() { return this.subjectType; }
    public String getClassNo() { return this.classNo; }
    public String getCredit() { return this.credit; }

    public String showData() {
        return "WKUData.WKUScheduleData / 교과목명 : " + getSubject() + " 강의실 : " + getPlace() + " 몇요일 : " + getDayOfWeek() + " 몇교시 : " + getPeriod() + " 구분 : " + getSubjectType() + " 분반 : " + getClassNo() + " 학점 : " + getCredit();
    }
}

class WKUAttendData {
    static final int ATTEND_SCRAP_SIZE = 17;

    static final int SUBJECT_INDEX = 17;
    static final int PROFESSOR_INDEX = 18;

    static final int YEAR_INDEX = 15;
    static final int TERM_INDEX = 12;
    static final int CODELESSON_INDEX = 8;
    static final int CLASSIFY_INDEX = 9;
    static final int EMPLOYEENO_INDEX = 7;

    static final int ORTHO_INDEX = 3;

    private String subject;
    private String professor;

    // FOR HEADER COOKIE...
    private int year;
    private int term;
    private String codeLesson;
    private String classify;
    private int employeeNo;

    private ArrayList<WKUAttendDetailData> wkuAttendDetailDatas;

    public WKUAttendData(String subject, String professor, int year, int term, String codeLesson, String classify, int employeeNo) {
        this.subject = subject;
        this.professor = professor;
        this.year = year;
        this.term = term;
        this.codeLesson = codeLesson;
        this.classify = classify;
        this.employeeNo = employeeNo;

        wkuAttendDetailDatas = new ArrayList<>();
    }

    public WKUAttendData(String subject, String professor) {
        this.subject = subject;
        this.professor = professor;
        this.year = -1;
        this.term = -1;
        this.codeLesson = "";
        this.classify = "";
        this.employeeNo = -1;

        wkuAttendDetailDatas = new ArrayList<>();
    }

    public String getSubject() { return this.subject; }
    public String getProfessor() { return this.professor; }

    public int getYear() { return this.year; }
    public int getTerm() { return this.term; }
    public String getCodeLesson() { return this.codeLesson; }
    public String getClassify() { return this.classify; }
    public int getEmployeeNo() { return this.employeeNo; }

    public ArrayList<WKUAttendDetailData> getWkuAttendDetailDatas() { return this.wkuAttendDetailDatas; }

    public String showData() {
        return "WKUData.WKUAttendData / SUBJECT : " + getSubject() + " PROFESSOR : " + getProfessor() + " YEAR : " + getYear() + " TERM : " + getTerm() + " CODELESSON : " + getCodeLesson() + " CLASSIFY : " + getClassify() + " EMPLOYEENO : " + getEmployeeNo();
    }
}

class WKUAttendDetailData {
    static final int ATTEND_DETAIL_SCRAP_SIZE = 30;

    static final int WEEK_INDEX = 31;
    static final int DATE_INDEX = 19;
    static final int PERIOD_INDEX = 27;
    static final int ATTENDANCE_INDEX = 30;

    private int week;
    private String date;
    private int period;
    private String attend;

    public WKUAttendDetailData(int week, String date, int period, String attend) {
        this.week = week;
        this.date = date;
        this.period = period;
        this.attend = attend;
    }

    public int getWeek() { return this.week; }
    public String getDate() { return this.date; }
    public int getPeriod() { return  this.period; }
    public String getAttend() { return this.attend; }


    public String showData() {
        return "WKUData.WKUAttendDetailData / WEEK : " + getWeek() + " DATE : " + getDate() + " PERIOD : " + getPeriod() + " ATTEND : " + getAttend();
    }
}

class WKUGradeData {
    private int year;
    private int schoolYear;
    private int term;
    private float reqCredit;
    private float ackCredit;
    private float totalGrade;
    private float avgGrade;

    ArrayList<WKUGradeDetailData> wkuGradeDetailDatas;

    public WKUGradeData(int year, int schoolYear, int term, float reqCredit, float ackCredit, float totalGrade, float avgGrade) {
        this.year = year;
        this.schoolYear = schoolYear;
        this.term = term;
        this.reqCredit = reqCredit;
        this.ackCredit = ackCredit;
        this.totalGrade = totalGrade;
        this.avgGrade = avgGrade;

        wkuGradeDetailDatas = new ArrayList<>();
    }
    public int getYear() { return this.year; }
    public String getSchoolYear() { return this.schoolYear + ""; }
    public int getTerm() { return this.term; }
    public float getReqCredit() { return this.reqCredit; }
    public float getAckCredit() { return this.ackCredit; }
    public float getTotalGrade() { return this.totalGrade; }
    public float getAvgGrade() { return this.avgGrade; }

    public ArrayList<WKUGradeDetailData> getWkuGradeDetailDatas() { return this.wkuGradeDetailDatas; }

    public String showData() {
        return "WKUData.WKUGradeData / YEAR : " + getYear() + " SCHOOLYEAR - " + getSchoolYear() + " TERM - " + getTerm() + " REQCREDIT - " + getReqCredit() + " ACKCREDIT - " + getAckCredit() + " TOTALGRADE - " + getTotalGrade() + " AVGGRADE - " + getAvgGrade();
    }
}

class WKUGradeDetailData {
    private String category;
    private String code;
    private String subject;
    private float credit;
    private float avgGrade;
    private String markGrade;

    public WKUGradeDetailData(String category, String code, String subject, float credit, float avgGrade, String markGrade) {
        this.category = category;
        this.code = code;
        this.subject = subject;
        this.credit = credit;
        this.avgGrade = avgGrade;
        this.markGrade = markGrade;
    }

    public String getCategory() { return this.category; }
    public String getCode() { return this.code; }
    public String getSubject() { return this.subject; }
    public String getCredit() { return this.credit+""; }
    public String getAvgGrade() { return this.avgGrade+""; }
    public String getMarkGrade() { return this.markGrade; }

    public String showData() {
        return "WKUData.WKUGradeDetailData / CATEGORY - " + getCategory() + " CODE - " + getCode() + " SUBJECT - " + getSubject() + " CREDIT - " + getCredit() + " AVGGRADE - " + getAvgGrade() + " MARKGRADE - " + getMarkGrade();
    }
}

class WKUGradeCurData {
    private String category;
    private String subject;
    private float credit;
    private float midExam;
    private float finalExam;
    private float attendAssign;
    private float other;
    private float avgGrade;
    private String markGrade;

    public WKUGradeCurData(String category, String subject, float credit, float midExam, float finalExam, float attendAssign, float other, float avgGrade, String markGrade) {
        this.category = category;
        this.subject = subject;
        this.credit = credit;
        this.midExam = midExam;
        this.finalExam = finalExam;
        this.attendAssign = attendAssign;
        this.other = other;
        this.avgGrade = avgGrade;
        this.markGrade = markGrade;
    }

    public String getCategory() { return this.category; }
    public String getSubject() { return this.subject; }
    public String getCredit() { return this.credit + ""; }
    public String getMidExam() { return  this.midExam + ""; }
    public String getFinalExam() { return this.finalExam + ""; }
    public String getAttendAssign() { return this.attendAssign + ""; }
    public String getOther() { return this.other + ""; }
    public String getAvgGrade() { return this.avgGrade + ""; }
    public String getMarkGrade() { return this.markGrade; }

    public String showData() {
        return "WKUData.WKUGradeCurData / CATEGORY : " + getCategory() + " SUBJECT : " + getSubject() + " CREDIT : " + getCredit() + " MIDEXAM : " + getMidExam() + " FINALEXAM : " + getFinalExam() + " ATTENDASSIGN : " + getAttendAssign() + " OTHER : " + getOther() + " AVGGRADE : " + getAvgGrade() + " MARKGRADE : " + getMarkGrade();
    }
}

class WKUGraduationData {
    private int graduationType;

    private float minGraduationCredit;
    private float curGraduationCredit;

    private float curMajorCredit;
    private float minMajorCredit;
    private float minMajorCreditForDouble;

    private float curMajorBasisCredit;
    private float minMajorBasisCredit;

    private float curGeneralCredit;
    private float minGeneralCredit;
    private float maxGeneralCredit;

    private float curGeneralCompulsoryCredit;
    private float minGeneralCompulsoryCredit;

    private float curGeneralAffiliationCredit;
    private float minGeneralAffiliationCredit;

    private float curGeneralLanguageCredit;
    private float minGeneralLanguageCredit;

    private float curGeneralEnglishCredit;
    private float minGeneralEnglishCredit;

    private float curGeneralSoftwareCredit;
    private float minGeneralSoftwareCredit;

    private float curGeneralHumanityCredit;
    private float minGeneralHumanityCredit;

    private float curGeneralStartUpCredit;
    private float minGeneralStartUpCredit;

    private float curGeneralCreativityCredit;
    private float minGeneralCreativityCredit;

    private ArrayList<WKUGraduationCompulsorySubjectData> graduationCreditLists;
    private ArrayList<WKUGraduationCompulsorySubjectData> majorCreditLists;
    private ArrayList<WKUGraduationCompulsorySubjectData> majorBasisCreditLists;
    private ArrayList<WKUGraduationCompulsorySubjectData> generalCreditLists;
    private ArrayList<WKUGraduationCompulsorySubjectData> generalAffiliationCreditLists;
    private ArrayList<Integer> compulsoryCourseCodes;

    public WKUGraduationData() {
        this.graduationCreditLists = new ArrayList<>();
        this.majorCreditLists = new ArrayList<>();
        this.majorBasisCreditLists = new ArrayList<>();
        this.generalCreditLists = new ArrayList<>();
        this.generalAffiliationCreditLists = new ArrayList<>();
    }

    public WKUGraduationData(
            int graduationType,
            float minGraduationCredit,
            float minMajorCredit,
            float minMajorCreditForDouble,
            float minMajorBasisCredit,
            float minGeneralCredit,
            float minGeneralCompulsoryCredit,
            float minGeneralAffiliationCredit,
            float minGeneralLanguageCredit,
            float minGeneralEnglishCredit,
            float minGeneralSoftwareCredit,
            float minGeneralHumanityCredit,
            float minGeneralStartUpCredit,
            float minGeneralCreativityCredit,
            float maxGeneralCredit) {
        this.graduationType = graduationType;

        this.minGraduationCredit = minGraduationCredit;

        this.minMajorCredit = minMajorCredit;
        this.minMajorBasisCredit = minMajorBasisCredit;

        this.minGeneralCredit = minGeneralCredit;
        this.minGeneralCompulsoryCredit = minGeneralCompulsoryCredit;
        this.minGeneralAffiliationCredit = minGeneralAffiliationCredit;
        this.minGeneralLanguageCredit = minGeneralLanguageCredit;
        this.minGeneralEnglishCredit = minGeneralEnglishCredit;
        this.minGeneralSoftwareCredit = minGeneralSoftwareCredit;
        this.minGeneralHumanityCredit = minGeneralHumanityCredit;
        this.minGeneralStartUpCredit = minGeneralStartUpCredit;
        this.minGeneralCreativityCredit = minGeneralCreativityCredit;
        this.maxGeneralCredit = maxGeneralCredit;

        this.graduationCreditLists = new ArrayList<>();
        this.majorCreditLists = new ArrayList<>();
        this.majorBasisCreditLists = new ArrayList<>();
        this.generalCreditLists = new ArrayList<>();
        this.generalAffiliationCreditLists = new ArrayList<>();

        this.compulsoryCourseCodes = new ArrayList<>();
    }
    public void set(int graduationType, int minGraduationCredit, int minMajorCredit, int minMajorCreditForDouble, int minMajorBasisCredit, int minGeneralCredit, int minGeneralCompulsoryCredit, int minGeneralAffiliationCredit, int minGeneralLanguageCredit, int minGeneralEnglishCredit, int minGeneralSoftwareCredit, int minGeneralHumanityCredit, int minGeneralStartUpCredit, int minGeneralCreativityCredit, int maxGeneralCredit) {
        this.graduationType = graduationType;
        this.minGraduationCredit = minGraduationCredit;
        this.minMajorCredit = minMajorCredit;
        this.minMajorCreditForDouble = minMajorCreditForDouble;
        this.minMajorBasisCredit = minMajorBasisCredit;
        this.minGeneralCredit = minGeneralCredit;
        this.minGeneralCompulsoryCredit = minGeneralCompulsoryCredit;
        this.minGeneralAffiliationCredit = minGeneralAffiliationCredit;
        this.minGeneralLanguageCredit = minGeneralLanguageCredit;
        this.minGeneralEnglishCredit = minGeneralEnglishCredit;
        this.minGeneralSoftwareCredit = minGeneralSoftwareCredit;
        this.minGeneralHumanityCredit = minGeneralHumanityCredit;
        this.minGeneralStartUpCredit = minGeneralSoftwareCredit;
        this.minGeneralCreativityCredit = minGeneralCreativityCredit;
        this.maxGeneralCredit = maxGeneralCredit;
    }

    public int getGraduationType() { return this.graduationType; }
    public void setGraduationType(int type) { this.graduationType = type; }

    public float getMinGraduationCredit() { return this.minGraduationCredit; }
    public float getCurGraduationCredit() { return curGraduationCredit; }
    public void setCurGraduationCredit(float curGraduationCredit) { this.curGraduationCredit = curGraduationCredit; }

    public float getMinMajorCredit() { return this.minMajorCredit; }
    public float getMinMajorCreditForDouble() { return this.minMajorCreditForDouble; }
    public float getCurMajorCredit() { return curMajorCredit; }
    public void setCurMajorCredit(float curMajorCredit) { this.curMajorCredit = curMajorCredit; }

    public float getMinMajorBasisCredit() { return this.minMajorBasisCredit; }
    public float getCurMajorBasisCredit() { return curMajorBasisCredit; }
    public void setCurMajorBasisCredit(float curMajorBasisCredit) { this.curMajorBasisCredit = curMajorBasisCredit; }

    public float getMinGeneralCredit() { return this.minGeneralCredit; }
    public float getMaxGeneralCredit() { return this.maxGeneralCredit; }
    public float getCurGeneralCredit() { return curGeneralCredit; }
    public void setCurGeneralCredit(float curGeneralCredit) { this.curGeneralCredit = curGeneralCredit; }

    public float getMinGeneralCompulsoryCredit() { return this.minGeneralCompulsoryCredit; }
    public float getCurGeneralCompulsoryCredit() { return curGeneralCompulsoryCredit; }
    public void setCurGeneralCompulsoryCredit(float curGeneralCompulsoryCredit) { this.curGeneralCompulsoryCredit = curGeneralCompulsoryCredit; }

    public float getMinGeneralAffiliationCredit() { return this.minGeneralAffiliationCredit; }
    public float getCurGeneralAffiliationCredit() { return curGeneralAffiliationCredit; }
    public void setCurGeneralAffiliationCredit(float curGeneralAffiliationCredit) { this.curGeneralAffiliationCredit = curGeneralAffiliationCredit; }

    public float getMinGeneralLanguageCredit() { return this.minGeneralLanguageCredit; }
    public float getCurGeneralLanguageCredit() { return curGeneralLanguageCredit; }
    public void setCurGeneralLanguageCredit(float curGeneralLanguageCredit) { this.curGeneralLanguageCredit = curGeneralLanguageCredit; }

    public float getMinGeneralEnglishCredit() { return this.minGeneralEnglishCredit; }
    public float getCurGeneralEnglishCredit() { return curGeneralEnglishCredit; }
    public void setCurGeneralEnglishCredit(float curGeneralEnglishCredit) { this.curGeneralEnglishCredit = curGeneralEnglishCredit; }

    public float getMinGeneralSoftwareCredit() { return this.minGeneralSoftwareCredit; }
    public float getCurGeneralSoftwareCredit() { return curGeneralSoftwareCredit; }
    public void setCurGeneralSoftwareCredit(float curGeneralSoftwareCredit) { this.curGeneralSoftwareCredit = curGeneralSoftwareCredit; }

    public float getMinGeneralHumanityCredit() { return this.minGeneralHumanityCredit; }
    public float getCurGeneralHumanityCredit() { return curGeneralHumanityCredit; }
    public void setCurGeneralHumanityCredit(float curGeneralHumanityCredit) { this.curGeneralHumanityCredit = curGeneralHumanityCredit; }

    public float getMinGeneralStartUpCredit() { return this.minGeneralStartUpCredit; }
    public float getCurGeneralStartUpCredit() { return curGeneralStartUpCredit; }
    public void setCurGeneralStartUpCredit(float curGeneralStartUpCredit) { this.curGeneralStartUpCredit = curGeneralStartUpCredit; }

    public float getMinGeneralCreativityCredit() { return this.minGeneralCreativityCredit; }
    public float getCurGeneralCreativityCredit() { return this.curGeneralCreativityCredit; }
    public void setCurGeneralCreativityCredit(float curGeneralCreativityCredit) { this.curGeneralCreativityCredit = curGeneralCreativityCredit; }

    public ArrayList<WKUGraduationCompulsorySubjectData> getGraduationCreditLists() { return graduationCreditLists; }
    public ArrayList<WKUGraduationCompulsorySubjectData> getMajorCreditLists() { return majorCreditLists; }
    public ArrayList<WKUGraduationCompulsorySubjectData> getMajorBasisCreditLists() { return majorBasisCreditLists; }
    public ArrayList<WKUGraduationCompulsorySubjectData> getGeneralCreditLists() { return generalCreditLists; }
    public ArrayList<WKUGraduationCompulsorySubjectData> getGeneralAffiliationCreditLists() { return generalAffiliationCreditLists; }

    public ArrayList<Integer> getCompulsoryCourseCodes() { return this.compulsoryCourseCodes; }
}

class WKUGraduationCompulsoryGradeData {
    private String title;
    private String explain;
    private float curGrade;
    private float aimGrade;
    private boolean status;

    public WKUGraduationCompulsoryGradeData(String title, String explain, float curGrade, float aimGrade) {
        this.title = title;
        this.explain = explain;
        this.curGrade = curGrade;
        this.aimGrade = aimGrade;
        this.status = false;

        if(this.curGrade > this.aimGrade) {
            this.status = true;
        }
    }

    public String getTitle() { return title; }
    public String getExplain() { return explain; }
    public float getCurGrade() { return curGrade; }
    public float getAimGrade() { return aimGrade; }
    public boolean isStatus() { return status; }
}

class WKUGraduationCompulsorySubjectData {
    private String code;
    private String category;
    private String title;
    private float credit;
    private boolean status;

    public WKUGraduationCompulsorySubjectData(String code, String category, float credit, String title) {
        this.code = code;
        this.category = category;
        this.credit = credit;
        this.title = title;
    }

    public String getCode() { return code; }
    public String getCategory() { return category; }
    public float getCredit() { return credit; }
    public String getTitle() { return title; }
}

class WKUBBSData extends WKUAlarmData {
    static final int PAGE_MAX = 2;
    static final int ROW_MAX = 24;
    static final int OCCUR_SCRAP_SIZE = 10;
    static final int NOTICE_VALUE = -100;
    static final int NEW_VALUE = -200;

    static final int SEARCH_MODE = 100;
    static final int SCROLL_MODE = 101;

    private int id;
    private String author;
    private String date;
    private int hits;
    private long cid;

    public WKUBBSData(int alarmId, int category, int id, String title, String author, String date, int hits, long cid, int isClick) {
        super(alarmId, category, title, isClick);
        this.id = id;
        this.author = author;
        this.date = date;
        this.hits = hits;
        this.cid = cid;
    }

    public int getId() { return this.id; }
    public String getAuthor() { return this.author; }
    public String getDate() { return this.date; }
    public int getHits() { return this.hits; }
    public long getCid() { return this.cid; }

    public String getIdForComp() { return this.id + cid + ""; }

    public String getType() {
        if(this.id == (-100)) {
            return "공지";
        } else if(this.id == (-200)) {
            return "NEW";
        } else {
            return "";
        }
    }

    public String showData() {
        return "WKUData.WKUBBSData / ID : " + getId() + " TITLE : " + getTitle() + " AUTHOR : " + getAuthor() + " DATE : " + getDate() + " VIEW :" + getHits() + " CID : " + getCid();
    }
}

class WKUDormData {
    private String date;
    private String reason;
    private String location;
    private String emgTel;

    public WKUDormData(String date, String reason, String location, String emgTel) {
        this.date = date;
        this.reason = reason;
        this.location = location;
        this.emgTel = emgTel;
    }

    public String getDate() { return this.date; }
    public String getReason() { return this.reason; }
    public String getLocation() { return this.location; }
    public String getEmgTel() { return this.emgTel; }

    public String showData() {
        return "WKUData.WKUDormData / DATE : " + getDate() + " REASON : " + getReason() + " LOCATION : " + getLocation() + " EMGTEL : " + getEmgTel();
    }

    public String getDateToYYYYMMDD() {
        String year = this.date.substring(0, 4);
        String month = this.date.substring(6, 8);
        String day = this.date.substring(10, 12);

        String date = year + month + day;

        return date;
    }
}

class WKUEClassData {
    private String title;
    private String professor;
    private String mp;
    private String sp;
    private String ci;
    private String ui;
    private String sn;

    private ArrayList<WKUEClassDetailNotiData> wkuEClassDetailNotiDatas;
    private ArrayList<WKUEClassDetailPdsData> wkuEClassDetailPdsDatas;
    private ArrayList<WKUEClassDetailReportData> wkuEClassDetailReportDatas;
    private ArrayList<WKUEClassDetailLectureData> wkuEClassDetailLectureDatas;


    public WKUEClassData(String title, String professor, String mp, String sp, String ci, String ui, String sn) {
        this.title = title;
        this.professor = professor;
        this.mp = mp;
        this.sp = sp;
        this.ci = ci;
        this.ui = ui;
        this.sn = sn;

        wkuEClassDetailNotiDatas = new ArrayList<>();
        wkuEClassDetailPdsDatas = new ArrayList<>();
        wkuEClassDetailReportDatas = new ArrayList<>();
        wkuEClassDetailLectureDatas = new ArrayList<>();
    }

    public String getTitle() { return this.title; }
    public String getProfessor() { return this.professor; }
    public String getMp() { return this.mp; }
    public String getSp() { return this.sp; }
    public String getCi() { return this.ci; }
    public String getUi() { return this.ui; }
    public String getSn() { return this.sn; }

    public ArrayList<WKUEClassDetailNotiData> getWkuEClassDetailNotiDatas() { return wkuEClassDetailNotiDatas; }
    public ArrayList<WKUEClassDetailPdsData> getWkuEClassDetailPdsDatas() { return wkuEClassDetailPdsDatas; }
    public ArrayList<WKUEClassDetailReportData> getWkuEClassDetailReportDatas() { return wkuEClassDetailReportDatas; }
    public ArrayList<WKUEClassDetailLectureData> getWkuEClassDetailLectureDatas() { return wkuEClassDetailLectureDatas; }

    public String showData() {
        return "WKUData.WKUEClassData / TITLE : " + getTitle() + " PROFESSOR : " + getProfessor() + " MP : " + getMp() + " SP : " + getSp() + " CI : " + getCi() + " UI : " + getUi() + " SN : " + getSn();
    }
}

class WKUEClassDetailNotiData extends WKUAlarmData {
    private String author;
    private String date;
    private boolean file;
    private String hits;

    public WKUEClassDetailNotiData(int alarmId, String author, String date, String title, boolean file, String hits, int isClick) {
        super(alarmId, WKUAlarmData.CATEGORY_ECLASS_DETAIL_NOTI, title, isClick);
        this.author = author;
        this.date = date;
        this.file = file;
        this.hits = hits;
    }

    public String getAuthor() { return author; }
    public String getDate() { return date; }
    public boolean isFile() { return file; }
    public String getHits() { return hits; }

    public String getId() { return date + title + hits; }

    public String showData() {
        return "WKUData.WKUEClassDetailNotiData / AUTHOR : " + getAuthor() + " DATE : " + getDate() + " TITLE : " + getTitle() + " FILE : " + isFile() + " HIT : " + getHits();
    }
}

class WKUEClassDetailPdsData extends WKUAlarmData {
    private String file;
    private String size;
    private String date;
    private String author;
    private String hits;

    public WKUEClassDetailPdsData(int alarmId, String title, String file, String size, String date, String author, String hits, int isClick) {
        super(alarmId, WKUAlarmData.CATEGORY_ECLASS_DETAIL_PDS, title, isClick);
        this.file = file;
        this.size = size;
        this.date = date;
        this.author = author;
        this.hits = hits;
    }

    public String getFile() { return file; }
    public String getSize() { return size; }
    public String getDate() { return date; }
    public String getAuthor() { return author; }
    public String getHits() { return hits; }

    public String getId() { return title + date + hits; }

    public String showData() {
        return "WKUData.WKUEClassDetailPdsData / TITLE : " + getTitle() + " FILE : " + getFile() + " SIZE : " + getSize() + " DATE : " + getDate() + " AUTHOR : " + getAuthor() + " HIT : " + getHits();
    }
}

class WKUEClassDetailReportData extends WKUAlarmData {
    private String date;
    private String grade;
    private boolean submit;

    public WKUEClassDetailReportData(int alarmId, String title, String date, String grade, boolean submit, int isClick) {
        super(alarmId, WKUAlarmData.CATEGORY_ECLASS_DETAIL_REPORT, title, isClick);
        this.date = date;
        this.grade = grade;
        this.submit = submit;
    }

    public String getDate() { return date; }
    public String getGrade() { return grade; }
    public boolean isSubmit() { return submit; }

    public String getId() { return title + date + grade; }

    public String showData() {
        return "WKUData.WKUEClassDetailReportData / TITLE : " + getTitle() + " DATE : " + getDate() + " GRADE : " + getGrade() + " SUBMIT : " + isSubmit();
    }
}

class WKUEClassDetailLectureData extends WKUAlarmData {
    private String week;
    private ArrayList<WKUEClassDetailLectureContentData> wkuEClassDetailLectureContentDatas;

    public WKUEClassDetailLectureData(int alarmId, String week, String title, int isClick) {
        super(alarmId, WKUAlarmData.CATEGORY_ECLASS_DETAIL_LECTURE, title, isClick);
        this.week = week;

        wkuEClassDetailLectureContentDatas = new ArrayList<>();
    }

    public String getWeek() { return week; }
    public String getId() { return week + title; }

    public ArrayList<WKUEClassDetailLectureContentData> getWkuEClassDetailLectureContentDatas() { return wkuEClassDetailLectureContentDatas; }

    public String showData() {
        return "WKUData.WKUEClassDetailLectureData / WEEK : " + getWeek() + " TITLE : " + getTitle();
    }
}

class WKUEClassDetailLectureContentData {
    private String period;
    private boolean attend;
    private String url;

    public WKUEClassDetailLectureContentData(String period, boolean attend, String url) {
        this.period = period;
        this.attend = attend;
        this.url = url;
    }

    public String getPeriod() { return period; }
    public boolean isAttend() { return attend; }
    public String getUrl() { return url; }

    public String showData() {
        return "WKUData.WKUEClassDetailLectureContentData / PERIOD : " + getPeriod() + " ATTEND : " + isAttend() + " URL : " + getUrl();
    }
}

class WKUAlarmData {
    public static final int ALARM_PERIOD = 1000 * 60 * 60; // 1 HOURS...
    public static final int ALARM_PERIOD_FOR_DEBUG = 1000 * 60; // 60 SECONDS...

    public static final int CATEGORY_BBS_NOTI = 1;
    public static final int CATEGORY_BBS_ROOM = 6;
    public static final int CATEGORY_BBS_MARKET = 7;
    public static final int CATEGORY_BBS_JOB = 8;
    public static final int CATEGORY_ECLASS_DETAIL_NOTI = 2;
    public static final int CATEGORY_ECLASS_DETAIL_PDS = 3;
    public static final int CATEGORY_ECLASS_DETAIL_REPORT = 4;
    public static final int CATEGORY_ECLASS_DETAIL_LECTURE = 5;

    protected int alarmId;
    protected int category;
    protected String title;
    protected int isClick;

    public WKUAlarmData() { }

    public WKUAlarmData(int alarmId, int category, String title, int isClick) {
        this.alarmId = alarmId;
        this.category = category;
        this.title = title;
        this.isClick = isClick;
    }

    public int getAlarmId() { return this.alarmId; }
    public int getCategory() { return this.category; }
    public String getTitle() { return this.title; }
    public int getIsClick() { return this.isClick; }

    public String showData() { return "CATEGORY : " + category + " TITLE : " + title; }
}

class WKUSettingData {
    public static final int BBS_ALARM_NOTI = 1;
    public static final int BBS_ALARM_ROOM = 2;
    public static final int BBS_ALARM_MARKET = 3;
    public static final int BBS_ALARM_JOB = 4;

    private int id;
    private String title;
    private String detail;
    private boolean power;

    public WKUSettingData(int id, String title, String detail, boolean power) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.power = power;
    }

    public int getId() { return this.id; }
    public String getTitle() { return title; }
    public String getDetail() { return detail; }
    public boolean isPower() { return power; }
    public int getPowerToInt() {
        if(power) {
            return 1;
        } else {
            return 0;
        }
    }
}
