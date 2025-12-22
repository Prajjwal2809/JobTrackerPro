create table if not exists notifications (
  id uuid primary key,
  user_id uuid not null,
  type varchar(50) not null,
  title varchar(150) not null,
  message varchar(500) not null,
  link varchar(300),
  is_read boolean not null default false,
  created_at timestamptz not null default now(),
  read_at timestamptz
);

create index if not exists idx_notifications_user_created
  on notifications(user_id, created_at desc);

create index if not exists idx_notifications_user_unread
  on notifications(user_id, is_read);

create table if not exists reminders (
  id uuid primary key,
  user_id uuid not null,
  job_id uuid not null,
  due_at timestamptz not null,
  status varchar(20) not null, -- PENDING, SENT, CANCELLED
  sent_at timestamptz,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create unique index if not exists ux_reminders_user_job
  on reminders(user_id, job_id);

create index if not exists idx_reminders_due_status
  on reminders(status, due_at);