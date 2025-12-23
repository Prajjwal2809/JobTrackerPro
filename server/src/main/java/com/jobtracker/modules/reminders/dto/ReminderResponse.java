package com.jobtracker.modules.reminders.dto;

import com.jobtracker.modules.reminders.domain.ReminderStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ReminderResponse {

    private UUID id;
    private UUID jobId;
    private OffsetDateTime remindAt;
    private ReminderStatus status;

    public ReminderResponse(
            UUID id,
            UUID jobId,
            OffsetDateTime remindAt,
            ReminderStatus status
    ) {
        this.id = id;
        this.jobId = jobId;
        this.remindAt = remindAt;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getJobId() {
        return jobId;
    }

    public OffsetDateTime getRemindAt() {
        return remindAt;
    }

    public ReminderStatus getStatus() {
        return status;
    }
}
