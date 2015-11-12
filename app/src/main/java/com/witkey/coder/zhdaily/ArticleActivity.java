package com.witkey.coder.zhdaily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.witkey.coder.zhdaily.adapters.ArticlePagerAdapter;
import com.witkey.coder.zhdaily.adapters.LoopPagerAdapterWrapper;

public class ArticleActivity extends AppCompatActivity {
    private static final String TO_ARTICLE = "TO_ARTICLE";
    private int articleId;
    private FragmentManager fragmentManager;
    private LoopPagerAdapterWrapper loopPagerAdapterWrapper;
    private int swipeDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.article_toolbar);
        toolbar.setCollapsible(true);
        setSupportActionBar(toolbar);
        // 设置文章页的toolbar，移除标题显示退回箭头
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayShowTitleEnabled(false);

            // 设置自定义的toolbar
            View customBar = getLayoutInflater().inflate(R.layout.toolbar_article, null);
            LayoutParams layoutParams = new LayoutParams(Gravity.END);
            bar.setCustomView(customBar, layoutParams);
            bar.setDisplayShowCustomEnabled(true);
        }

        Intent intent = getIntent();
        articleId = intent.getIntExtra(TO_ARTICLE, 0);

        fragmentManager = getSupportFragmentManager();
        ArticlePagerAdapter articlePagerAdapter = new ArticlePagerAdapter(fragmentManager, articleId);
        loopPagerAdapterWrapper = new LoopPagerAdapterWrapper(articlePagerAdapter);

        ViewPager viewPager = (ViewPager) findViewById(R.id.article_pager);
        viewPager.setAdapter(loopPagerAdapterWrapper);
//        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//                if (position < -1) {
//                    swipeDirection = -1;
//                } else if (position > 1) {
//                    swipeDirection = 1;
//                }
//            }
//        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                int index = DailyApp.getStoryIdList().indexOf(articleId);
//                if (swipeDirection == -1 && index != 0) {
//                    onPageSelect(position, index - 1);
//                } else if (swipeDirection == 1 && index != DailyApp.getStoryIdList().size() - 1) {
//                    onPageSelect(position, index + 1);
//                }
                onPageSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    void onPageSelect(int position) {
        Fragment viewing = ((ArticlePagerAdapter) loopPagerAdapterWrapper.getRealAdapter())
                .getItem(position);
        Bundle arg = viewing.getArguments();
        // Demo
        arg.putInt(TO_ARTICLE, articleId);
        viewing.setArguments(arg);
        fragmentManager.beginTransaction().replace(R.id.article_frame, viewing).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // 返回主页并清除back stack
                Intent backToMain = new Intent(getApplication(), MainActivity.class);
                backToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(backToMain, 0);
                this.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
