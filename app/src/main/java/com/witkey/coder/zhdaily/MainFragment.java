package com.witkey.coder.zhdaily;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.witkey.coder.zhdaily.adapters.FeedAdapter;
import com.witkey.coder.zhdaily.db.CircleDB;
import com.witkey.coder.zhdaily.models.Stories;
import com.witkey.coder.zhdaily.models.Story;
import com.witkey.coder.zhdaily.models.ImageFlipper;
import com.witkey.coder.zhdaily.networking.Networking;
import com.witkey.coder.zhdaily.utils.Tool;

import java.util.ArrayList;
import java.util.Iterator;

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

        Handler handler = new Handler(Looper.getMainLooper());
        // 启用线程从数据库中读取缓存好的数据流
        handler.post(loadStoryStreamFromDB());

        // 设置 recycler view
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.main_view);
        setRecyclerViewLayoutManager(recyclerView);
        feedAdapter = new FeedAdapter(getActivity());
        recyclerView.setAdapter(feedAdapter);

        return rootView;
    }

    private Runnable loadStoryStreamFromDB() {
         return new Runnable() {
            @Override
            public void run() {
                // 迭代读取数据库
                Iterator<String> iterator = CircleDB.getKeyIterator();
                while (iterator.hasNext()) {
                    String d = iterator.next();
                    String date = Tool.toFormatDate(d);
                    ArrayList<Story> storyArrayList = (ArrayList<Story>)CircleDB.read(d);
                    if (storyArrayList != null) {
                        dataStream.add(date);
                        dataStream.addAll(storyArrayList);
                    }
                }
                refresh();
            }
        };
    }

    public void refresh() {
        if (dataStream.size() > 1) {
            feedAdapter.setDataset(dataStream);
            feedAdapter.notifyDataSetChanged();
        } else {
            updateStream(Tool.getTomorrowDate());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

//        updateStream(Tool.getTomorrowDate());
    }

    private void updateStream(String date) {
        Networking.get(String.format("%s%s", Networking.FEED_STREAM, date), Stories.class, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DailyApp.getAppContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Stories stories = (Stories) response;
                        ArrayList<Story> storyArrayList = stories.getStories();

                        dataStream.addAll(storyArrayList);
                        CircleDB.write(stories.getDate(), storyArrayList);
                        feedAdapter.setDataset(dataStream);
                        feedAdapter.notifyDataSetChanged();
                    }
                }

        );
    }
}
