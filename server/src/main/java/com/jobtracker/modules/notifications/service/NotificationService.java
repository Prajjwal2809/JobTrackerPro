package com.jobtracker.modules.notifications.service;

import com.jobtracker.common.exceptions.NotFoundException;
import com.jobtracker.modules.notifications.domain.NotificationType;
import com.jobtracker.modules.notifications.dto.NotificationResponse;
import com.jobtracker.modules.notifications.entity.Notification;
import com.jobtracker.modules.notifications.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Internal API used by Job module (2.4)
    public Notification create(UUID userId, NotificationType type, String title, String message, UUID jobId) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setType(type);
        n.setTitle(title);
        n.setMessage(message);
        n.setJobId(jobId);
        return notificationRepository.save(n);
    }

    public Page<NotificationResponse> list(UUID userId, Pageable pageable) {
        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::toResponse);
    }

    public long unreadCount(UUID userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    public Notification markRead(UUID userId, UUID notificationId) {
        Notification n = notificationRepository.findByIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));

        if (!n.isRead()) {
            n.setRead(true);
            n.setReadAt(Instant.now());
            n = notificationRepository.save(n);
        }
        return n;
    }

    public void delete(UUID userId, UUID notificationId) {
        Notification n = notificationRepository.findByIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));
        notificationRepository.delete(n);
    }

    private NotificationResponse toResponse(Notification n) {
        NotificationResponse r = new NotificationResponse();
        r.setId(n.getId());
        r.setType(n.getType());
        r.setTitle(n.getTitle());     // ✅ title included
        r.setMessage(n.getMessage());
        r.setJobId(n.getJobId());
        r.setRead(n.isRead());
        r.setReadAt(n.getReadAt());
        r.setCreatedAt(n.getCreatedAt());
        return r;
    }
}
