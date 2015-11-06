package com.witkey.coder.zhdaily;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.witkey.coder.zhdaily.adapters.FeedAdapter;
import com.witkey.coder.zhdaily.models.Feed;

import java.util.ArrayList;

/**
 * 主页内容Fragment
 */
public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private FeedAdapter feedAdapter;
    private ArrayList<Object> dataStream = new ArrayList<>();

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // 数据流第一位永远是“今日热闻”
        dataStream.add(String.format("%s", "今日热闻"));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        // 此处插入时间
        dataStream.add(String.format("%s %s", "11月4日", "星期三"));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));
        dataStream.add(new Feed("1", "2", "3", "4", 1, true));

        // 设置 recycler view
        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_view);
        setRecyclerViewLayoutManager();
        feedAdapter = new FeedAdapter(getActivity());
        recyclerView.setAdapter(feedAdapter);

        feedAdapter.setDataset(dataStream);
        feedAdapter.notifyDataSetChanged();

        return rootView;
    }


    private void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // 使用 linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        // 如果layout manager已经设定好, 则获取Adapter中第一个可见item的位置
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }
}
