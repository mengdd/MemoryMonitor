package com.ddmeng.memorymonitor;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceMemoryInfoViewHolder {

    private View view;

    @BindView(R.id.memory_info_avail_mem)
    TextView availMemView;
    @BindView(R.id.memory_info_low_memory)
    TextView lowMemView;
    @BindView(R.id.memory_info_threshold)
    TextView thresholdView;
    @BindView(R.id.memory_info_total_mem)
    TextView totalMemView;
    @BindView(R.id.memory_class)
    TextView memoryClassView;
    @BindView(R.id.large_memory_class)
    TextView largeMemoryClassView;

    public DeviceMemoryInfoViewHolder(View view) {
        this.view = view;
        ButterKnife.bind(this, view);
    }

    public void populate(ActivityManager.MemoryInfo memoryInfo, int memoryClass, int largeMemoryClass) {
        Context context = view.getContext();
        availMemView.setText(String.format(Locale.US, context.getString(R.string.available_memory_on_system), memoryInfo.availMem / 1024));
        lowMemView.setText(String.format(Locale.US, context.getString(R.string.low_memory_flag), memoryInfo.lowMemory));
        thresholdView.setText(String.format(Locale.US, context.getString(R.string.memory_threshold), memoryInfo.threshold / 1024));
        totalMemView.setText(String.format(Locale.US, context.getString(R.string.total_memory), memoryInfo.totalMem / 1024));
        memoryClassView.setText(String.format(Locale.US, context.getString(R.string.memory_class), memoryClass));
        largeMemoryClassView.setText(String.format(Locale.US, context.getString(R.string.large_memory_class), largeMemoryClass));

    }
}
