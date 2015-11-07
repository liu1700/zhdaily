package com.witkey.coder.zhdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.witkey.coder.zhdaily.R;
import com.witkey.coder.zhdaily.customviews.FadeInImageView;
import com.witkey.coder.zhdaily.models.Article;
import com.witkey.coder.zhdaily.networking.Networking;

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

        // 使用webview渲染文章主体，并嵌入css文件
        WebView webView = (WebView) v.findViewById(R.id.article_rich_text_view);
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><link href=\"");
        sb.append(article.getCss().get(0));
        sb.append("\" type=\"text/css\" rel=\"stylesheet\"/></head><body>");

//        我猜测有可能是这种做法
//        String b = article.getBody().replace("<div class=\"img-place-holder\"></div>",
//                "<div class=\"img-wrap\">" +
//                        "<img src=\"" + article.getImage() + "\">" +
//                        "<span class=\"headline-title\">" + article.getTitle() + "</span>" +
//                        "<span class=\"img-source\">" + article.getImageSource() + "</span>" +
//                        "</div>");

        String b = article.getBody().replace("<div class=\"img-place-holder\"></div>","");
        sb.append(b);
        sb.append("</body></html>");
        webView.loadData(sb.toString(), "text/html;charset=UTF-8", null);

        FadeInImageView fadeInImageView = (FadeInImageView) v.findViewById(R.id.article_head_image);
        Networking.loadImage(article.getImage(), fadeInImageView);

        TextView title = (TextView) v.findViewById(R.id.article_head_text);
        TextView imgSource = (TextView) v.findViewById(R.id.article_image_source);

        title.setText(article.getTitle());
        imgSource.setText(article.getImageSource());
    }
}
