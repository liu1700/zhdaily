package com.witkey.coder.zhdaily.networking;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.witkey.coder.zhdaily.DailyApp;
import com.witkey.coder.zhdaily.R;

/**
 * Networking 类处理基本的网络请求
 */
public class Networking {

    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/";
    public static final String FEED_STREAM = "stories/before/";
    public static final String ARTICLE_DETAIL = "story/";
    public static final String ARTICLE_DETAIL_EXTRA = "story-extra/";

    private static class Holder{
        static final RequestQueue INSTANCE = Volley.newRequestQueue(DailyApp.getAppContext(), new OkHttpStack());
    }

    private static RequestQueue getInstance() {
        return Holder.INSTANCE;
    }


    public static void get(String method, Class<?> responseClass, ErrorListener errorListener, Listener listener) {
        GsonRequest request = new GsonRequest(
                Request.Method.GET,
                BASE_URL+method,
                null,
                responseClass,
                listener,
                errorListener);

        getInstance().add(request);
    }

    // Check the imageView type: true for network imageview, false for circle imageview
    public static void loadImage(String imgUrl, final ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(DailyApp.getAppContext()));

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_photo_white_36dp) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_photo_white_36dp) // resource or drawable
                .showImageOnFail(R.drawable.ic_photo_white_36dp)
                .cacheInMemory(true).build();

        imageLoader.loadImage(imgUrl, new ImageSize(imageView.getWidth(), imageView.getHeight()), displayImageOptions, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                imageView.setImageResource(R.drawable.ic_photo_white_36dp);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
