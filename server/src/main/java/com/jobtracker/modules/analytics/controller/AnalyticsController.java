package com.jobtracker.modules.analytics.controller;

import com.jobtracker.common.exceptions.UnauthorisedException;
import com.jobtracker.modules.analytics.dto.AnalyticsSummaryResponse;
import com.jobtracker.modules.analytics.dto.TimelinePoint;
import com.jobtracker.modules.analytics.service.AnalyticsService;
import com.jobtracker.modules.users.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final UserRepository userRepository;

    public AnalyticsController(AnalyticsService analyticsService, UserRepository userRepository) {
        this.analyticsService = analyticsService;
        this.userRepository = userRepository;
    }

    private UUID currentUserId(Principal principal) {
        String email = principal.getName();
        return userRepository.findByEmail(email)
                .map(u -> u.getId())
                .orElseThrow(() -> new UnauthorisedException("User not found"));
    }

    @GetMapping("/summary")
    public AnalyticsSummaryResponse summary(Principal principal) {
        UUID userId = currentUserId(principal);
        return analyticsService.summary(userId);
    }

    @GetMapping("/timeline")
    public List<TimelinePoint> timeline(Principal principal) {
        UUID userId = currentUserId(principal);
        return analyticsService.timeline(userId);
    }
}
