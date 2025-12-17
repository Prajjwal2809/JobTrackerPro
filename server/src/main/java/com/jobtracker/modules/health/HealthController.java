package com.jobtracker.modules.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {
    

    @GetMapping("/public")
    public String pub() {
        return "JobTrackerPro is up and running!";
    }

    @GetMapping("/secure")
    public String secure() {
        return "JobTrackerPro is secure and running!";
    }
}
