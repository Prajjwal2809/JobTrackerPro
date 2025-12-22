package com.jobtracker.modules.notification.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobtracker.modules.notification.dto.NotificationResponse;
import com.jobtracker.modules.notification.dto.UnreadCountResponse;
import com.jobtracker.modules.notification.service.NotificationService;

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService)
    {
        this.notificationService=notificationService;
    }


    @GetMapping
    public List<NotificationResponse> list(@RequestParam(defaultValue="20") int limit)
    {
        return notificationService.listMine(limit);
    }
    
    @GetMapping("/unread-count")
    public UnreadCountResponse unreadCount() {
        return new UnreadCountResponse(notificationService.unreadCountMine());
    }

    @PatchMapping("/{id}/read")
    public void markRead(@PathVariable UUID id) {
        notificationService.markRead(id);
    }

    @PatchMapping("/read-all")
    public void markAllRead() {
        notificationService.markAllRead();
    }
}
