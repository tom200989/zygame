package com.zygame.common.bean;

import java.io.Serializable;

/*
 * Created by Administrator on 2020/6/5.
 */
public class PreventBean implements Serializable {

    private long totalPermitDuration;// 最多只允许玩耍的时长
    private long totalDuration;// 一共玩了的时长
    private long lastRecordTime;// 最后一次计算［总玩］的时间

    public long getLastRecordTime() {
        return lastRecordTime;
    }

    public void setLastRecordTime(long lastRecordTime) {
        this.lastRecordTime = lastRecordTime;
    }

    public long getTotalPermitDuration() {
        return totalPermitDuration;
    }

    public long getTotalPermitDuration_Min() {
        return totalPermitDuration / 60L / 1000L;
    }

    public void setTotalPermitDuration(long totalPermitDuration) {
        this.totalPermitDuration = totalPermitDuration;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public long getTotalDuration_Min() {
        return totalDuration / 60L / 1000L;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }
}
