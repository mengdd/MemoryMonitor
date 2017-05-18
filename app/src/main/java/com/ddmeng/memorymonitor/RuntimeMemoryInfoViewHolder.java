package com.ddmeng.memorymonitor;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RuntimeMemoryInfoViewHolder {
    private View view;

    @BindView(R.id.runtime_total_memory)
    TextView totalMemoryView;
    @BindView(R.id.runtime_free_memory)
    TextView freeMemoryView;
    @BindView(R.id.runtime_allocated_memory)
    TextView allocatedMemoryView;
    @BindView(R.id.runtime_max_memory)
    TextView maxMemoryView;

    public RuntimeMemoryInfoViewHolder(View view) {
        this.view = view;
        ButterKnife.bind(this, view);
    }

    public void populate(Runtime runtime) {
        Context context = view.getContext();
        totalMemoryView.setText(String.format(Locale.US, context.getString(R.string.runtime_total_memory), runtime.totalMemory() / 1024));
        freeMemoryView.setText(String.format(Locale.US, context.getString(R.string.runtime_free_memory), runtime.freeMemory() / 1024));
        long allocatedMemory = runtime.totalMemory() - runtime.freeMemory();
        allocatedMemoryView.setText(String.format(Locale.US, context.getString(R.string.runtime_allocated_memory), allocatedMemory / 1024));
        maxMemoryView.setText(String.format(Locale.US, context.getString(R.string.runtime_max_memory), runtime.maxMemory() / 1024));
    }
}
