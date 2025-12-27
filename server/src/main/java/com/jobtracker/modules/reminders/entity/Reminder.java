package com.jobtracker.modules.reminders.entity;

import java.time.LocalDate;

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

    @Column(name = "remind_at", nullable = false)
    private LocalDate remindAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReminderStatus status = ReminderStatus.PENDING;
    

    @Column(name="reminder_key")

    private String reminderKey;

    protected Reminder() {}

    public Reminder(UUID userId, UUID jobId, LocalDate remindAt) {
        this.userId = userId;
        this.jobId = jobId;
        this.remindAt = remindAt;
        this.reminderKey= userId.toString().concat(" : ".concat(
            jobId.toString().concat(" : ".concat(
                remindAt.toString()
            ))
        ));
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

    public LocalDate getRemindAt() {
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
