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
import com.witkey.coder.zhdaily.models.ImageFlipper;

import java.util.ArrayList;

/**
 * 主页内容Fragment
 */
public class MainFragment extends BaseFragment {
    private FeedAdapter feedAdapter;
    private ArrayList<Object> dataStream = new ArrayList<>();

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<ImageFlipper> imageFlippers = new ArrayList<>();
        imageFlippers.add(new ImageFlipper("1", "1", "1", "1"));
        imageFlippers.add(new ImageFlipper("1", "1", "2", "1"));
        imageFlippers.add(new ImageFlipper("1", "1", "3", "1"));
        dataStream.add(imageFlippers);

        // 数据流时间第一位是“今日热闻”
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
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.main_view);
        setRecyclerViewLayoutManager(recyclerView);
        feedAdapter = new FeedAdapter(getActivity());
        recyclerView.setAdapter(feedAdapter);

        feedAdapter.setDataset(dataStream);
        feedAdapter.notifyDataSetChanged();

        return rootView;
    }
}
