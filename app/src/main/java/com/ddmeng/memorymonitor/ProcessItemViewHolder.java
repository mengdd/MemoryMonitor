package com.ddmeng.memorymonitor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProcessItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.app_name)
    TextView appNameView;
    @BindView(R.id.process_name)
    TextView processNameView;
    @BindView(R.id.pid)
    TextView pidView;
    @BindView(R.id.total_private_dirty)
    TextView totalPrivateDirtyView;
    @BindView(R.id.native_heap)
    TextView nativeHeapView;
    @BindView(R.id.java_heap)
    TextView javaHeapView;

    public ProcessItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(ProcessData processData) {
        Context context = itemView.getContext();
        appNameView.setText(String.format(Locale.US, context.getString(R.string.application_name), processData.getAppName()));
        processNameView.setText(String.format(Locale.US, context.getString(R.string.process_name), processData.getProcessName()));
        pidView.setText(String.format(Locale.US, context.getString(R.string.process_pid), processData.getPid()));
        totalPrivateDirtyView.setText(String.format(Locale.US, context.getString(R.string.total_private_dirty), processData.getTotalPrivateDirty()));

        nativeHeapView.setText(String.format(Locale.US, context.getString(R.string.native_heap), processData.getNativeHeap()));
        javaHeapView.setText(String.format(Locale.US, context.getString(R.string.java_heap), processData.getJavaHeap()));
    }
}
