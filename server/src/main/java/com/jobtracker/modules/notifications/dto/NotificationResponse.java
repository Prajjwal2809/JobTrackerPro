package com.jobtracker.modules.notifications.dto;

import com.jobtracker.modules.notifications.domain.NotificationStatus;
import com.jobtracker.modules.notifications.domain.NotificationType;
import java.time.OffsetDateTime;

public class NotificationResponse {

    private Long id;
    private String title;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private OffsetDateTime createdAt;

    public NotificationResponse(
            Long id,
            String title,
            String message,
            NotificationType type,
            NotificationStatus status,
            OffsetDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
