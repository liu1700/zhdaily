package com.witkey.coder.zhdaily.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.witkey.coder.zhdaily.R;

/**
 * 自定义圆形ImageView
 */
public class CircularImageView extends ImageView {
    private static Context mCtx;
    private static final Bitmap.Config CONFIG = Bitmap.Config.ARGB_8888;

    public CircularImageView(Context context) {
        super(context);
        mCtx = context;
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCtx = context;
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCtx = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        int w = getWidth(), h = getHeight();

        if (w == 0 || h == 0) return;

        // 压缩图片防止out of memory
        Bitmap curBitmap = ((BitmapDrawable)drawable).getBitmap();
        Bitmap cpBitmap = curBitmap.copy(CONFIG, true);

        // 以宽度为基准缩放
        int radius = w < h ? w : h;
        Bitmap roundBitmap = toRoundBitmap(cpBitmap, radius);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                    false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), CONFIG);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                finalBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(ContextCompat.getColor(mCtx, R.color.blue50));
        canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
                finalBitmap.getHeight() / 2 + 0.7f,
                finalBitmap.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);

        return output;
    }
}
