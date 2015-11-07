package com.witkey.coder.zhdaily.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Article 类
 *
 */
public class Article {
    private String body;

    @SerializedName("image_source")
    private String imageSource;
    private String title;
    private String image;

    @SerializedName("share_url")
    private String shareUrl;

    @SerializedName("ga_prefix")
    private String gaPrefix;
    private int type;
    private int id;
    private ArrayList<String> js;
    private ArrayList<String> css;

    public Article(String body, String imageSource, String title,
                   String image, String shareUrl,
                   ArrayList<String> js, String gaPrefix,
                   int type, int id, ArrayList<String> css) {
        this.body = body;
        this.imageSource = imageSource;
        this.title = title;
        this.image = image;
        this.shareUrl = shareUrl;
        this.js = js;
        this.gaPrefix = gaPrefix;
        this.type = type;
        this.id = id;
        this.css = css;
    }

    public String getBody() {
        return body;
    }
}
