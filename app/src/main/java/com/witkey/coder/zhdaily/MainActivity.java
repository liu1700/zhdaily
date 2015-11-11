package com.witkey.coder.zhdaily;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.witkey.coder.zhdaily.adapters.StoryPagerAdapter;
import com.witkey.coder.zhdaily.db.CircleDB;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startService(new Intent(DailyApp.getAppContext(), CircleDB.class));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // 设置首页的pager
        final StoryPagerAdapter storyPagerAdapter = new StoryPagerAdapter(fragmentManager);
        // 设置 demo 数据
        ArrayList<String> demoTitle = new ArrayList<>();
        demoTitle.add("首页");
        demoTitle.add("用户推荐");
        demoTitle.add("媒体专栏");
        demoTitle.add("动漫日报");
        demoTitle.add("互联网安全");
        demoTitle.add("不许无聊");
        demoTitle.add("电影日报");
        demoTitle.add("大公司日报");
        demoTitle.add("设计日报");
        storyPagerAdapter.setPageTitle(demoTitle);

        ViewPager viewPager = (ViewPager) findViewById(R.id.main_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_pager_tap);

        viewPager.setAdapter(storyPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment selected = storyPagerAdapter.getItem(position);
                fragmentManager.beginTransaction().replace(R.id.content_frame, selected).commit();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(DailyApp.getAppContext(), CircleDB.class));
    }
}
