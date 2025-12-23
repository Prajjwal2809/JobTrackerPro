package com.jobtracker.modules.reminders.controller;

import com.jobtracker.modules.reminders.dto.CreateReminderRequest;
import com.jobtracker.modules.reminders.dto.ReminderResponse;
import com.jobtracker.modules.reminders.service.ReminderService;
import com.jobtracker.security.CurrentUser;
import com.jobtracker.security.UserPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping
    public ReminderResponse create(
            @CurrentUser UserPrincipal user,
            @RequestBody CreateReminderRequest request
    ) {
        return reminderService.create(user.getId(), request);
    }

    @GetMapping
    public List<ReminderResponse> myReminders(
            @CurrentUser UserPrincipal user
    ) {
        return reminderService.getMyReminders(user.getId().toString());
    }
}
