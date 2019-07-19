package com.example.yun.wku;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class WKUBBSMarketFragment extends Fragment {
    private boolean SCROLL_SCRAPING_FLAG = false;
    private boolean SEARCH_SCRAPING_FLAG = false;

    private int PAGE_INDEX = 1;

    private ListView bbsMarketList;
    private ArrayList<WKUBBSData> arrayList;
    private BBSAdapter bbsMarketAdapter;

    private WKUDatabase wkuDatabase;
    private WKUScrapingEngine wkuScrapingEngine;

    private YunAnimationController yunAnimationController;

    private String wkuId, wkuPw;
    private String jSessionId = "";
    private String wkuTokenKey = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == WKUScrapingEngine.SCRAPING_FINISH_MODE) {
                Log.i("WKU", "WKUBBSRoomFragment.handler / FINISH...");
                SCROLL_SCRAPING_FLAG = false;

                getBbsMarketData(wkuScrapingEngine, WKUBBSData.SCROLL_MODE);
            }
        }
    };

    public WKUBBSMarketFragment() {
        arrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wkubbsroom, container, false);

        bbsMarketList = rootView.findViewById(R.id.bbs_room_list);
        bbsMarketAdapter = new BBSAdapter(getActivity(), R.layout.list_wkubbs, arrayList);
        bbsMarketList.setAdapter(bbsMarketAdapter);

        wkuDatabase = new WKUDatabase(getActivity());

        bbsMarketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WKUBBSDetailActivity.class);
                intent.putExtra("url", "http://cyber.wku.ac.kr/Cyber/ComBoard_V005/Content/view.jsp?bucket=7&lpage=1&gid=1115983888724&bid=1115985650747&cid=" + arrayList.get(position).getCid() + "&sField=&sKey=");
                startActivity(intent);
            }
        });

        bbsMarketList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((firstVisibleItem + visibleItemCount) > (totalItemCount - WKUBBSData.OCCUR_SCRAP_SIZE)) {
                    if(!SCROLL_SCRAPING_FLAG && !SEARCH_SCRAPING_FLAG) {
                        Log.i("WKU", "WKUBBSRoomFragment.onScroll / OCCUR SCRAPING...");
                        wkuScrapingEngine = new WKUScrapingEngine(WKUScrapingEngine.SCRAPING_BBS_MARKET_MODE, handler, wkuId, wkuPw, jSessionId, wkuTokenKey, ++PAGE_INDEX);
                        wkuScrapingEngine.start();

                        SCROLL_SCRAPING_FLAG = true;
                    }
                }
            }
        });

        yunAnimationController = new YunAnimationController(bbsMarketList);
        yunAnimationController.addAnimation(
                new YunScaleAnimation(((WKUBBSActivity)getActivity()).getSearchBox(), 0, 150, 0, 0),
                new YunScaleAnimation(((WKUBBSActivity)getActivity()).getSearchBox(), 0, 0, 0, 150),
                null,
                null);

        yunAnimationController.addAnimation(
                new YunScaleAnimation(((WKUBBSActivity)getActivity()).getTabBar(), 0, 150, 0, 0),
                new YunScaleAnimation(((WKUBBSActivity)getActivity()).getTabBar(), 0, 0, 0, 150),
                null,
                null
        );
        yunAnimationController.start();

        getScrapingData();
        getBbsMarketData(wkuDatabase);

        return rootView;
    }

    public void getBbsMarketData(WKUDatabase wkuDatabase) {
        arrayList.clear();

        Cursor cursor = wkuDatabase.getDB().rawQuery("SELECT id, title, author, date, hits, cid FROM wkuBbsMarketData", null);

        WKUBBSData marketbbs;

        while (cursor.moveToNext()) {
            marketbbs = new WKUBBSData(0, WKUAlarmData.CATEGORY_BBS_MARKET, Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)), Long.parseLong(cursor.getString(5)), 0);

            arrayList.add(marketbbs);
        }

        bbsMarketAdapter.notifyDataSetChanged();
    }

    public void getBbsMarketData(WKUScrapingEngine wkuScrapingEngine, int mode) {
        if(mode == WKUBBSData.SEARCH_MODE) {
            arrayList.clear();
        }

        for(int i=0; i<wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().size(); i++) {
            arrayList.add(new WKUBBSData(0,
                            WKUAlarmData.CATEGORY_BBS_MARKET,
                            wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(i).getId(),
                            wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(i).getTitle(),
                            wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(i).getAuthor(),
                            wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(i).getDate(),
                            wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(i).getHits(),
                            wkuScrapingEngine.getWkuData().getWkuBbsMarketDatas().get(i).getCid(),
                            0
                    )
            );
        }

        bbsMarketAdapter.notifyDataSetChanged();
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

    public void setSearchScrapingFlag(boolean flag) { this.SEARCH_SCRAPING_FLAG = flag; }
}
