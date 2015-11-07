package com.witkey.coder.zhdaily.models;

import java.util.ArrayList;

/**
 * 主页Stores 数据流
 *
 */
public class Stories {
    private String date;
    private ArrayList<Story> stories;

    public Stories(String date, ArrayList<Story> stories) {
        this.date = date;
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Story> getStories() {
        return stories;
    }
}
