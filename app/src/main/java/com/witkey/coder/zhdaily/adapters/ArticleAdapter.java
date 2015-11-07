package com.witkey.coder.zhdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.witkey.coder.zhdaily.R;
import com.witkey.coder.zhdaily.models.Article;

/**
 * 文章页Adapter 呈现文章页的数据内容
 *
 */
public class ArticleAdapter extends BasicAdapter  {

    public ArticleAdapter(Context context) {
        this.ctx = context;
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{
        public final View contentView;

        public ContentViewHolder(View v) {
            super(v);
            contentView = v;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.content_article, parent, false);

        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (dataset.isEmpty()) return;

        Article article = (Article)dataset.get(position);
        View v = ((ContentViewHolder)holder).contentView;

        TextView richTextView = (TextView) v.findViewById(R.id.article_rich_text_view);
        richTextView.setText(Html.fromHtml(article.getBody()));
    }
}
