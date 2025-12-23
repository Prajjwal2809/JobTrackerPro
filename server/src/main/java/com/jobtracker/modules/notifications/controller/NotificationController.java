package com.jobtracker.modules.notifications.controller;

import com.jobtracker.modules.notifications.dto.MarkReadResponse;
import com.jobtracker.modules.notifications.dto.NotificationResponse;
import com.jobtracker.modules.notifications.entity.Notification;
import com.jobtracker.modules.notifications.service.NotificationService;
import com.jobtracker.security.CurrentUser;
import com.jobtracker.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> list(@CurrentUser UserPrincipal user, Pageable pageable) {
        return ResponseEntity.ok(notificationService.list(user.getId(), pageable));
    }

   
    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> unreadCount(@CurrentUser UserPrincipal user) {
        return ResponseEntity.ok(Map.of("unreadCount", notificationService.unreadCount(user.getId())));
    }


    @PatchMapping("/{id}/read")
    public ResponseEntity<MarkReadResponse> markRead(@CurrentUser UserPrincipal user, @PathVariable UUID id) {
        Notification n = notificationService.markRead(user.getId(), id);
        return ResponseEntity.ok(new MarkReadResponse(n.getId(), n.isRead(), n.getReadAt()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@CurrentUser UserPrincipal user, @PathVariable UUID id) {
        notificationService.delete(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
