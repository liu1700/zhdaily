package com.witkey.coder.zhdaily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.witkey.coder.zhdaily.adapters.ArticleAdapter;
import com.witkey.coder.zhdaily.models.Article;
import com.witkey.coder.zhdaily.models.ArticleExtra;
import com.witkey.coder.zhdaily.networking.Networking;

import java.util.ArrayList;

/**
 * 文章页Fragment
 */
public class ArticleActivityFragment extends BaseFragment {
    private static final String TO_ARTICLE = "TO_ARTICLE";
    private int articleId;
    private ArticleAdapter articleAdapter;
    private ArrayList<Object> dataStream = new ArrayList<>();

    private TextView comments;
    private TextView likes;

    public ArticleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        View barView = bar.getCustomView();

        comments = (TextView) barView.findViewById(R.id.article_comments);
        likes = (TextView) barView.findViewById(R.id.article_likes);
        // 获取文章id
        Intent intent = getActivity().getIntent();
        articleId = intent.getIntExtra(TO_ARTICLE, 0);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.article_view);
        setRecyclerViewLayoutManager(recyclerView);
        articleAdapter = new ArticleAdapter(getActivity());
        recyclerView.setAdapter(articleAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Thread articleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                retrieveArticle(articleId);
                retrieveArticalExtra(articleId);
            }
        });
        articleThread.start();
    }

    private void retrieveArticle(int id) {
        Networking.get(String.format("%s%d", Networking.ARTICLE_DETAIL, id), Article.class, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Article article = (Article) response;
                dataStream.add(article);

                articleAdapter.setDataset(dataStream);
                articleAdapter.notifyDataSetChanged();
            }
        });
    }

    private void retrieveArticalExtra(int id) {
        Networking.get(String.format("%s%d", Networking.ARTICLE_DETAIL_EXTRA, id), ArticleExtra.class, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ArticleExtra extra = (ArticleExtra) response;
                comments.setText(extra.getComments());
                likes.setText(extra.getPopularity());
            }
        });
    }
}
