package com.witkey.coder.zhdaily.models;

/**
 * 首页顶部轮播图
 *
 */
public class ImageFlipper {
    private String imageUrl;
    private String cc;
    private String title;
    private String feedId;

    public ImageFlipper(String imageUrl, String cc, String title, String feedId) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.feedId = feedId;
        this.cc = cc;
    }

    public String getFeedId() {
        return feedId;
    }

    public String getTitle() {
        return title;
    }
}
