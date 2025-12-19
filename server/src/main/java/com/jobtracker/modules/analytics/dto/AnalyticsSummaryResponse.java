package com.jobtracker.modules.analytics.dto;

import java.util.Map;

public class AnalyticsSummaryResponse {
    
    private long total;
    private Map<String, Long> byStatus;
    private double appliedToInterviewRatio;
    private double interviewToOfferRatio;

    public AnalyticsSummaryResponse(long total, Map<String, Long> byStatus, double appliedToInterviewRatio, double interviewToOfferRatio) {
        this.total = total;
        this.byStatus = byStatus;
        this.appliedToInterviewRatio = appliedToInterviewRatio;
        this.interviewToOfferRatio = interviewToOfferRatio;
    }
    public long getTotal() {
        return total;
    }
    public Map<String, Long> getByStatus() {
        return byStatus;
    }
    public double getAppliedToInterviewRatio() {
        return appliedToInterviewRatio;
    }
    public double getInterviewToOfferRatio() {
        return interviewToOfferRatio;
    }

}
