package com.witkey.coder.zhdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.witkey.coder.zhdaily.R;
import com.witkey.coder.zhdaily.models.Feed;
import com.witkey.coder.zhdaily.models.ImageSlider;

import java.util.ArrayList;
import java.util.List;

/**
 * FeedAdapter 填充首页feed流
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FEED = 1;
    private static final int TYPE_DATE = 2;

    private List<Object> dataset = new ArrayList<>();
    private Context ctx;

    public FeedAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public class FlipperViewHolder extends RecyclerView.ViewHolder{
        public final View flipperView;

        public FlipperViewHolder(View v) {
            super(v);
            flipperView = v;
        }
    }

    // CardViewHolder 用于处理信息流中的feed
    public class CardViewHolder extends RecyclerView.ViewHolder {
        public final View cardView;

        public CardViewHolder(View v) {
            super(v);
            cardView = v;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(ctx, "Hello", Toast.LENGTH_SHORT).show();
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

    // DateTextViewHolder 用于处理信息流中的日期文字
    public class DateTextViewHolder extends RecyclerView.ViewHolder {
        public final View dateTextView;

        public DateTextViewHolder(View v) {
            super(v);
            dateTextView = v;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 根据View type 创建不同的view holder
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case TYPE_FEED:
                view = layoutInflater.inflate(R.layout.card_view_main, parent, false);
                return new CardViewHolder(view);
            case TYPE_DATE:
                view = layoutInflater.inflate(R.layout.date_text_main, parent, false);
                return new DateTextViewHolder(view);
            case TYPE_HEADER:
                view = layoutInflater.inflate(R.layout.head_flipper_main, parent, false);
                return new FlipperViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(dataset.isEmpty()) return;

        // 根据holder种类进行数据的渲染预处理
        if (holder instanceof CardViewHolder) {
            Feed f = (Feed)dataset.get(position);
            View v = ((CardViewHolder)holder).cardView;
            v.setId(position);

        } else if (holder instanceof DateTextViewHolder) {
            View v = ((DateTextViewHolder)holder).dateTextView;
            TextView date = (TextView)v.findViewById(R.id.dateText);
            date.setText((String)dataset.get(position));

        } else if (holder instanceof FlipperViewHolder) {
            ArrayList<ImageSlider> imageSliders = (ArrayList<ImageSlider>)dataset.get(position);
            View v = ((FlipperViewHolder)holder).flipperView;
            ViewGroup parent = (ViewGroup)v.getParent();

            ViewFlipper viewFlipper= (ViewFlipper)v.findViewById(R.id.head_flipper);
            for (ImageSlider imageSlider : imageSliders) {
                View flipperChildView = LayoutInflater.from(ctx).inflate(R.layout.flipper_child_main, parent);
                TextView childTextView = (TextView) flipperChildView.findViewById(R.id.flipperText);
                childTextView.setText(imageSlider.getTitle());

                viewFlipper.addView(flipperChildView);
            }
            viewFlipper.setAutoStart(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 根据数据辨别是是哪种view
        if (position == 0) {
            return TYPE_HEADER;
        } else if (dataset.get(position) instanceof String) {
            return TYPE_DATE;
        } else {
            return TYPE_FEED;
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(ArrayList<Object> d) {
        dataset = d;
    }
}
