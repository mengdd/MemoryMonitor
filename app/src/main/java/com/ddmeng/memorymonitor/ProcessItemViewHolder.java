package com.ddmeng.memorymonitor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProcessItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.appName)
    TextView appNameView;
    @BindView(R.id.processName)
    TextView processNameView;
    @BindView(R.id.pid)
    TextView pidView;

    public ProcessItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(ProcessData processData) {
        appNameView.setText(processData.getAppName());
        processNameView.setText(processData.getProcessName());
        pidView.setText(String.valueOf(processData.getPid()));
    }
}
