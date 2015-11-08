package com.witkey.coder.zhdaily.models;

import com.google.gson.annotations.SerializedName;

/**
 * Article 的附属信息
 *
 */
public class ArticleExtra {
    @SerializedName("long_comments")
    private int longComments;
    private int popularity;
    @SerializedName("short_comments")
    private int shortComments;
    private int comments;

    public ArticleExtra(int longComments, int popularity, int shortComments, int comments) {
        this.longComments = longComments;
        this.popularity = popularity;
        this.shortComments = shortComments;
        this.comments = comments;
    }

    public String getPopularity() {
        return String.valueOf(popularity);
    }

    public String getComments() {
        return String.valueOf(comments);
    }
}
