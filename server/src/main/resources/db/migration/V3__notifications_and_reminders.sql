-- V3__notifications_and_reminders.sql

-- UUID generator (pgcrypto)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================
-- notifications
-- =========================
CREATE TABLE IF NOT EXISTS notifications (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID NOT NULL,
    type       VARCHAR(40) NOT NULL,
    title      VARCHAR(160) NOT NULL,
    message    VARCHAR(400) NOT NULL,
    job_id     UUID NULL,
    is_read    BOOLEAN NOT NULL DEFAULT FALSE,
    read_at    TIMESTAMPTZ NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_notifications_user_created
ON notifications (user_id, created_at DESC);

CREATE INDEX IF NOT EXISTS idx_notifications_user_read
ON notifications (user_id, is_read);

CREATE INDEX IF NOT EXISTS idx_notifications_job
ON notifications (job_id);

-- =========================
-- reminders
-- =========================
CREATE TABLE IF NOT EXISTS reminders (
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id   UUID NOT NULL,
    job_id    UUID NOT NULL,
    remind_at DATE NOT NULL,
    status    VARCHAR(20) NOT NULL DEFAULT 'PENDING'
);

CREATE INDEX IF NOT EXISTS idx_reminders_user
ON reminders (user_id);

CREATE INDEX IF NOT EXISTS idx_reminders_due
ON reminders (status, remind_at);

CREATE INDEX IF NOT EXISTS idx_reminders_job
ON reminders (job_id);
