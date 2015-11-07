package com.witkey.coder.zhdaily.models;

import java.util.ArrayList;

/**
 * 首页Story类
 */
public class Story {
    private int id;
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
}
