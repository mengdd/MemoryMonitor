package com.ddmeng.memorymonitor;

import java.util.Map;

public class ProcessData {

    private String appName;
    private String processName;
    private int pid;

    private Map<String, String> memoryStatus;
    private int totalPrivateClean;
    private int totalPrivateDirty;
    private int totalPss;
    private int totalSharedClean;
    private int totalSharedDirty;
    private int totalSwappablePss;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public Map<String, String> getMemoryStatus() {
        return memoryStatus;
    }

    public void setMemoryStatus(Map<String, String> memoryStatus) {
        this.memoryStatus = memoryStatus;
    }

    public int getTotalPrivateClean() {
        return totalPrivateClean;
    }

    public void setTotalPrivateClean(int totalPrivateClean) {
        this.totalPrivateClean = totalPrivateClean;
    }

    public int getTotalPrivateDirty() {
        return totalPrivateDirty;
    }

    public void setTotalPrivateDirty(int totalPrivateDirty) {
        this.totalPrivateDirty = totalPrivateDirty;
    }

    public int getTotalPss() {
        return totalPss;
    }

    public void setTotalPss(int totalPss) {
        this.totalPss = totalPss;
    }

    public int getTotalSharedClean() {
        return totalSharedClean;
    }

    public void setTotalSharedClean(int totalSharedClean) {
        this.totalSharedClean = totalSharedClean;
    }

    public int getTotalSharedDirty() {
        return totalSharedDirty;
    }

    public void setTotalSharedDirty(int totalSharedDirty) {
        this.totalSharedDirty = totalSharedDirty;
    }

    public int getTotalSwappablePss() {
        return totalSwappablePss;
    }

    public void setTotalSwappablePss(int totalSwappablePss) {
        this.totalSwappablePss = totalSwappablePss;
    }

    public int getJavaHeap() {
        return memoryStatus != null ? Integer.valueOf(memoryStatus.get("summary.java-heap")) : 0;
    }

    public int getNativeHeap() {
        return memoryStatus != null ? Integer.valueOf(memoryStatus.get("summary.native-heap")) : 0;
    }

    @Override
    public String toString() {
        return "ProcessData{" +
                "appName='" + appName + '\'' +
                ", processName='" + processName + '\'' +
                ", pid=" + pid +
                ", memoryStatus=" + memoryStatus +
                ", totalPrivateClean=" + totalPrivateClean +
                ", totalPrivateDirty=" + totalPrivateDirty +
                ", totalPss=" + totalPss +
                ", totalSharedClean=" + totalSharedClean +
                ", totalSharedDirty=" + totalSharedDirty +
                ", totalSwappablePss=" + totalSwappablePss +
                '}';
    }
}
