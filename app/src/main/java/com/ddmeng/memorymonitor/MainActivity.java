package com.ddmeng.memorymonitor;

import android.app.ActivityManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @BindView(R.id.processes_list)
    RecyclerView processesRecyclerView;
    private ProcessListAdapter processListAdapter;

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
    }

    private void refreshData() {
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
        Log.d(TAG, "processes: " + processes.size());
        List<ProcessData> processDataList = new ArrayList<>();
        for (AndroidAppProcess process : processes) {
            try {
                PackageInfo packageInfo = process.getPackageInfo(this, 0);
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Log.d(TAG, "appName: " + appName);
                ProcessData processData = new ProcessData();
                processData.setPid(process.stat().getPid());
                processData.setAppName(appName);
                processData.setProcessName(process.name);
                processDataList.add(processData);
            } catch (PackageManager.NameNotFoundException | IOException e) {
                e.printStackTrace();
            }
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
}
