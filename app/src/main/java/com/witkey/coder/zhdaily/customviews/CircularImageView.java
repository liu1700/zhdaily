package com.witkey.coder.zhdaily.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 自定义圆形ImageView
 */
public class CircularImageView extends ImageView {
    private static final Bitmap.Config CONFIG = Bitmap.Config.ARGB_8888;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        int w = getWidth();
        int y = getHeight();
        if (w == 0 || y == 0) return;

        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        // 按照ARGB_8888拷贝压缩，防止图片过大oom
        Bitmap bitmap = b.copy(CONFIG, true);

        Bitmap circularBitmap = toCircularBitmap(bitmap, w);
        canvas.drawBitmap(circularBitmap, 0, 0, null);
    }

    private static Bitmap toCircularBitmap(Bitmap bitmap, int radius) {
        Bitmap dst;
        // 首先缩放图片到指定半径大小
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius) {
            dst = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
        } else {
            dst = bitmap;
        }
        Bitmap output = Bitmap.createBitmap(dst.getWidth(),
                dst.getHeight(), CONFIG);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, dst.getWidth(), dst.getHeight());

        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawCircle(dst.getWidth() / 2,
                dst.getHeight() / 2, dst.getWidth() / 3,
                paint);

        // 图片混合SRC_IN可以使方形被圆形裁切
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(dst, rect, rect, paint);

        return output;
    }
}
