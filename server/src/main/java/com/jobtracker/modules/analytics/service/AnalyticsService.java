package com.jobtracker.modules.analytics.service;

import com.jobtracker.modules.analytics.dto.AnalyticsSummaryResponse;
import com.jobtracker.modules.analytics.dto.TimelinePoint;
import com.jobtracker.modules.jobs.entity.JobStatus;
import com.jobtracker.modules.jobs.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {

    private final JobRepository jobRepository;

    public AnalyticsService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public AnalyticsSummaryResponse summary(UUID userId) {
        long total = jobRepository.countByUserId(userId);

        Map<String, Long> byStatus = new LinkedHashMap<>();
        for (JobStatus s : JobStatus.values()) {
            byStatus.put(s.name(), jobRepository.countByUserIdAndStatus(userId, s));
        }

        long applied = byStatus.getOrDefault("APPLIED", 0L);
        long interview = byStatus.getOrDefault("INTERVIEW", 0L);
        long offer = byStatus.getOrDefault("OFFER", 0L);

        double appliedToInterview = applied == 0 ? 0.0 : (double) interview / applied;
        double interviewToOffer = interview == 0 ? 0.0 : (double) offer / interview;

        return new AnalyticsSummaryResponse(total, byStatus, appliedToInterview, interviewToOffer);
    }

    public List<TimelinePoint> timeline(UUID userId) {
        List<Object[]> rows = jobRepository.countByUserIdGroupedByIsoWeek(userId);
        List<TimelinePoint> result = new ArrayList<>();
        for (Object[] r : rows) {
            String period = (String) r[0];
            long count = ((Number) r[1]).longValue();
            result.add(new TimelinePoint(period, count));
        }
        return result;
    }
}
