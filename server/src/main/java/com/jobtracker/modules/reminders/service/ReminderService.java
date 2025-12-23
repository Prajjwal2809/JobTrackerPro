package com.jobtracker.modules.reminders.service;

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

    public ReminderResponse create(UUID userId, CreateReminderRequest request) {
        Reminder reminder = new Reminder(
                userId,
                request.getJobId(),
                request.getRemindAt()
        );

        Reminder saved = reminderRepository.save(reminder);

        return new ReminderResponse(
                saved.getId(),
                saved.getJobId(),
                saved.getRemindAt(),
                saved.getStatus()
        );
    }

    public List<ReminderResponse> getMyReminders(UUID userId) {
        return reminderRepository.findByUserId(userId)
                .stream()
                .map(r -> new ReminderResponse(
                        r.getId(),
                        r.getJobId(),
                        r.getRemindAt(),
                        r.getStatus()
                ))
                .toList();
    }
}
