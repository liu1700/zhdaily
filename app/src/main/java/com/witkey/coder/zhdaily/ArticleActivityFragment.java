package com.witkey.coder.zhdaily;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.witkey.coder.zhdaily.adapters.ArticleAdapter;
import com.witkey.coder.zhdaily.models.Article;

import java.util.ArrayList;

/**
 * 文章页Fragment
 */
public class ArticleActivityFragment extends BaseFragment {
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

        dataStream.add(new Article("<h2>Title</h2><br><p>Description here</p>", "1", "2", "3", "4", "5", "6", 1, 2, arrayList));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.article_view);
        setRecyclerViewLayoutManager(recyclerView);
        articleAdapter = new ArticleAdapter(getActivity());
        recyclerView.setAdapter(articleAdapter);

        articleAdapter.setDataset(dataStream);
        articleAdapter.notifyDataSetChanged();

        return rootView;
    }
}
