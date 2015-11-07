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

import com.google.gson.Gson;
import com.witkey.coder.zhdaily.ArticleActivity;
import com.witkey.coder.zhdaily.R;
import com.witkey.coder.zhdaily.customviews.FadeInImageView;
import com.witkey.coder.zhdaily.models.Story;
import com.witkey.coder.zhdaily.models.ImageFlipper;
import com.witkey.coder.zhdaily.networking.Networking;
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
    private static final String TO_ARTICLE = "TO_ARTICLE";


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
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Story story = (Story)dataset.get(cardView.getId());
                    intent.putExtra(TO_ARTICLE, story.getId());
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
            Story f = (Story)dataset.get(position);
            View v = ((CardViewHolder)holder).cardView;
            v.setId(position);

            TextView title = (TextView) v.findViewById(R.id.story_title);
            TextView type = (TextView) v.findViewById(R.id.story_type);
            FadeInImageView fadeInImageView = (FadeInImageView) v.findViewById(R.id.story_thumbnail);

            title.setText(f.getTitle());
            type.setText(String.format("%s %d", "类型:", f.getType()));
            Networking.loadImage(f.getImages().get(0), fadeInImageView);

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
