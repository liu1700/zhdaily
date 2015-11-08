package com.witkey.coder.zhdaily.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 加入淡入效果的ImageView
 *
 */
public class FadeInImageView extends ImageView {

    private static final int FADE_IN_TIME_MS = 400;
    private Context ctx;

    public FadeInImageView(Context context) {
        super(context);
        ctx = context;
    }

    public FadeInImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
    }

    public FadeInImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ctx = context;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                new ColorDrawable(ContextCompat.getColor(ctx, android.R.color.transparent)),
                new BitmapDrawable(getContext().getResources(), bm)
        });

        setImageDrawable(td);
        td.startTransition(FADE_IN_TIME_MS);
    }

}

