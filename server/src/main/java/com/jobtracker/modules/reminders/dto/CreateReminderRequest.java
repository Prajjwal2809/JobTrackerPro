package com.jobtracker.modules.reminders.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class CreateReminderRequest {

    private UUID jobId;
    private LocalDate remindAt;

    public UUID getJobId() {
        return jobId;
    }

    public LocalDate getRemindAt() {
        return remindAt;
    }
}
