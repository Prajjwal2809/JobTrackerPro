package com.jobtracker.modules.reminders.service;

import java.time.LocalDate;

import com.jobtracker.modules.reminders.dto.CreateReminderRequest;
import com.jobtracker.modules.reminders.dto.ReminderResponse;
import com.jobtracker.modules.reminders.entity.Reminder;
import com.jobtracker.modules.reminders.repository.ReminderRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void create(UUID userId, UUID jobId, LocalDate remindAt) {
        Reminder reminder = new Reminder(
                userId,
                jobId,
                remindAt
               
        );

        reminderRepository.save(reminder);

    }

    public void upsert(UUID userId, UUID jobId, LocalDate remindAt)
    {
        reminderRepository.deleteByJobId(jobId);

        Reminder reminder=new Reminder(userId, 
            jobId, 
            remindAt
        );
        reminderRepository.save(reminder);

    }

    // public List<ReminderResponse> getMyReminders(UUID userId) {
    //     return reminderRepository.findByUserId(userId)
    //             .stream()
    //             .map(r -> new ReminderResponse(
    //                     r.getId(),
    //                     r.getJobId(),
    //                     r.getRemindAt(),
    //                     r.getStatus()
    //             ))
    //             .toList();
    // }
}
