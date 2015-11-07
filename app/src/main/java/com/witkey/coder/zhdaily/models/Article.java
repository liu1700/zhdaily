package com.witkey.coder.zhdaily.models;

import java.util.ArrayList;

/**
 * Article 类
 *
 */
public class Article {
    private String body;
    private String imageSource;
    private String title;
    private String image;
    private String shareUrl;
    private String js;
    private String gaPrefix;
    private int type;
    private int id;
    private ArrayList<String> css;

    public Article(String body, String imageSource, String title, String image,
                   String shareUrl, String js, String gaPrefix,
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
