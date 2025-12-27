package com.jobtracker.modules.reminders.dto;

import java.time.LocalDate;

import com.jobtracker.modules.reminders.domain.ReminderStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ReminderResponse {

    private UUID id;
    private UUID jobId;
    private LocalDate remindAt;
    private ReminderStatus status;

    public ReminderResponse(
            UUID id,
            UUID jobId,
            LocalDate remindAt,
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

    public LocalDate getRemindAt() {
        return remindAt;
    }

    public ReminderStatus getStatus() {
        return status;
    }
}
