package com.jobtracker.modules.jobs.events;

import java.util.UUID;

public record JobDeletedEvent(UUID userId, UUID jobId, String company, String title) {}

