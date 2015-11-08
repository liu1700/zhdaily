package com.witkey.coder.zhdaily.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Adapter 基类其他Adapter由此拓展
 *
 */
public abstract class BasicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context ctx;
    ArrayList<Object> dataset = new ArrayList<>();

    @Override
    abstract public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    abstract public void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(ArrayList<Object> d) {
        dataset = d;
    }
}
