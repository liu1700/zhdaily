package com.witkey.coder.zhdaily;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

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
    void configPullToRefresh(PtrFrameLayout layout) {
        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getActivity().getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPtrFrameLayout(layout);
        layout.setHeaderView(header);
        layout.addPtrUIHandler(header);

        layout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onRefresh(frame);
            }
        });
    }

    protected abstract void onBottom();

    protected void onRefresh(PtrFrameLayout frame){
        frame.refreshComplete();
    }

//    public void onChangeDate(String date){}
}
