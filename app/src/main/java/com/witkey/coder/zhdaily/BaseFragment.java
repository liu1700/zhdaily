package com.witkey.coder.zhdaily;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Fragment基类，包含复用的函数
 *
 */
public abstract class BaseFragment extends Fragment {
    private boolean processing = true;
    private int previousAll = 0;

    // 设置Recyclerview
    void setRecyclerViewLayoutManager(RecyclerView recyclerView) {
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 可见的item数量
                int itemsInView = recyclerView.getChildCount();
                // 总item数量
                int currentAll = layoutManager.getItemCount();
                // 最顶端可见item的位置
                int topVisibleItemPosition = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();

//                改变主页左上角显示的文字
//                View view = recyclerView.getChildAt(topVisibleItemPosition);
//                if (view != null){
//                    if(view.getTag().equals("date")){
//                    // notify change title bar
//                    }
//                }

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
                        onBottom();
                    }
                }

                // 当顶部位置为显示内容的两倍时，调用deepin，
                // 目前功能只触发回到顶部按钮
                if (topVisibleItemPosition > itemsInView << 1) {
                    onDeepIn();
                }
            }
        });
    }

    protected abstract void onBottom();
    protected abstract void onDeepIn();

//    public void onChangeDate(String date){}
}
