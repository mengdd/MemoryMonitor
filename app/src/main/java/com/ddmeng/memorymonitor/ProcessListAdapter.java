package com.ddmeng.memorymonitor;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ProcessListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<ProcessData> processData;

    public void setProcessData(List<ProcessData> processData) {
        this.processData = processData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.process_item_view_holder, parent, false);
        return new ProcessItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof ProcessItemViewHolder) {
            ((ProcessItemViewHolder) holder).populate(processData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return processData != null ? processData.size() : 0;
    }
}
