package com.witkey.coder.zhdaily.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.witkey.coder.zhdaily.MainFragment;

import java.util.ArrayList;

/**
 * 首页Swip view adapter
 *
 */
public class StoryPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<String> pageTitle;

    public StoryPagerAdapter(FragmentManager fm) {
        super(fm);
        pageTitle = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return new MainFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle.get(position);
    }

    @Override
    public int getCount() {
        return pageTitle.size();
    }

    public void setPageTitle(ArrayList<String> title) {
        pageTitle = title;
    }
}
