package com.witkey.coder.zhdaily.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.witkey.coder.zhdaily.ArticleActivityFragment;

/**
 * Article页Pager
 *
 */
public class ArticlePagerAdapter extends FragmentPagerAdapter {
    private static final String TO_ARTICLE = "TO_ARTICLE";
    private int firstEntryId;

    public ArticlePagerAdapter(FragmentManager fm, int firstId) {
        super(fm);
        firstEntryId = firstId;
    }

    @Override
    public Fragment getItem(int position) {
//        position = LoopViewPager.toRealPosition(position, getCount());
        ArticleActivityFragment fragment = new ArticleActivityFragment();
        Bundle arg = new Bundle();
        arg.putInt(TO_ARTICLE, firstEntryId);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
