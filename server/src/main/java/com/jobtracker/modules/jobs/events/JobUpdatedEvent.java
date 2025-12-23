package com.jobtracker.modules.jobs.events;

import java.util.UUID;

public record JobUpdatedEvent(UUID userId, UUID jobId, String company, String title) {}

