package com.witkey.coder.zhdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.witkey.coder.zhdaily.R;
import com.witkey.coder.zhdaily.models.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * FeedAdapter 填充首页feed流
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private List<Feed> dataset = new ArrayList<>();
    private Context ctx;

    public FeedAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = v;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, FeedDetailActivity.class);
//                    Feed feed = dataset.get(cardView.getId());
//                    Gson detail = new Gson();
//                    CircleCache.put(R.string.k_cur_feed_user, feedClass.userId);
//                    CircleCache.put(R.string.k_cur_feed, feedClass.feedId);
//                    intent.putExtra(TO_FEED_DETAIL, detail.toJson(feedClass));
//                    ctx.startActivity(intent);
                }
            });
        }
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedAdapter.ViewHolder holder, int position) {
        if(dataset.isEmpty()) return;
        Feed f = dataset.get(position);
        View v = holder.cardView;
        v.setId(position);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(List<Feed> d) {
        dataset = d;
    }
}
