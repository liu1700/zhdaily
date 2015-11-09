package com.witkey.coder.zhdaily;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
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
    private static String oldest;
    private FloatingActionButton backToTop;
    private RecyclerView recyclerView;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // 加入首页顶部轮播图的假数据
        ArrayList<ImageFlipper> imageFlippers = new ArrayList<>();
        imageFlippers.add(new ImageFlipper("1", "1", "1", "1"));
        imageFlippers.add(new ImageFlipper("1", "1", "2", "1"));
        imageFlippers.add(new ImageFlipper("1", "1", "3", "1"));
        dataStream.add(imageFlippers);

        // 将更新操作发送到主循环的消息队列
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(loadStoryStream());

        // 设置 recycler view
        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_view);
        setRecyclerViewLayoutManager(recyclerView);
        feedAdapter = new FeedAdapter(getActivity());
        recyclerView.setAdapter(feedAdapter);

        backToTop = (FloatingActionButton) getActivity().findViewById(R.id.back_to_top);
        backToTop.setVisibility(View.GONE);

        return rootView;
    }

    private Runnable loadStoryStream() {
         return new Runnable() {
            @Override
            public void run() {
                // 迭代读取数据库
                Iterator<String> iterator = CircleDB.getKeyIterator();
                if (!iterator.hasNext()) {
                    updateStream(Tool.getTomorrow(), true);
                    return;
                }
                while (iterator.hasNext()) {
                    String d = iterator.next();
                    String date = Tool.toFormatDate(d);
                    ArrayList<Story> storyArrayList = (ArrayList<Story>)CircleDB.read(d);
                    if (storyArrayList == null) return;
                    oldest = d;
                    dataStream.add(date);
                    dataStream.addAll(storyArrayList);
                }
                updateStream(Tool.getTomorrow(), false);
            }
        };
    }

    private void updateStream(String date, final boolean loadMore) {
        Networking.get(String.format("%s%s", Networking.FEED_STREAM, date), Stories.class, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Stories stories = (Stories) response;
                        ArrayList<Story> storyArrayList = stories.getStories();

                        String date = Tool.toFormatDate(stories.getDate());

                        if (loadMore) {
                            oldest = stories.getDate();
                            dataStream.add(date);
                            dataStream.addAll(storyArrayList);
                        } else {
                            String gaPrefixInDB = ((Story)dataStream.get(2)).getGaPrefix();
                            dataStream.set(1, date);
                            for (int i = 0; !storyArrayList.get(i).getGaPrefix().equals(gaPrefixInDB); i++) {
                                dataStream.add(2, storyArrayList.get(i));
                            }
                        }
                        update();
                        CircleDB.write(stories.getDate(), storyArrayList);
                    }
                }

        );
    }

    private void update() {
        feedAdapter.setDataset(dataStream);
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBottom() {
        updateStream(oldest, true);
    }

    @Override
    public void onDeepIn() {
        backToTop.setVisibility(View.VISIBLE);
        backToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
                backToTop.setVisibility(View.GONE);
            }
        });
    }

//    @Override
//    public void onChangeDate(String date) {
//        super.onChangeDate(date);
//
//    }
}
