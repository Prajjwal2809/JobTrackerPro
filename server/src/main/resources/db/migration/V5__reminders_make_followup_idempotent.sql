
-- Prevent duplicates for the same follow-up time per job/user
ALTER TABLE reminders
ADD COLUMN IF NOT EXISTS reminder_key VARCHAR(120);

-- Backfill if needed (safe if table already empty)
UPDATE reminders
SET reminder_key = CONCAT(user_id::text, ':', job_id::text, ':', remind_at::text)
WHERE reminder_key IS NULL;

ALTER TABLE reminders
ALTER COLUMN reminder_key SET NOT NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_reminders_reminder_key
ON reminders (reminder_key);

CREATE INDEX IF NOT EXISTS idx_reminders_user_status
ON reminders (user_id, status);
