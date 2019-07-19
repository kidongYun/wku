package com.example.yun.wku;

import android.os.Handler;
import android.os.Message;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WKUScrapingEngine extends Thread {
    public static final int SCRAPING_LOGIN_MODE = 1;
    public static final int SCRAPING_ATTEND_MODE = 2;
    public static final int SCRAPING_SCHOLAR_MODE = 3;
    public static final int SCRAPING_GRADE_MODE = 4;
    public static final int SCRAPING_BBS_NOTI_SEARCH_MODE = 5;
    public static final int SCRAPING_BBS_ROOM_SEARCH_MODE = 6;
    public static final int SCRAPING_BBS_JOB_SEARCH_MODE = 7;
    public static final int SCRAPING_BBS_MARKET_SEARCH_MODE = 8;
    public static final int SCRAPING_BBS_MODE = 13;
    public static final int SCRAPING_BBS_NOTI_MODE = 9;
    public static final int SCRAPING_BBS_ROOM_MODE = 10;
    public static final int SCRAPING_BBS_JOB_MODE = 11;
    public static final int SCRAPING_BBS_MARKET_MODE = 12;
    public static final int SCRAPING_BBS_DETAIL_DOWNLOAD_MODE = 14;
    public static final int SCRAPING_DORM_MODE = 15;
    public static final int SCRAPING_DORM_ADD_MODE = 16;
    public static final int SCRAPING_DORM_CANCEL_MODE = 17;
    public static final int SCRAPING_ECLASS_MODE = 18;
    public static final int SCRAPING_ECLASS_DETAIL_MODE = 19;

    public static final int SCRAPING_FINISH_MODE = 100;
    public static final int SCRAPING_NOTI_SEARCH_FINISH_MODE = 101;
    public static final int SCRAPING_ROOM_SEARCH_FINISH_MODE = 102;
    public static final int SCRAPING_JOB_SEARCH_FINISH_MODE = 103;
    public static final int SCRAPING_MARKET_SEARCH_FINISH_MODE = 104;
    public static final int SCRAPING_DORM_FINISH_MODE = 105;
    public static final int SCRAPING_DORM_ADD_FINISH_MODE = 106;
    public static final int SCRAPING_DORM_CANCEL_FINISH_MODE = 107;

    public static final int SCRAPING_ERROR_MODE = 200;

    private String wkuId;
    private String wkuPw;

    private String jSessionId;
    private String wkuTokenKey;

    private String ASPSESSIONID_KEY;
    private String ASPSESSIONID_VALUE;

    private int paramTypeInt;
    private String paramTypeString;

    private String outDate;
    private String reason;
    private String location;
    private String emgTel;

    private int MODE_FLAG;

    private Handler handler;
    private WKUData wkuData;

    public WKUData getWkuData() { return this.wkuData; }

    public WKUScrapingEngine(int MODE_FLAG, Handler handler) {
        this.MODE_FLAG = MODE_FLAG;
        this.handler = handler;

        wkuData = new WKUData();
    }

    public WKUScrapingEngine(int MODE_FLAG, Handler handler, String paramTypeString) {
        this.MODE_FLAG = MODE_FLAG;
        this.handler = handler;
        this.paramTypeString = paramTypeString;

        wkuData = new WKUData();
    }

    public WKUScrapingEngine(int MODE_FLAG, Handler handler, String wkuId, String wkuPw, String jSessionId, String wkuTokenKey) {
        this.MODE_FLAG = MODE_FLAG;
        this.handler = handler;
        this.wkuId = wkuId;
        this.wkuPw = wkuPw;
        this.jSessionId = jSessionId;
        this.wkuTokenKey = wkuTokenKey;

        wkuData = new WKUData();
    }

    public WKUScrapingEngine(int MODE_FLAG, Handler handler, String wkuId, String wkuPw, String jSessionId, String wkuTokenKey, int paramTypeInt) {
        this.MODE_FLAG = MODE_FLAG;
        this.handler = handler;
        this.wkuId = wkuId;
        this.wkuPw = wkuPw;
        this.jSessionId = jSessionId;
        this.wkuTokenKey = wkuTokenKey;
        this.paramTypeInt = paramTypeInt;

        wkuData = new WKUData();
    }

    public WKUScrapingEngine(int MODE_FLAG, Handler handler, String wkuId, String wkuPw, String jSessionId, String wkuTokenKey, String paramTypeString) {
        this.MODE_FLAG = MODE_FLAG;
        this.handler = handler;
        this.wkuId = wkuId;
        this.wkuPw = wkuPw;
        this.jSessionId = jSessionId;
        this.wkuTokenKey = wkuTokenKey;
        this.paramTypeString = paramTypeString;

        wkuData = new WKUData();
    }

    public WKUScrapingEngine(int MODE_FLAG, Handler handler, String wkuId, String wkuPw, String jSessionId, String wkuTokenKey, String outDate, String reason, String location, String emgTel) {
        this.MODE_FLAG = MODE_FLAG;
        this.handler = handler;
        this.wkuId = wkuId;
        this.wkuPw = wkuPw;
        this.jSessionId = jSessionId;
        this.wkuTokenKey = wkuTokenKey;
        this.outDate = outDate;
        this.reason = reason;
        this.location = location;
        this.emgTel = emgTel;

        wkuData = new WKUData();
    }

    @Override
    public void run() {
        super.run();
        Log.i("WKU", "WKUScrapingEngine: SCRAPING START... MODE : " + MODE_FLAG);
        choiceMode(MODE_FLAG);
    }

    private void choiceMode(int mode) {
        Map<String, String> loginCookie = new HashMap<>();
        loginCookie.put("JSESSIONID", jSessionId + ";");
        loginCookie.put("wkuTokenKey", wkuTokenKey + ";");

        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36";

        switch (mode) {
            case SCRAPING_LOGIN_MODE:
                loginMode(userAgent, loginCookie);
                break;
            case SCRAPING_ATTEND_MODE:
                attendMode(userAgent, loginCookie);
                break;
            case SCRAPING_SCHOLAR_MODE:
                scholarMode(userAgent, loginCookie);
                break;
            case SCRAPING_GRADE_MODE:
                gradeMode(userAgent, loginCookie);
                break;
            case SCRAPING_BBS_MODE:
                bbsMode(userAgent, loginCookie);
                break;
            case SCRAPING_BBS_NOTI_MODE:
                bbsNotiMode(userAgent, loginCookie, paramTypeInt);
                break;
            case SCRAPING_BBS_ROOM_MODE:
                bbsRoomMode(userAgent, loginCookie, paramTypeInt);
                break;
            case SCRAPING_BBS_JOB_MODE:
                bbsJobMode(userAgent, loginCookie, paramTypeInt);
                break;
            case SCRAPING_BBS_MARKET_MODE:
                bbsMarketMode(userAgent, loginCookie, paramTypeInt);
                break;
            case SCRAPING_BBS_NOTI_SEARCH_MODE:
                bbsNotiSearchMode(userAgent, loginCookie, paramTypeString);
                break;
            case SCRAPING_BBS_ROOM_SEARCH_MODE:
                bbsRoomSearchMode(userAgent, loginCookie, paramTypeString);
                break;
            case SCRAPING_BBS_JOB_SEARCH_MODE:
                bbsJobSearchMode(userAgent, loginCookie, paramTypeString);
                break;
            case SCRAPING_BBS_MARKET_SEARCH_MODE:
                bbsMarketSearchMode(userAgent, loginCookie, paramTypeString);
                break;
            case SCRAPING_BBS_DETAIL_DOWNLOAD_MODE:
                bbsDetailDownloadMode(userAgent, loginCookie, paramTypeString);
                break;
            case SCRAPING_DORM_MODE:
                dormMode(userAgent, loginCookie);
                break;
            case SCRAPING_DORM_ADD_MODE:
                dormAddMode(userAgent, loginCookie, outDate, reason, location, emgTel);
                break;
            case SCRAPING_DORM_CANCEL_MODE:
                dormCancelMode(userAgent, loginCookie, outDate, reason, location, emgTel);
                break;
            case SCRAPING_ECLASS_MODE:
                eClassMode(userAgent, paramTypeString);
                break;
        }
    }

    private void loginMode(String userAgent, Map<String, String> loginCookie) {
        privateScrapData(userAgent, loginCookie);
        scheduleScrapData(userAgent, loginCookie);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void attendMode(String userAgent, Map<String, String> loginCookie) {
        attendScrapData(userAgent, loginCookie, paramTypeInt);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void scholarMode(String userAgent, Map<String, String> loginCookie) {
        scholarScrapData(userAgent, loginCookie);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void gradeMode(String userAgent, Map<String, String> loginCookie) {
        gradeScrapData(userAgent, loginCookie);
        gradeCurScrapData(userAgent, loginCookie);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsMode(String userAgent, Map<String, String> loginCookie) {
        bbsNotiScrapData(userAgent, loginCookie, 1);
        bbsRoomScrapData(userAgent, loginCookie, 1);
        bbsJobScrapData(userAgent, loginCookie, 1);
        bbsMarketScrapData(userAgent, loginCookie, 1);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsNotiMode(String userAgent, Map<String, String> loginCookie, int pageIndex) {
        bbsNotiScrapData(userAgent, loginCookie, pageIndex);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsRoomMode(String userAgent, Map<String, String> loginCookie, int pageIndex) {
        bbsRoomScrapData(userAgent, loginCookie, pageIndex);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsJobMode(String userAgent, Map<String, String> loginCookie, int pageIndex) {
        bbsJobScrapData(userAgent, loginCookie, pageIndex);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsMarketMode(String userAgent, Map<String, String> loginCookie, int pageIndex) {
        bbsMarketScrapData(userAgent, loginCookie, pageIndex);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsNotiSearchMode(String userAgent, Map<String, String> loginCookie, String sKey) {
        bbsNotiSearchScrapData(userAgent, loginCookie, sKey);

        Message msg = new Message();
        msg.what = SCRAPING_NOTI_SEARCH_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsRoomSearchMode(String userAgent, Map<String, String> loginCookie, String sKey) {
        bbsRoomSearchScrapData(userAgent, loginCookie, sKey);

        Message msg = new Message();
        msg.what = SCRAPING_ROOM_SEARCH_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsJobSearchMode(String userAgent, Map<String, String> loginCookie, String sKey) {
        bbsJobSearchScrapData(userAgent, loginCookie, sKey);

        Message msg = new Message();
        msg.what = SCRAPING_JOB_SEARCH_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsMarketSearchMode(String userAgent, Map<String, String> loginCookie, String sKey) {
        bbsMarketSearchScrapData(userAgent, loginCookie, sKey);

        Message msg = new Message();
        msg.what = SCRAPING_MARKET_SEARCH_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void bbsDetailDownloadMode(String userAgent, Map<String, String> loginCookie, String url) {
        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        msg.obj = bbsDetailDownloadData(userAgent, loginCookie, url);
        handler.sendMessage(msg);
    }

    private void dormMode(String userAgent, Map<String, String> loginCookie) {
        dormScrapData(userAgent, loginCookie);

        Message msg = new Message();
        msg.what = SCRAPING_DORM_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void dormAddMode(String userAgent, Map<String, String> loginCookie, String outDate, String reason, String location, String emgTel) {

        if(dormAddScrapData(userAgent, loginCookie, outDate, reason, location, emgTel)) {
            Message msg = new Message();
            msg.what = SCRAPING_DORM_ADD_FINISH_MODE;
            msg.obj = SCRAPING_DORM_ADD_MODE;
            handler.sendMessage(msg);
        } else {
            Message msg = new Message();
            msg.what = SCRAPING_ERROR_MODE;
            handler.sendMessage(msg);
        }
    }

    private void dormCancelMode(String userAgent, Map<String, String> loginCookie, String outDate, String reason, String location, String emgTel) {
        dormCancelScrapData(userAgent, loginCookie, outDate, reason, location, emgTel);

        Message msg = new Message();
        msg.what = SCRAPING_DORM_CANCEL_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void eClassMode(String userAgent, String studentNo) {
        eClassScrapData(userAgent, studentNo);

        Message msg = new Message();
        msg.what = SCRAPING_FINISH_MODE;
        handler.sendMessage(msg);
    }

    private void privateScrapData(String userAgent, Map<String, String> loginCookie) {

        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/Service/Stud/Resume/resume.jsp?sm=3")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .get();

            Elements nameElement = doc.select("form[name=studentName] tr:nth-child(1) > td:nth-child(4)");
            Elements deptElement = doc.select("form[name=studentName] tr:nth-child(4) > td:nth-child(2)");
            Elements majorElement = doc.select("form[name=studentName] tr:nth-child(4) > td:nth-child(4)");
            Elements gradeNumElement = doc.select("form[name=studentName] tr:nth-child(1) > td:nth-child(2)");
            Elements yearElement = doc.select("form[name=studentName] tr:nth-child(3) > td:nth-child(2)");
            Element telElement = doc.select("form[name=studentJuso] tr:nth-child(5) > td:nth-child(2) > input").first();
            Elements addrElement1 = doc.select("input[name=fullAddress]");
            Elements addrElement2 = doc.select("form[name=studentJuso] tr:nth-child(2) > td:nth-child(2) > input");

            String addrElementValue = "";

            if(addrElement2.val().equals("")) {
                addrElementValue = addrElement1.val();
            } else {
                addrElementValue = addrElement2.val();
            }

            doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/CommonServ/dormitory/stud/goOutList.jsp")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .ignoreContentType(true)
                    .get();

            Elements isDormElement = doc.select("script");

            String isDorm  = "";

            if(isDormElement.html().charAt(0) == 'f') {
                isDorm = "YES";
            } else if(isDormElement.html().charAt(0) == 'a'){
                isDorm = "NO";
            }

            wkuData.getWkuPrivateData()
                    .setWkuId(wkuId)
                    .setWkuPw(wkuPw)
                    .setName(nameElement.html())
                    .setDepartment(deptElement.html())
                    .setMajor(majorElement.html())
                    .setStudentNo(gradeNumElement.html())
                    .setYear(yearElement.html())
                    .setTel(telElement.attr("value"))
                    .setAddress(addrElementValue)
                    .setIsDorm(isDorm)
                    .setJSessionId(jSessionId)
                    .setWkuTokenKey(wkuTokenKey);

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUPrivateData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUPrivateData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUPrivateData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
        }  catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUPrivateData() -> SocketException : 네트워크 전환이 이루어집니다!!");
        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUPrivateData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            e.printStackTrace();
        }
    }

    private void scheduleScrapData(String userAgent, Map<String, String> loginCookie) {
        int length = 0;
        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/Service/Stud/Sugang/Request/requestList.jsp?sm=3")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .get();

            int i=2;

            while(true) {
                Elements idElements = doc.select("table:eq(4) > tbody > tr:eq(" + i + ") > td:nth-child(2) > font");

                if(idElements.html() == "") {
                    break;
                }

                Elements subjectElements = doc.select("table:eq(4) > tbody > tr:eq(" + i + ") > td > font > a");

                if(subjectElements.html() == "") {
                    subjectElements = doc.select("table:eq(4) > tbody > tr:eq(" + i + ") > td:nth-child(3) > font");
                }

                Elements placeElements = doc.select("table:eq(4) > tbody > tr:eq(" + i + ") > td:nth-child(9) > font");
                Elements dateElements = doc.select("table:eq(4) > tbody > tr:eq(" + i + ") > td:nth-child(6) > font");
                Elements subjectTypeElements = doc.select("table:eq(4) > tbody > tr:eq(" + i + ") > td:nth-child(1) > font");
                Elements classNoElements = doc.select("table:eq(4) > tbody > tr:eq(" + i + ") > td:nth-child(4) > font");
                Elements creditElements = doc.select("table:eq(4) > tbody > tr:eq(" + i + ") > td:nth-child(5) > font");

                length += scheduleOrthoData(subjectElements.html(), placeElements.html(), dateElements.html(), subjectTypeElements.html(), classNoElements.html(), creditElements.html());

                i++;
            }
        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine -> scheduleScrapData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine -> scheduleScrapData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine -> scheduleScrapData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine -> scheduleScrapData() -> SocketException : 네트워크 전환이 이루어집니다!!");
        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine -> scheduleScrapData() -> Exception : 알수없는 오류가 발생하였습니다!!");
        }
    }

    private int scheduleOrthoData(String subject, String place, String date, String subjectType, String classNo, String credit) {
        int dayOfWeek = -1;
        int tempLength = 0;

        for(int i=0; i<date.length(); i++) {
            if(date.charAt(i) == '월' || date.charAt(i) == '화' || date.charAt(i) == '수' || date.charAt(i) == '목' || date.charAt(i) == '금') {
                switch (date.charAt(i)) {
                    case '월' :
                        dayOfWeek = WKUScheduleData.MON;
                        break;
                    case '화' :
                        dayOfWeek = WKUScheduleData.TUE;
                        break;
                    case '수' :
                        dayOfWeek = WKUScheduleData.WED;
                        break;
                    case '목' :
                        dayOfWeek = WKUScheduleData.THU;
                        break;
                    case '금' :
                        dayOfWeek = WKUScheduleData.FRI;
                        break;
                }
            } else {
                int period = -1;
                tempLength++;

                switch (date.charAt(i)) {
                    case '1' :
                        period = WKUScheduleData.PERIOD_1;
                        break;
                    case '2':
                        period = WKUScheduleData.PERIOD_2;
                        break;
                    case '3' :
                        period = WKUScheduleData.PERIOD_3;
                        break;
                    case '4' :
                        period = WKUScheduleData.PERIOD_4;
                        break;
                    case '5' :
                        period = WKUScheduleData.PERIOD_5;
                        break;
                    case '6' :
                        period = WKUScheduleData.PERIOD_6;
                        break;
                    case '7' :
                        period = WKUScheduleData.PERIOD_7;
                        break;
                    case '8' :
                        period = WKUScheduleData.PERIOD_8;
                        break;
                    case '9' :
                        period = WKUScheduleData.PERIOD_9;
                        break;
                }

                wkuData.getWkuPrivateData().getWkuScheduleDatas().add(new WKUScheduleData(subject, place, dayOfWeek, period, subjectType, classNo, credit));
            }
        }

        return tempLength;
    }

    private boolean attendScrapData(String userAgent, Map<String, String> loginCookie, int studentNo) {
        try {

            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/FSPTrnfServlet?_SQL_ID=unaf%2Fcour%2Fat%2Fcancellecture%3AcancelLecture_S07&_SECURE_KEY=318d9bd22e054e57833229c2481d351c&_urlencode_flag=N&year=2018&term=2&studentno=" + studentNo)
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .ignoreContentType(true)
                    .get();

            attendOrthoData(doc.html());

            for(int i=0; i < wkuData.getWkuPrivateData().getWkuAttendDatas().size(); i++) {
                Log.i("WKU", "WKUScrapingEngine.attendScrapData / TERM : " + wkuData.getWkuPrivateData().getWkuAttendDatas().get(i).getTerm());
                attendDetailScrapData(
                        userAgent,
                        loginCookie,
                        wkuData.getWkuPrivateData().getWkuAttendDatas().get(i).getYear(),
                        wkuData.getWkuPrivateData().getWkuAttendDatas().get(i).getTerm(),
                        wkuData.getWkuPrivateData().getWkuAttendDatas().get(i).getCodeLesson(),
                        wkuData.getWkuPrivateData().getWkuAttendDatas().get(i).getClassify(),
                        wkuData.getWkuPrivateData().getWkuAttendDatas().get(i).getEmployeeNo(),
                        studentNo,
                        i);
            }

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine -> attendScrapData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine -> attendScrapData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine -> attendScrapData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine -> attendScrapData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine -> attendScrapData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            e.printStackTrace();
        }

        return true;
    }

    private void attendOrthoData(String doc) {
        String[] splitDoc = doc.split(",");

        for(int i=0;  i + WKUAttendData.ATTEND_SCRAP_SIZE <splitDoc.length; i += WKUAttendData.ATTEND_SCRAP_SIZE) {

            String employeeNo;

            try {
                employeeNo = splitDoc[i + WKUAttendData.EMPLOYEENO_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX];
            } catch (ArrayIndexOutOfBoundsException e) {
                employeeNo = "";
            }

            if(employeeNo.equals("")) {
                // 스크랩하는 데이터에 EMPLOYEENO의 데이터가 공백일 때
                wkuData.getWkuPrivateData().getWkuAttendDatas().add(new WKUAttendData(
                        splitDoc[i + WKUAttendData.SUBJECT_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                        splitDoc[i + WKUAttendData.PROFESSOR_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                        Integer.parseInt(splitDoc[i + WKUAttendData.YEAR_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX]),
                        Integer.parseInt(splitDoc[i + WKUAttendData.TERM_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX]),
                        splitDoc[i + WKUAttendData.CODELESSON_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                        splitDoc[i + WKUAttendData.CLASSIFY_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                        -1));

            } else {
                wkuData.getWkuPrivateData().getWkuAttendDatas().add(new WKUAttendData(
                        splitDoc[i + WKUAttendData.SUBJECT_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                        splitDoc[i + WKUAttendData.PROFESSOR_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                        Integer.parseInt(splitDoc[i + WKUAttendData.YEAR_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX]),
                        Integer.parseInt(splitDoc[i + WKUAttendData.TERM_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX]),
                        splitDoc[i + WKUAttendData.CODELESSON_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                        splitDoc[i + WKUAttendData.CLASSIFY_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                        Integer.parseInt(splitDoc[i + WKUAttendData.EMPLOYEENO_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX])));
            }
        }
    }

    private boolean attendDetailScrapData(String userAgent, Map<String, String> loginCookie, int year, int term, String codeLesson, String classify, int employeeNo, int studentNo, int index) {

        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/FSPTrnfServlet?_SQL_ID=unaf%2Fcour%2Fat%2Fattendprotest%3AattendProtest_S02&_SECURE_KEY=318d9bd22e054e57833229c2481d351c&_urlencode_flag=N&year=" + year + "&term=" + term + "&codelesson=" + codeLesson + "&class=" + classify + "&employeeno=" + employeeNo + "&studentno=" + studentNo + "&gubun=stud")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .cookies(loginCookie)
                    .ignoreContentType(true)
                    .get();

            attendDetailOrthoData(doc.html(), index);

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUAttendDetailData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUAttendDetailData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUAttendDetialData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUAttendDetailData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUAttendDetailData() -> Exception : 알수없는 오류가 발생하였습니다!!");

        }

        return true;
    }

    private void attendDetailOrthoData(String doc, int index) {
        String[] splitDoc = doc.split(",");

        for(int i=0; i + WKUAttendDetailData.ATTEND_DETAIL_SCRAP_SIZE < splitDoc.length; i += WKUAttendDetailData.ATTEND_DETAIL_SCRAP_SIZE) {
            wkuData.getWkuPrivateData().getWkuAttendDatas().get(index).getWkuAttendDetailDatas().add(new WKUAttendDetailData(
                    Integer.parseInt(splitDoc[i + WKUAttendDetailData.WEEK_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX]),
                    splitDoc[i + WKUAttendDetailData.DATE_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX],
                    Integer.parseInt(splitDoc[i + WKUAttendDetailData.PERIOD_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX]),
                    splitDoc[i + WKUAttendDetailData.ATTENDANCE_INDEX].split("\"")[WKUAttendData.ORTHO_INDEX]));
        }
    }

    private boolean scholarScrapData(String userAgent, Map<String, String> loginCookie) {
        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/Service/Stud/Search/scholarResume.jsp?sm=3")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .get();


            Elements yearElement = doc.select("tr > td:nth-child(1)");
            Elements termElement = doc.select("tr > td:nth-child(2)");
            Elements titleElement = doc.select("tr > td:nth-child(3)");
            Elements moneyElement = doc.select("tr > td:nth-child(6)");

            scholarOrthoData(yearElement.html(), termElement.html(), titleElement.html(), moneyElement.html());
        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUScholarData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUScholarData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUScholarData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUScholarData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUScholarData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            e.printStackTrace();
        }

        return true;
    }

    private void scholarOrthoData(String yearData, String termData, String titleData, String moneyData) {
        String[] splitYearData = yearData.split("\n");
        String[] splitTermData = termData.split("\n");
        String[] splitTitleData = titleData.split("\n");
        String[] splitMoneyData = moneyData.split("\n");

        splitMoneyData = scholarMoneyParseInt(splitMoneyData);

        for(int i=0; i < splitYearData.length; i++) {
            wkuData.getWkuPrivateData().getWkuScholarDatas().add(new WKUScholarData(Integer.parseInt(splitYearData[i]), Integer.parseInt(splitTermData[i+2]), splitTitleData[i], Integer.parseInt(splitMoneyData[i])));
        }
    }

    private String[] scholarMoneyParseInt(String[] strings) {
        String[] resultStrings = new String[strings.length];
        for(int i=0; i<strings.length; i++) {
            String[] tmpStr1 = strings[i].split("&");
            String[] tmpStr2 = tmpStr1[0].split(",");

            resultStrings[i] = "";
            for (int j = 0; j < tmpStr2.length; j++) {
                resultStrings[i] += "" + tmpStr2[j];
            }
        }
        return resultStrings;
    }

    private boolean gradeScrapData(String userAgent, Map<String, String> loginCookie) {
        try {

            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/Service/Stud/Score/scoreAll.jsp?sm=3")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .get();

            int i=2;

            while(true) {
                Elements yearElements = doc.select("body > table:nth-child(4) > tbody > tr:nth-child(" + i + ") > td:nth-child(1)");
                Elements schoolYearElements = doc.select("body > table:nth-child(4) > tbody > tr:nth-child(" + i + ") > td:nth-child(2)");
                Elements termElements = doc.select("body > table:nth-child(4) > tbody > tr:nth-child(" + i + ") > td:nth-child(3)");
                Elements reqCreditElements = doc.select("body > table:nth-child(4) > tbody > tr:nth-child(" + i + ") > td:nth-child(4)");
                Elements ackCreditElements = doc.select("body > table:nth-child(4) > tbody > tr:nth-child(" + i + ") > td:nth-child(5)");
                Elements totalGradeElements = doc.select("body > table:nth-child(4) > tbody > tr:nth-child(" + i + ") > td:nth-child(6) > a");
                Elements avgGradeElements = doc.select("body > table:nth-child(4) > tbody > tr:nth-child(" + i + ") > td:nth-child(7)");


                wkuData.getWkuPrivateData().getWkuGradeDatas().add(new WKUGradeData(
                        Integer.parseInt(yearElements.html()),
                        Integer.parseInt(schoolYearElements.html()),
                        Integer.parseInt(termElements.html()),
                        Float.parseFloat(reqCreditElements.html()),
                        Float.parseFloat(ackCreditElements.html()),
                        Float.parseFloat(totalGradeElements.html()),
                        Float.parseFloat(avgGradeElements.html())));

                gradeDetailScrapData(userAgent, loginCookie, wkuData.getWkuPrivateData().getWkuGradeDatas().get(i-2).getYear(), wkuData.getWkuPrivateData().getWkuGradeDatas().get(i-2).getTerm(), i-2);

                i++;
            }
        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine -> crawlingWKUScoreBySemesterData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeScrapData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeScrapData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeScrapData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeScrapData() -> Exception : 알수없는 오류가 발생하였습니다!!");

        }

        return true;
    }

    private boolean gradeDetailScrapData(String userAgent, Map<String, String> loginCookie, int year, int semester, int index) {
        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/Service/Stud/Score/scoreDetail.jsp?year=" + year + "&term=" + semester)
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .get();

            int i=2;

            while(true) {
                Elements categoryElements = doc.select("table:eq(3) > tbody > tr:nth-child(" + i + ") > td:eq(0)");
                Elements codeElements = doc.select("table:eq(3) > tbody > tr:nth-child(" + i + ") > td:eq(1)");
                Elements subjectElements = doc.select("table:eq(3) > tbody > tr:nth-child(" + i + ") > td:eq(2)");
                Elements creditElements = doc.select("table:eq(3) > tbody > tr:nth-child(" + i + ") > td:eq(3)");
                Elements avgGradeElements = doc.select("table:eq(3) > tbody > tr:nth-child(" + i + ") > td:eq(4)");
                Elements markGradeElements = doc.select("table:eq(3) > tbody > tr:nth-child(" + i + ") > td:eq(5)");

                if(categoryElements.html() == "") {
                    break;
                }

                wkuData.getWkuPrivateData().getWkuGradeDatas().get(index).getWkuGradeDetailDatas().add(new WKUGradeDetailData(
                        categoryElements.html(),
                        codeElements.html(),
                        subjectElements.html(),
                        Float.parseFloat(creditElements.html()),
                        Float.parseFloat(avgGradeElements.html()),
                        markGradeElements.html()));
                i++;
            }
        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeDetailScrapData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeDetailScrapData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeDetailScrapData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeDetailScrapData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine -> gradeDetailScrapData() -> Exception : 알수없는 오류가 발생하였습니다!!");

        }

        return true;
    }

    private boolean gradeCurScrapData(String userAgent, Map<String, String> loginCookie) {
        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/Service/Stud/Score/scoreTable.jsp?sm=3")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .get();

            Elements elements = doc.select("table > tbody > tr > td");

//            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUCurScoreData() -> doc.html : " + elements.html());

            gradeCurOrthoData(elements.html());

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUCurScoreData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUCurScoreData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUCurScoreData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUCurScoreData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUCurScoreData() -> Exception : 알수없는 오류가 발생하였습니다!!");

        }

        return true;
    }

    private void gradeCurOrthoData(String doc) {
        final int TOTAL_SIZE = 11;

        final int CURGRADE_YEAR_INDEX = 4;
        final int CURGRADE_SEMESTER_INDEX = 5;

        final int SUBJECT_CATEGORY_INDEX = 6;
        final int SUBJECT_NAME_INDEX = 8;
        final int CREDIT_INDEX = 10;
        final int MIDTERM_EXAM_INDEX = 11;
        final int FINAL_EXAM_INDEX = 12;
        final int ATTEND_ASSIGN_INDEX = 13;
        final int OTHER_INDEX = 14;
        final int SCORE_INDEX = 15;
        final int MARKSCORE_INDEX = 16;

        String[] splitDoc = doc.split("\n");
        int index = 0;

        String semester = splitDoc[5].split(";")[2];

        while(true) {
            wkuData.getWkuPrivateData().getWkuGradeCurDatas().add(new WKUGradeCurData(
                    splitDoc[TOTAL_SIZE * index + SUBJECT_CATEGORY_INDEX],
                    splitDoc[TOTAL_SIZE * index + SUBJECT_NAME_INDEX],
                    Float.parseFloat(splitDoc[TOTAL_SIZE * index + CREDIT_INDEX]),
                    Float.parseFloat(splitDoc[TOTAL_SIZE * index + MIDTERM_EXAM_INDEX]),
                    Float.parseFloat(splitDoc[TOTAL_SIZE * index + FINAL_EXAM_INDEX]),
                    Float.parseFloat(splitDoc[TOTAL_SIZE * index + ATTEND_ASSIGN_INDEX]),
                    Float.parseFloat(splitDoc[TOTAL_SIZE * index + OTHER_INDEX]),
                    Float.parseFloat(splitDoc[TOTAL_SIZE * index + SCORE_INDEX]),
                    splitDoc[TOTAL_SIZE * index + MARKSCORE_INDEX]));
            index++;

            if(splitDoc[TOTAL_SIZE * index + 6].equals("&nbsp;")) {
                wkuData.getWkuPrivateData().setTerm(semester);
                break;
            }
        }

        wkuData.getWkuPrivateData().setGradeCurYear(splitDoc[CURGRADE_YEAR_INDEX].split(";")[2]);
        wkuData.getWkuPrivateData().setGradeCurTerm(splitDoc[CURGRADE_SEMESTER_INDEX].split(";")[2]);
    }

    private boolean bbsNotiScrapData(String userAgent, Map<String, String> loginCookie, int pageIndex) {
        int bbsNoticeLength = 0;

        for(int page = (WKUBBSData.PAGE_MAX * pageIndex - WKUBBSData.PAGE_MAX + 1); page <= WKUBBSData.PAGE_MAX * pageIndex; page++) {
            try {
                Document doc = Jsoup.connect("http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/list.jsp?bucket=5&lpage=" + page + "&gid=1115983888724&bid=1115985252888&sField=&sKey=")
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                        .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                        .cookies(loginCookie)
                        .get();

                int i = bbsNoticeLength;

                while(true) {
                    Elements idElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(1)");
                    Elements titleElements = null;

                    if(idElements.html() == "") {
                        break;
                    }

                    if(pageIndex == 1) {
                        if(idElements.text().equals("공지")) {
                            bbsNoticeLength++;
                            titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a > b > font");
                        } else {
                            titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");
                        }

                        Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(3)");
                        Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(4)");
                        Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(5)");
                        Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");

                        String id = idElements.text();
                        String title = titleElements.html();
                        String author = authorElements.html();
                        String date = dateElements.html();
                        String view = viewElements.html();
                        String cid = cidElements.attr("href");

                        String[] splitCid = cid.split("\"");

                        wkuData.getWkuBbsNotiDatas().add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_NOTI, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                        i++;

                    } else if(pageIndex > 1){
                        if(idElements.text().equals("공지")) {
                            bbsNoticeLength++;
                            i++;
                        } else {
                            titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");
                            Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(3)");
                            Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(4)");
                            Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(5)");
                            Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");

                            String id = idElements.text();
                            String title = titleElements.html();
                            String author = authorElements.html();
                            String date = dateElements.html();
                            String view = viewElements.html();
                            String cid = cidElements.attr("href");

                            String[] splitCid = cid.split("\"");

                            wkuData.getWkuBbsNotiDatas().add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_NOTI, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                            i++;
                        }
                    }
                }

            } catch(java.net.UnknownHostException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsNotiScrapData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.ConnectException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsNotiScrapData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketTimeoutException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsNotiScrapData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsNotiScrapData() -> SocketException : 네트워크 전환이 이루어집니다!!");

            } catch(Exception e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsNotiScrapData() -> Exception : 알수없는 오류가 발생하였습니다!!");
                e.printStackTrace();
            }
        }

        return true;
    }

    private boolean bbsRoomScrapData(String userAgent, Map<String, String> loginCookie, int pageIndex) {

        for(int page = (WKUBBSData.PAGE_MAX * pageIndex - WKUBBSData.PAGE_MAX + 1); page <= WKUBBSData.PAGE_MAX * pageIndex; page++) {
            try {
                Document doc = Jsoup.connect("http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/list.jsp?bucket=13&lpage=" + page + "&gid=1115983888724&bid=1203406502138&sField=&sKey=")
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                        .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                        .cookies(loginCookie)
                        .get();

                int i = 0;

                while(true) {
                    Elements idElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(1)");
                    Elements titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");

                    if(idElements.html() == "") {
                        break;
                    }

                    Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(3)");
                    Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(4)");
                    Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(5)");
                    Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");

                    String id = idElements.text();
                    String title = titleElements.html();
                    String author = authorElements.html();
                    String date = dateElements.html();
                    String view = viewElements.html();
                    String cid = cidElements.attr("href");

                    String[] splitCid = cid.split("\"");
                    wkuData.getWkuBbsRoomDatas().add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_ROOM, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                    i++;
                }

            } catch(java.net.UnknownHostException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKURoomBBSData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
                e.printStackTrace();
                return false;

            } catch(java.net.ConnectException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKURoomBBSData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
                e.printStackTrace();
                return false;

            } catch(java.net.SocketTimeoutException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKURoomBBSData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKURoomBBSData() -> SocketException : 네트워크 전환이 이루어집니다!!");
                e.printStackTrace();
            } catch(Exception e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKURoomBBSData() -> Exception : 알수없는 오류가 발생하였습니다!!");
                e.printStackTrace();
            }
        }

        return true;
    }

    private boolean bbsMarketScrapData(String userAgent, Map<String, String> loginCookie, int pageIndex) {

        for(int page = (WKUBBSData.PAGE_MAX * pageIndex - WKUBBSData.PAGE_MAX + 1); page <= WKUBBSData.PAGE_MAX * pageIndex; page++) {
            try {
                Document doc = Jsoup.connect("http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/list.jsp?bucket=7&lpage=" + page + "&gid=1115983888724&bid=1115985650747&sField=&sKey=")
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                        .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                        .cookies(loginCookie)
                        .get();

                int i = 0;

                while(true) {
                    Elements idElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(1)");
                    Elements titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");

                    if(idElements.html() == "") {
                        break;
                    }

                    Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(3)");
                    Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(4)");
                    Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(5)");
                    Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");

                    String id = idElements.text();
                    String title = titleElements.html();
                    String author = authorElements.html();
                    String date = dateElements.html();
                    String view = viewElements.html();
                    String cid = cidElements.attr("href");

                    String[] splitCid = cid.split("\"");
                    wkuData.getWkuBbsMarketDatas().add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_MARKET, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                    i++;
                }

            } catch(java.net.UnknownHostException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.ConnectException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketTimeoutException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> SocketException : 네트워크 전환이 이루어집니다!!");

            } catch(Exception e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            }
        }

        return true;
    }

    private boolean bbsJobScrapData(String userAgent, Map<String, String> loginCookie, int pageIndex) {

        for(int page = (WKUBBSData.PAGE_MAX * pageIndex - WKUBBSData.PAGE_MAX + 1); page <= WKUBBSData.PAGE_MAX * pageIndex; page++) {
            try {
                Document doc = Jsoup.connect("http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/list.jsp?bucket=5&lpage=" + page + "&gid=1115983888724&bid=1115985577405&sField=&sKey=")
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                        .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                        .cookies(loginCookie)
                        .get();

                int i = 0;

                while(true) {
                    Elements idElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(1)");
                    Elements titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");

                    if(idElements.html() == "") {
                        break;
                    }

                    Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(3)");
                    Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(4)");
                    Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(5)");
                    Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + i + ") > td:nth-child(2) > a");

                    String id = idElements.text();
                    String title = titleElements.html();
                    String author = authorElements.html();
                    String date = dateElements.html();
                    String view = viewElements.html();
                    String cid = cidElements.attr("href");

                    String[] splitCid = cid.split("\"");
                    wkuData.getWkuBbsJobDatas().add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_JOB, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                    i++;
                }

            } catch(java.net.UnknownHostException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.ConnectException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketTimeoutException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> SocketException : 네트워크 전환이 이루어집니다!!");

            } catch(Exception e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUMarketBBSData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            }
        }

        return true;
    }

    private boolean bbsNotiSearchScrapData(String userAgent, Map<String, String> loginCookie, String sKey) {

        int searchBBSPage = 1;
        SEARCH_LOOP : while(true) {
            try {
                Document doc = Jsoup.connect("http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/list.jsp?bucket=5&lpage=" + searchBBSPage + "&gid=1115983888724&bid=1115985252888&sField=SUBJECT&sKey=" + sKey)
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                        .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                        .cookies(loginCookie)
                        .get();

                for(int row = 0; row < WKUBBSData.ROW_MAX; row++) {
                    Elements idElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(1)");

                    if(idElements.html().equals("&nbsp;")) {
                        break SEARCH_LOOP;
                    } else if(idElements.text().equals("공지")) {

                    } else {

                        Elements titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(2) > a");
                        Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(3)");
                        Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(4)");
                        Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(5)");
                        Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(2) > a");

                        String id = idElements.text();
                        String title = titleElements.html();
                        String author = authorElements.html();
                        String date = dateElements.html();
                        String view = viewElements.html();
                        String cid = cidElements.attr("href");

                        String[] splitCid = cid.split("\"");
                        wkuData.getWkuBbsNotiDatas().add( new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_NOTI, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                    }
                }
            } catch(java.net.UnknownHostException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUSearchNotiBBSData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.ConnectException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUSearchNotiBBSData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketTimeoutException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUSearchNotiBBSData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
                return false;

            }  catch(java.net.SocketException e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUSearchNotiBBSData() -> SocketException : 네트워크 전환이 이루어집니다!!");

            } catch(Exception e) {
                Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUSearchNotiBBSData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            }

            searchBBSPage++;
        }

        return true;
    }

    private boolean bbsRoomSearchScrapData(String userAgent, Map<String, String> loginCookie, String sKey) {

        int searchBBSPage = 1;
        SEARCH_LOOP : while(true) {
            try {

                Document doc = Jsoup.connect("http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/list.jsp?bucket=13&lpage=" + searchBBSPage + "&gid=1115983888724&bid=1203406502138&sField=SUBJECT&sKey=" + sKey)
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                        .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                        .cookies(loginCookie)
                        .get();

                for(int row = 0; row < WKUBBSData.ROW_MAX; row++) {
                    Elements idElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(1)");

                    if(idElements.html().equals("&nbsp;")) {
                        break SEARCH_LOOP;
                    } else if(idElements.text().equals("공지")) {

                    } else {

                        Elements titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(2) > a");
                        Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(3)");
                        Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(4)");
                        Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(5)");
                        Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(2) > a");

                        String id = idElements.text();
                        String title = titleElements.html();
                        String author = authorElements.html();
                        String date = dateElements.html();
                        String view = viewElements.html();
                        String cid = cidElements.attr("href");

                        String[] splitCid = cid.split("\"");
                        wkuData.getWkuBbsRoomDatas().add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_ROOM, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                    }
                }
            } catch(java.net.UnknownHostException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsRoomSearchScrapData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.ConnectException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsRoomSearchScrapData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketTimeoutException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsRoomSearchScrapData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsRoomSearchScrapData() -> SocketException : 네트워크 전환이 이루어집니다!!");

            } catch(Exception e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsRoomSearchScrapData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            }

            searchBBSPage++;
        }

        return true;
    }

    private boolean bbsMarketSearchScrapData(String userAgent, Map<String, String> loginCookie, String sKey) {

        int searchBBSPage = 1;
        SEARCH_LOOP : while(true) {
            try {
                Document doc = Jsoup.connect("http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/list.jsp?bucket=7&lpage=" + searchBBSPage + "&gid=1115983888724&bid=1115985650747&sField=SUBJECT&sKey=" + sKey)
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                        .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                        .cookies(loginCookie)
                        .get();

                for(int row = 0; row < WKUBBSData.ROW_MAX; row++) {
                    Elements idElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(1)");

                    if(idElements.html().equals("&nbsp;")) {
                        break SEARCH_LOOP;
                    } else if(idElements.text().equals("공지")) {

                    } else {

                        Elements titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(2) > a");
                        Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(3)");
                        Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(4)");
                        Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(5)");
                        Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(2) > a");

                        String id = idElements.text();
                        String title = titleElements.html();
                        String author = authorElements.html();
                        String date = dateElements.html();
                        String view = viewElements.html();
                        String cid = cidElements.attr("href");

                        String[] splitCid = cid.split("\"");
                        wkuData.getWkuBbsMarketDatas().add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_MARKET, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                    }
                }
            } catch(java.net.UnknownHostException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsMarketSearchScrapData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.ConnectException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsMarketSearchScrapData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketTimeoutException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsMarketSearchScrapData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsMarketSearchScrapData() -> SocketException : 네트워크 전환이 이루어집니다!!");

            } catch(Exception e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsMarketSearchScrapData() -> Exception : 알수없는 오류가 발생하였습니다!!");

            }

            searchBBSPage++;
        }
        return true;
    }

    private boolean bbsJobSearchScrapData(String userAgent, Map<String, String> loginCookie, String sKey) {

        int searchBBSPage = 1;
        SEARCH_LOOP : while(true) {
            try {
                Document doc = Jsoup.connect("http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/list.jsp?bucket=5&lpage=" + searchBBSPage + "&gid=1115983888724&bid=1115985577405&sField=SUBJECT&sKey=" + sKey)
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                        .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                        .cookies(loginCookie)
                        .get();

                for(int row = 0; row < WKUBBSData.ROW_MAX; row++) {
                    Elements idElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(1)");

                    if(idElements.html().equals("&nbsp;")) {
                        break SEARCH_LOOP;
                    } else if(idElements.text().equals("공지")) {

                    } else {

                        Elements titleElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(2) > a");
                        Elements authorElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(3)");
                        Elements dateElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(4)");
                        Elements viewElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(5)");
                        Elements cidElements = doc.select("form[name=list] > table > tbody > tr:eq(" + row + ") > td:nth-child(2) > a");

                        String id = idElements.text();
                        String title = titleElements.html();
                        String author = authorElements.html();
                        String date = dateElements.html();
                        String view = viewElements.html();
                        String cid = cidElements.attr("href");

                        String[] splitCid = cid.split("\"");
                        wkuData.getWkuBbsJobDatas().add(new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_JOB, bbsParseId(id), title, author, date, Integer.parseInt(view), Long.parseLong(splitCid[1]), 0));
                    }
                }
            } catch(java.net.UnknownHostException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsJobSearchScrapData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.ConnectException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsJobSearchScrapData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketTimeoutException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsJobSearchScrapData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
                return false;

            } catch(java.net.SocketException e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsJobSearchScrapData() -> SocketException : 네트워크 전환이 이루어집니다!!");

            } catch(Exception e) {
                Log.i("WKU", "WKUScrapingEngine -> bbsJobSearchScrapData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            }

            searchBBSPage++;
        }

        return true;
    }

    private int bbsParseId(String id) {
        int resultId = 0;

        if(id.equals("공지")) {
            resultId = WKUBBSData.NOTICE_VALUE;
        } else if(id.equals("new")) {
            resultId = WKUBBSData.NEW_VALUE;
        } else {
            resultId = Integer.parseInt(id);
        }

        return resultId;
    }

    private String bbsDetailDownloadData(String userAgent, Map<String, String> loginCookie, String docUrl) {

        String url = "";

        try {
            Document doc = Jsoup.connect(docUrl)
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .get();

            Elements lpage = doc.select("input[name=lpage]");
            Elements gid = doc.select("input[name=gid]");
            Elements bid = doc.select("input[name=bid]");
            Elements cid = doc.select("input[name=cid]");
            Elements fchange = doc.select("input[name=fchange]");

            url = "http://cyber.wku.ac.kr/Cyber/ComBoardDownLoad?lpage=" + lpage.val() +
                    "&gid=" + gid.val() +
                    "&bid=" + bid.val() +
                    "&cid=" + cid.val() +
                    "&fchange=" + fchange.val() +
                    "&fgubun=&baseSavePath=/wupis/cyber/ComBoard/upload/upload&isRealPath=T";

            return url;

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDownloadFileUrl() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return "UnknownHostException";

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDownloadFileUrl() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return "ConnectionException";

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDownloadFIleUrl() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return "SocketTimeoutException";

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDownloadFileUrl() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDownloadFileUrl() -> Exception : 알수없는 오류가 발생하였습니다!!");
        }

        return url;
    }

    private boolean dormScrapData(String userAgent, Map<String, String> loginCookie) {
        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/CommonServ/dormitory/stud/goOutList.jsp")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .ignoreContentType(true)
                    .get();

            Elements dateElements = doc.select("td:eq(0)");
            Elements reasonElements = doc.select("td:eq(2)");
            Elements locationElements = doc.select("td:eq(1)");
            Elements emgTelElements = doc.select("td:eq(3)");

            String date = dateElements.html();
            String reason = reasonElements.html();
            String location = locationElements.html();
            String emgTel = emgTelElements.html();

            String[] splitDate = date.split("\n");
            String[] splitReason = reason.split("\n");
            String[] splitLocation = location.split("\n");
            String[] splitEmgTel = emgTel.split("\n");

            for(int i=0; i< splitDate.length; i++) {
                wkuData.getWkuPrivateData().getWkuDormDatas().add(new WKUDormData(splitDate[i], splitReason[i], splitLocation[i], splitEmgTel[i]));
            }

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormListData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormListData() -> ConnectException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormListData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        }  catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormListData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormListData() -> Exception : 알수없는 오류가 발생하였습니다!!");

        }
        return true;
    }

    private boolean dormAddScrapData(String userAgent, Map<String, String> loginCookie, String outDate, String reason, String location, String emgTel) {
        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/CommonServ/dormitory/stud/dormAction.jsp?ContextPath=goOutList.jsp&Process=goOutApply&outDate=" + outDate + "&reason=" + reason + "&location=" + location + "&emgTel=" + emgTel)
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .ignoreContentType(true)
                    .get();

            Elements element = doc.select("script");

            String[] splitDoc =element.html().split("\"");

            if(splitDoc[1].equals("외박신청이 완료되었습니다.")) {
                dormScrapData(userAgent, loginCookie);
            } else if(splitDoc[1].equals("해당 일에 이미 신청하신 내역이 있습니다.")) {
                return false;
            }

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormData() -> Exception : 알수없는 오류가 발생하였습니다!!");

        }
        return true;
    }

    private boolean dormCancelScrapData(String userAgent, Map<String, String> loginCookie, String outDate, String reason, String location, String emgTel) {
        try {
            Document doc = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/CommonServ/dormitory/stud/dormAction.jsp?ContextPath=goOutList.jsp&Process=goOutDelete&outDate=" + outDate + "&reason=" + reason + "&location=" + location + "&emgTel=" + emgTel)
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp?error_msg=1")
                    .cookies(loginCookie)
                    .ignoreContentType(true)
                    .get();

            dormScrapData(userAgent, loginCookie);

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormCancelData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormCancelData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormCancelData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormCancelData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUDormCancelData() -> Exception : 알수없는 오류가 발생하였습니다!!");
            e.printStackTrace();
        }

        return true;
    }

    private boolean eClassScrapData(String userAgent, String studentNo) {
        try {

            Connection.Response res = Jsoup.connect("http://wvu.wku.ac.kr/studentlogin/cmdLogin.asp?gourl=&sName=&sNo=" + studentNo + "&sYear=&sTerm=")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9s,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Referer", "http://wvu.wku.ac.kr/clogin.asp")
                    .execute();

            Document doc = res.parse();

            Elements title = doc.select("#list_list > tbody > tr > td:eq(0) > p > font");
            Elements professor = doc.select("#list_list > tbody > tr > td:eq(2) > font > a");
            Elements mp = doc.select("#list_list > tbody > input[name='mp']");
            Elements sp = doc.select("#list_list > tbody > input[name='sp']");
            Elements ci = doc.select("#list_list > tbody > input[name='ci']");
            Elements ui = doc.select("#list_list > tbody > input[name='ui']");
            Elements sn = doc.select("#list_list > tbody > input[name='sn']");

            String[] splitTitle = title.html().split("\n");
            String[] splitProfessor = professor.html().split("\n");

            for(int i=0; i<splitTitle.length; i++) {
                wkuData.getWkuPrivateData().getWkuEClassDatas().add(new WKUEClassData(splitTitle[i], splitProfessor[i], mp.eachAttr("value").get(i), sp.eachAttr("value").get(i), ci.eachAttr("value").get(i), ui.attr("value"), sn.attr("value")));
                eClassDetailScrapData(userAgent, res.cookies(), mp.eachAttr("value").get(i), sp.eachAttr("value").get(i), ci.eachAttr("value").get(i), ui.attr("value"), sn.attr("value"), i);
            }

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassScrapData / UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassScrapData / ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassScrapData / SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassScrapData / SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine.eClassScrapData / Exception : 알수없는 오류가 발생하였습니다!!");
            e.printStackTrace();
        }
        return true;
    }

    private boolean eClassDetailScrapData(String userAgent, Map<String, String> eClassDetailCookie, String mp, String sp, String ci, String ui, String sn, int i) {
        try {
            Jsoup.connect("http://wvu.wku.ac.kr/studentlogin/branch.asp?mp=" + mp + "&sp=" + sp + "&ci=" + ci + "&ui=" + ui + "&sn=" + sn)
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .cookies(eClassDetailCookie)
                    .method(Connection.Method.GET)
                    .get();

            eClassDetailNotiScrapData(userAgent, eClassDetailCookie, i);
            eClassDetailPdsScrapData(userAgent, eClassDetailCookie, i);
            eClassDetailReportScrapData(userAgent, eClassDetailCookie, i);
            eClassDetailLectureScrapData(userAgent, eClassDetailCookie, i);

            Jsoup.connect("http://wvu.wku.ac.kr/cmdLogout.asp?Mesg=1")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .cookies(eClassDetailCookie)
                    .method(Connection.Method.GET)
                    .get();

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUEClassDetailData() -> UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUEClassDetailData() -> ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUEClassDetailData() -> SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUEClassDetailData() -> SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUCrawlingEngine -> crawlingWKUEClassDetailData() -> Exception : 알수없는 오류가 발생하였습니다!!");
        }

        return true;
    }

    private boolean eClassDetailNotiScrapData(String userAgent, Map<String, String> eClassDetailCookie, int eclass_index) {
        int scrap_index = 1;

        try {
            Document doc = Jsoup.connect("http://wvu.wku.ac.kr/wvuFB/vacant_list.asp?page=1&ua=")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .method(Connection.Method.GET)
                    .cookies(eClassDetailCookie)
                    .get();

            while(true) {
                Elements author = doc.select("#list_list > tbody > tr:eq(" + scrap_index + ") > td:eq(1) > center > font");
                Elements date = doc.select("#list_list > tbody > tr:eq(" + scrap_index + ") > td:eq(2) > center > font");
                Elements title = doc.select("#list_list > tbody > tr:eq(" + scrap_index + ") > td:eq(3) > p > font > a");
                Elements file = doc.select("#list_list > tbody > tr:eq(" + scrap_index + ") > td:eq(4) > center > font > a");
                Elements hit = doc.select("#list_list > tbody > tr:eq(" + scrap_index + ") > td:eq(5) > center > font");

                String titleStr = title.html().replace("&nbsp;", "");
                boolean fileFlag = false;

                if(!file.html().equals("")) {
                    fileFlag = true;
                }

                if(author.html().equals("")) {
                    break;
                }

                wkuData.getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailNotiDatas().add(new WKUEClassDetailNotiData(0, author.html(), date.html(), titleStr, fileFlag, hit.html(), 0));

                scrap_index++;
            }

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailNotiScrapData / UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailNotiScrapData / ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailNotiScrapData / SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailNotiScrapData / SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailNotiScrapData / Exception : 알수없는 오류가 발생하였습니다!!");
            e.printStackTrace();
        }

        return true;
    }

    private boolean eClassDetailPdsScrapData(String userAgent, Map<String, String> eClassDetailCookie, int eclass_index) {
        int scrap_index = 3;

        try {
            Document doc = Jsoup.connect("http://wvu.wku.ac.kr/pds/list_view.asp?ua=")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .method(Connection.Method.GET)
                    .cookies(eClassDetailCookie)
                    .get();

            while(true) {
                Elements title = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(1) > a");
                Elements file = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(2) > a");
                Elements size = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(3)");
                Elements date = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(4)");
                Elements author = doc.select("div > table > tbody > tr:eq(" + scrap_index +") > td:eq(5)");
                Elements hit = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(6)");

                String fileUrl = "http://wvu.wku.ac.kr/pds/" + file.attr("href");

                if(title.html().equals("")) {
                    break;
                }
                wkuData.getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailPdsDatas().add(new WKUEClassDetailPdsData(0, title.html(), fileUrl, size.html(), date.html(), author.html(), hit.html(), 0));

                scrap_index += 2;
            }

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailPdsScrapData / UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailPdsScrapData / ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailPdsScrapData / SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailPdsScrapData / SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailPdsScrapData / Exception : 알수없는 오류가 발생하였습니다!!");
        }

        return true;
    }

    private boolean eClassDetailReportScrapData(String userAgent, Map<String, String> eClassDetailCookie, int eclass_index) {
        int scrap_index = 2;

        try {
            Document doc = Jsoup.connect("http://wvu.wku.ac.kr/student/report_bbs/report_list.asp?ua=")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .method(Connection.Method.GET)
                    .cookies(eClassDetailCookie)
                    .get();

            while(true) {
                Elements title = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(1) > font > a");
                Elements date = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(2) > font");
                Elements grade = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(3) > font");
                Elements submit = doc.select("div > table > tbody > tr:eq(" + scrap_index + ") > td:eq(5) > font");

                boolean submitFlag = false;
                String dateStr = date.html().replace("&nbsp;", "");

                if (submit.html().equals("기간내제출")) {
                    submitFlag = true;
                }

                if (title.html().equals("")) {
                    break;
                }
                wkuData.getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailReportDatas().add(new WKUEClassDetailReportData(0, title.html(), dateStr, grade.html(), submitFlag, 0));

                scrap_index += 2;
            }

        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailReportScrapData / UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailReportScrapData / ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailReportScrapData / SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailReportScrapData / SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailReportScrapData / Exception : 알수없는 오류가 발생하였습니다!!");
        }

        return true;
    }

    private boolean eClassDetailLectureScrapData(String userAgent, Map<String, String> eClassDetailCookie, int eclass_index) {
        int scrap_index = 3;
        int folderIndex = -1;

        try {
            Document doc = Jsoup.connect("http://wvu.wku.ac.kr/lecture/lecture_com/lecture.asp?uidrandom=")
                    .userAgent(userAgent)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .method(Connection.Method.GET)
                    .cookies(eClassDetailCookie)
                    .get();

            while(true) {
                Elements week = doc.select("form > table:eq(2) > tbody > tr[valign='middle']:eq(" + scrap_index + ") > td:eq(0) > p > font");
                Elements titleFolder = doc.select("form > table:eq(2) > tbody > tr[valign='middle']:eq(" + scrap_index + ") > td:eq(1) > p > b > font");
                Elements titleContent = doc.select("form > table:eq(2) > tbody > tr[valign='middle']:eq(" + scrap_index + ") > td:eq(1) > p > font");
                Elements period = doc.select("form > table:eq(2) > tbody > tr[valign='middle']:eq(" + scrap_index + ") > td:eq(2) > p > font");
                Elements attend = doc.select("form > table:eq(2) > tbody > tr[valign='middle']:eq(" + scrap_index + ") > td:eq(3) > p > font > font");
                Elements url = doc.select("form > table:eq(2) > tbody > tr[valign='middle']:eq(" + scrap_index + ") > td:eq(4) > p > font > a");

                if(!titleFolder.html().equals("")) {
                    wkuData.getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailLectureDatas().add(new WKUEClassDetailLectureData(0, week.html(), titleFolder.html(), 0));
                    folderIndex++;
                } else if(!titleContent.html().equals("")) {
                    String[] splitUrl = url.attr("href").split("'");
                    String urlStr = "http://wvu.wku.ac.kr/classroom/content_link.asp?num=" + splitUrl[1] + "&kk=" + splitUrl[3];

                    boolean attendFlag = false;

                    if(attend.html().equals("O")) {
                        attendFlag = true;
                    }
                    wkuData.getWkuPrivateData().getWkuEClassDatas().get(eclass_index).getWkuEClassDetailLectureDatas().get(folderIndex).getWkuEClassDetailLectureContentDatas().add(new WKUEClassDetailLectureContentData(period.html(), attendFlag, urlStr));
                }

                if (week.html().equals("")) {
                    break;
                }

                scrap_index += 3;
            }
        } catch(java.net.UnknownHostException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailLectureScrapData / UnknownHostException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.ConnectException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailLectureScrapData / ConnectionException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketTimeoutException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailLectureScrapData / SocketTimeoutException : 네트워크 연결을 확인하세요!!");
            return false;

        } catch(java.net.SocketException e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailLectureScrapData / SocketException : 네트워크 전환이 이루어집니다!!");

        } catch(Exception e) {
            Log.i("WKU", "WKUScrapingEngine.eClassDetailLectureScrapData / Exception : 알수없는 오류가 발생하였습니다!!");
            e.printStackTrace();
        }

        return true;
    }
}
