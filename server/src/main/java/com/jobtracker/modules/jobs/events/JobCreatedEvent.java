package com.jobtracker.modules.jobs.events;

import java.util.UUID;

public record JobCreatedEvent(UUID userId, UUID jobId, String company, String title) {}
