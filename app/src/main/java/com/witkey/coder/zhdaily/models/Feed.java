package com.witkey.coder.zhdaily.models;

/**
 * 首页Feed类
 */
public class Feed {
    private String id;
    private String title;
    private String thumbnail;
    private String tag;
    private int timeStamp;
    private boolean isMultiPics;

    public Feed(String id, String title, String thumbnail, String tag, int timeStamp, boolean isMultiPics) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.tag = tag;
        this.timeStamp = timeStamp;
        this.isMultiPics = isMultiPics;
    }
}
