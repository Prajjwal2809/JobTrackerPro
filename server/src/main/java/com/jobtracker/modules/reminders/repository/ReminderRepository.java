package com.jobtracker.modules.reminders.repository;

import com.jobtracker.modules.reminders.entity.Reminder;
import com.jobtracker.modules.reminders.domain.ReminderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface ReminderRepository extends JpaRepository<Reminder, UUID> {

    List<Reminder> findByUserId(UUID userId);

    List<Reminder> findByStatusAndRemindAtBefore(
            ReminderStatus status,
            OffsetDateTime time
    );

    void deleteByJobId(UUID jobId);

    @Query("""
    select r from Reminder r
    where r.status = 'PENDING'
      and r.remindAt <= :today
  """)
  List<Reminder> findDue(LocalDate today);



}
