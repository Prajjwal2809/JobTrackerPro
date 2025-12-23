package com.jobtracker.modules.reminders.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CreateReminderRequest {

    private UUID jobId;
    private OffsetDateTime remindAt;

    public UUID getJobId() {
        return jobId;
    }

    public OffsetDateTime getRemindAt() {
        return remindAt;
    }
}
