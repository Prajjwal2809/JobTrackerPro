ALTER TABLE jobs
ADD COLUMN IF NOT EXISTS follow_up_at DATE NULL;

CREATE INDEX IF NOT EXISTS idx_jobs_user_followup
ON jobs (user_id, follow_up_at);
