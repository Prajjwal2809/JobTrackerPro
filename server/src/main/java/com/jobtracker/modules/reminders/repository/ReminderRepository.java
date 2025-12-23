package com.jobtracker.modules.reminders.repository;

import com.jobtracker.modules.reminders.entity.Reminder;
import com.jobtracker.modules.reminders.domain.ReminderStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByUserId(UUID userId);

    List<Reminder> findByStatusAndRemindAtBefore(
            ReminderStatus status,
            OffsetDateTime time
    );
}
