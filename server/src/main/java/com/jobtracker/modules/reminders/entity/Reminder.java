package com.jobtracker.modules.reminders.entity;

import com.jobtracker.modules.reminders.domain.ReminderStatus;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID jobId;

    @Column(nullable = false)
    private OffsetDateTime remindAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReminderStatus status = ReminderStatus.PENDING;

    protected Reminder() {}

    public Reminder(UUID userId, UUID jobId, OffsetDateTime remindAt) {
        this.userId = userId;
        this.jobId = jobId;
        this.remindAt = remindAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
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

    public void markSent() {
        this.status = ReminderStatus.SENT;
    }

    public void dismiss() {
        this.status = ReminderStatus.DISMISSED;
    }
}
