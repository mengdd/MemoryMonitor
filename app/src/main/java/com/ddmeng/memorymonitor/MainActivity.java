package com.ddmeng.memorymonitor;

import android.app.ActivityManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @BindView(R.id.processes_list)
    RecyclerView processesRecyclerView;
    @BindView(R.id.main_container)
    View mainContainer;

    private ProcessListAdapter processListAdapter;
    private DeviceMemoryInfoViewHolder deviceMemoryInfoViewHolder;
    private RuntimeMemoryInfoViewHolder runtimeMemoryInfoViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        processesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        processListAdapter = new ProcessListAdapter();
        processesRecyclerView.setAdapter(processListAdapter);
        deviceMemoryInfoViewHolder = new DeviceMemoryInfoViewHolder(mainContainer);
        runtimeMemoryInfoViewHolder = new RuntimeMemoryInfoViewHolder(mainContainer);
    }

    private void refreshData() {
        getSystemMemoryInfo();
        getRuntimeMemory();
        getProcessesMemoryInfo();
    }

    private void getRuntimeMemory() {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.d(TAG, String.format(Locale.US, "Runtime totalMemory = %,d kB, freeMemory = %,d kB, maxMemory = %,d kB",
                totalMemory / 1024, freeMemory / 1024, maxMemory / 1024));
        runtimeMemoryInfoViewHolder.populate(Runtime.getRuntime());
    }

    private void getSystemMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        Log.d(TAG, String.format(Locale.US, "ActivityManager.memoryInfo %d availMem, %b lowMemory, %d threshold",
                memoryInfo.availMem, memoryInfo.lowMemory, memoryInfo.threshold));

        int memoryClass = activityManager.getMemoryClass();
        int largeMemoryClass = activityManager.getLargeMemoryClass();
        Log.d(TAG, String.format(Locale.US, "getMemoryClass() = %d , getLargeMemoryClass() = %d ", memoryClass, largeMemoryClass));

        deviceMemoryInfoViewHolder.populate(memoryInfo, memoryClass, largeMemoryClass);
    }

    private void getProcessesMemoryInfo() {
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
        Log.d(TAG, "processes: " + processes.size());
        List<ProcessData> processDataList = new ArrayList<>();
        int[] pidArray = new int[processes.size()];
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int n = 0;
        for (AndroidAppProcess process : processes) {
            try {
                PackageInfo packageInfo = process.getPackageInfo(this, 0);
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                ProcessData processData = new ProcessData();
                Stat stat = process.stat();
                processData.setPid(stat.getPid());
                pidArray[n++] = stat.getPid();
                processData.setAppName(appName);
                processData.setProcessName(process.name);

                processDataList.add(processData);
            } catch (PackageManager.NameNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(pidArray);
        for (int i = 0; i < processMemoryInfo.length; ++i) {
            ProcessData processData = processDataList.get(i);
            Debug.MemoryInfo memoryInfo = processMemoryInfo[i];

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                processData.setMemoryStatus(memoryInfo.getMemoryStats());
            }

            processData.setTotalPrivateClean(memoryInfo.getTotalPrivateClean());
            processData.setTotalPrivateDirty(memoryInfo.getTotalPrivateDirty());
            processData.setTotalPss(memoryInfo.getTotalPss());
            processData.setTotalSharedClean(memoryInfo.getTotalSharedClean());
            processData.setTotalSharedDirty(memoryInfo.getTotalSharedDirty());
            processData.setTotalSwappablePss(memoryInfo.getTotalSwappablePss());

            Log.i(TAG, "=: " + processData.toString());
        }
        processListAdapter.setProcessData(processDataList);
        processListAdapter.notifyDataSetChanged();
        List<ActivityManager.RunningAppProcessInfo> processesInfo = AndroidProcesses.getRunningAppProcessInfo(this);
        Log.d(TAG, "processesInfo: " + processesInfo.size());
    }

    @OnClick(R.id.refresh_button)
    void onRefreshClicked() {
        refreshData();
    }

    private ArrayList<byte[]> bytesContainer = new ArrayList<>();

    @OnClick(R.id.allocate_memory)
    void onAllocateMemoryClicked() {
        byte[] b = new byte[100 * 1024 * 1024];
        bytesContainer.add(b);
        Log.d(TAG, "create 100 MB");
    }
}
