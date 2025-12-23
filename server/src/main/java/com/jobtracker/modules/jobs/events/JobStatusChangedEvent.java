package com.jobtracker.modules.jobs.events;

import com.jobtracker.modules.jobs.entity.JobStatus;
import java.util.UUID;

public record JobStatusChangedEvent(UUID userId, UUID jobId, String company, String title, JobStatus status) {}
