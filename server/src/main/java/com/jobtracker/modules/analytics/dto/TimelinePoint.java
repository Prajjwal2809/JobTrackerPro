package com.jobtracker.modules.analytics.dto;

public class TimelinePoint {

    private String period;
    private long count;
    public TimelinePoint(String period, long count) {
        this.period = period;
        this.count = count;
    }
    public String getPeriod() {
        return period;
    }
    public long getCount() {
        return count;
    }
    
}
