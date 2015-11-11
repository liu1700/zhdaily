package com.witkey.coder.zhdaily;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Fragment基类，包含复用的函数
 *
 */
public abstract class BaseFragment extends Fragment {

    // 设置Recyclerview
    RecyclerView.LayoutManager setRecyclerViewLayoutManager(RecyclerView recyclerView) {
        int scrollPosition = 0;

        // 使用 linear layout manager
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // 如果layout manager已经设定好, 则获取Adapter中第一个可见item的位置
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(scrollPosition);

        return layoutManager;
    }

    // 设置下拉刷新
    void configPullToRefresh(final SwipeRefreshLayout layout) {
        int[] colors = getActivity().getResources().getIntArray(R.array.google_colors);
        layout.setColorSchemeColors(colors);

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    void refresh(){};

//    public void onChangeDate(String date){}
}
