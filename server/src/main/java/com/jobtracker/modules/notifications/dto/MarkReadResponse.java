package com.jobtracker.modules.notifications.dto;

import java.time.Instant;
import java.util.UUID;

public class MarkReadResponse {
    private UUID id;
    private boolean read;
    private Instant readAt;

    public MarkReadResponse() {}

    public MarkReadResponse(UUID id, boolean read, Instant readAt) {
        this.id = id;
        this.read = read;
        this.readAt = readAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public Instant getReadAt() { return readAt; }
    public void setReadAt(Instant readAt) { this.readAt = readAt; }
}
