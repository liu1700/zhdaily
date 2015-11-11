package com.witkey.coder.zhdaily;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.witkey.coder.zhdaily.adapters.StoryAdapter;
import com.witkey.coder.zhdaily.db.CircleDB;
import com.witkey.coder.zhdaily.models.Stories;
import com.witkey.coder.zhdaily.models.Story;
import com.witkey.coder.zhdaily.models.ImageFlipper;
import com.witkey.coder.zhdaily.networking.Networking;
import com.witkey.coder.zhdaily.utils.Tool;

import java.util.ArrayList;
import java.util.Iterator;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 主页内容Fragment
 */
public class MainFragment extends BaseFragment {
    private StoryAdapter storyAdapter;
    private ArrayList<Object> dataStream = new ArrayList<>();
    private static String oldest;
    private FloatingActionButton backToTop;
    private boolean processing = true;
    private int previousAll = 0;
    private LinearLayoutManager layoutManager;
    private PtrFrameLayout ptrFrameLayout;

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

        // 设置下拉刷新
        ptrFrameLayout = (PtrFrameLayout) rootView.findViewById(R.id.ptr_frame);
        configPullToRefresh(ptrFrameLayout);

        // 设置 recycler view
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.main_view);
        layoutManager = (LinearLayoutManager)setRecyclerViewLayoutManager(recyclerView);
        storyAdapter = new StoryAdapter(getActivity());
        recyclerView.setAdapter(storyAdapter);

        // 设置 recycler view 的滚动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 处理滚动逻辑
                processLogic(recyclerView);
            }
        });

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
                            String gaPrefixInDB = ((Story) dataStream.get(2)).getGaPrefix();
                            String newestInDB = CircleDB.getFirstKey();
                            // 判断数据库中最新的日期是否是今天
                            if (!newestInDB.equals(Tool.getToday())){
                                dataStream.add(1, date);
                                dataStream.addAll(2, storyArrayList);
                            } else {
                                dataStream.set(1, date);
                                for (int i = 0;
                                     !storyArrayList.get(i).getGaPrefix().equals(gaPrefixInDB);
                                     i++) {
                                    dataStream.add(2, storyArrayList.get(i));
                                }
                            }
                        }
                        update();
                        CircleDB.write(stories.getDate(), storyArrayList);
                    }
                }
        );
    }

    private void update() {
        storyAdapter.setDataset(dataStream);
        storyAdapter.notifyDataSetChanged();
    }

    // 滚动处理逻辑
    void processLogic(final RecyclerView recyclerView) {
        // 可见的item数量
        int itemsInView = recyclerView.getChildCount();
        // 总item数量
        int currentAll = layoutManager.getItemCount();
        // 最顶端可见item的位置
        int topVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        // 如果正在处理中，检测当前总item数量是否多于以前的记录
        // 如果未在处理中，检测是否到底
        if (processing) {
            if (currentAll > previousAll) {
                processing = false;
                previousAll = currentAll;
            }
        } else {
            if (topVisibleItemPosition + itemsInView >= currentAll) {
                processing = true;
                updateStream(oldest, true);
            }
        }

        // 当顶部位置为显示内容的两倍时，显示回到顶部按钮
        if (topVisibleItemPosition > itemsInView << 1) {
            backToTop.setVisibility(View.VISIBLE);
            backToTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerView.scrollToPosition(0);
                    backToTop.setVisibility(View.GONE);
                }
            });
        }

        ptrFrameLayout.setEnabled(recyclerView.computeVerticalScrollOffset() == 0);
    }

    @Override
    protected void onRefresh(PtrFrameLayout frameLayout) {
        super.onRefresh(frameLayout);
        updateStream(Tool.getTomorrow(), false);
    }

    //    @Override
//    public void onChangeDate(String date) {
//        super.onChangeDate(date);
//
//    }
}
