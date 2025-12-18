CREATE TABLE jobs (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

  company VARCHAR(160) NOT NULL,
  title VARCHAR(160) NOT NULL,

  status VARCHAR(30) NOT NULL,
  location_type VARCHAR(30) NOT NULL,
  location VARCHAR(160),

  applied_date DATE NOT NULL,
  source VARCHAR(80),
  notes TEXT,

  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_jobs_user_id ON jobs(user_id);
CREATE INDEX idx_jobs_user_status ON jobs(user_id, status);
CREATE INDEX idx_jobs_user_company ON jobs(user_id, company);
CREATE INDEX idx_jobs_user_title ON jobs(user_id, title);