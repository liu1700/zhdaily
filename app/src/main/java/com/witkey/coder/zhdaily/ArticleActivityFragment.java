package com.witkey.coder.zhdaily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.witkey.coder.zhdaily.adapters.ArticleAdapter;
import com.witkey.coder.zhdaily.models.Article;
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

    public ArticleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        Intent intent = getActivity().getIntent();
        articleId = intent.getIntExtra(TO_ARTICLE, 0);

//        dataStream.add(new Article("<h2>Title</h2><br><p>Description here</p>", "1", "2", "3", "4", "5", "6", 1, 2, arrayList));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.article_view);
        setRecyclerViewLayoutManager(recyclerView);
        articleAdapter = new ArticleAdapter(getActivity());
        recyclerView.setAdapter(articleAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        retrieveArticle(articleId);
    }

    private void retrieveArticle(int id) {
        Networking.get(String.format("%s%d", Networking.ARTICLE_DETAIL, id), Article.class, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DailyApp.getAppContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Toast.makeText(DailyApp.getAppContext(), "E", Toast.LENGTH_SHORT).show();
                Article article = (Article) response;
                dataStream.add(article);

                articleAdapter.setDataset(dataStream);
                articleAdapter.notifyDataSetChanged();
            }
        });
    }
}
