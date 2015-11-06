package com.witkey.coder.zhdaily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ArticalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.articalToolbar);
        toolbar.setCollapsible(true);
        setSupportActionBar(toolbar);
        // 设置文章页的toolbar，移除标题显示退回箭头
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayShowTitleEnabled(false);

//        bar.setCustomView(R.layout.artical_toolbar, new ActionBar.LayoutParams(new La));
        bar.setDisplayShowCustomEnabled(true);

        Fragment articalActivityFragment = new ArticalActivityFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.artical_frame, articalActivityFragment).commit();
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
        getMenuInflater().inflate(R.menu.menu_artical, menu);
        return true;
    }
}
