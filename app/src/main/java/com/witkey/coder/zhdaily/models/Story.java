package com.witkey.coder.zhdaily.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 首页Story类
 */
public class Story {
    private int id;
    @SerializedName("ga_prefix")
    private String gaPrefix;
    private String title;
    private ArrayList<String> images;
    private int tyoe;
    private boolean multipic;

    public Story(int id, String title, ArrayList<String> images, int type, boolean multipic) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.tyoe = type;
        this.multipic = multipic;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public int getType() {
        return tyoe;
    }

    public boolean isMultipic() {
        return multipic;
    }
}
