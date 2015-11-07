package com.witkey.coder.zhdaily.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.witkey.coder.zhdaily.ArticleActivity;
import com.witkey.coder.zhdaily.R;
import com.witkey.coder.zhdaily.models.Feed;
import com.witkey.coder.zhdaily.models.ImageFlipper;
import com.witkey.coder.zhdaily.utils.FlingListener;
import com.witkey.coder.zhdaily.utils.GestureListener;

import java.util.ArrayList;

/**
 * FeedAdapter 填充首页feed流
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FEED = 1;
    private static final int TYPE_DATE = 2;

    private static final int FLIPPER_INTERVAL = 5000;

    private ArrayList<Object> dataset = new ArrayList<>();
    private Context ctx;

    public FeedAdapter(Context ctx) {
        this.ctx = ctx;
    }

    // FlipperViewHolder 首页顶部的轮播
    public class FlipperViewHolder extends RecyclerView.ViewHolder implements FlingListener {
        public final View flipperView;
        private Animation.AnimationListener animationListener;
        GestureListener gestureListener = new GestureListener();
        final GestureDetector gd;

        public FlipperViewHolder(View v) {
            super(v);
            flipperView = v;
            // 注册Flipper listener
            gestureListener.registeListener(this);
            GestureDetector.SimpleOnGestureListener simpleOnGestureListener = gestureListener;
            gd = new GestureDetector(ctx, simpleOnGestureListener);
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gd.onTouchEvent(event);
                    return true;
                }
            });
        }

        @Override
        public void doAnimation(int type) {
            // 根据监听的滑动感应方向使用动画
            ViewFlipper v = ((ViewFlipper)flipperView);
            if (type == 2) {
                v.setInAnimation(AnimationUtils.loadAnimation(ctx, R.anim.left_in));
                v.setOutAnimation(AnimationUtils.loadAnimation(ctx, R.anim.left_out));

                v.getInAnimation().setAnimationListener(animationListener);
                v.showNext();
            } else {
                v.setInAnimation(AnimationUtils.loadAnimation(ctx, R.anim.right_in));
                v.setOutAnimation(AnimationUtils.loadAnimation(ctx, R.anim.right_out));

                v.getInAnimation().setAnimationListener(animationListener);
                v.showPrevious();
            }
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
                    Intent intent = new Intent(ctx, ArticleActivity.class);
//                    Feed feed = dataset.get(cardView.getId());
//                    Gson detail = new Gson();
//                    CircleCache.put(R.string.k_cur_feed_user, feedClass.userId);
//                    CircleCache.put(R.string.k_cur_feed, feedClass.feedId);
//                    intent.putExtra(TO_FEED_DETAIL, detail.toJson(feedClass));
                    ctx.startActivity(intent);
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

        // 根据holder种类进行数据的填充
        if (holder instanceof CardViewHolder) {
            Feed f = (Feed)dataset.get(position);
            View v = ((CardViewHolder)holder).cardView;
            v.setId(position);

        } else if (holder instanceof DateTextViewHolder) {
            View v = ((DateTextViewHolder)holder).dateTextView;
            TextView date = (TextView)v.findViewById(R.id.dateText);
            date.setText((String)dataset.get(position));

        } else if (holder instanceof FlipperViewHolder) {
            ArrayList<ImageFlipper> imageFlippers = (ArrayList<ImageFlipper>)dataset.get(position);
            View v = ((FlipperViewHolder)holder).flipperView;
            ViewFlipper viewFlipper = (ViewFlipper)v;
            ViewGroup parent = (ViewGroup)v.getParent();

            // 为每个flipper设置内容
            for (ImageFlipper imageFlipper : imageFlippers) {
                View flipperChildView = LayoutInflater.from(ctx).inflate(R.layout.flipper_child_main, parent);
                TextView childTextView = (TextView) flipperChildView.findViewById(R.id.flipper_text);
                childTextView.setText(imageFlipper.getTitle());

                viewFlipper.addView(flipperChildView);
            }
            viewFlipper.setFlipInterval(FLIPPER_INTERVAL);
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
