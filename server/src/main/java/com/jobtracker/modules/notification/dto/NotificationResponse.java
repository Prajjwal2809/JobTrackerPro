package com.jobtracker.modules.notification.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        String type,
        String title,
        String message,
        String link,
        boolean read,
        OffsetDateTime createdAt
) {}
