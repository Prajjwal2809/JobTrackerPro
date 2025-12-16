# JobTracker Pro

Enterprise-style job application tracking system built to demonstrate production backend + frontend practices.

## Features (in progress)
- JWT authentication with refresh tokens
- Track job applications with stages (Kanban)
- Notes, reminders, activity timeline
- Analytics dashboard (stage counts, applied per week)

## Tech Stack
**Backend**
- Java 17, Spring Boot
- Spring Security (JWT)
- PostgreSQL
- Flyway migrations
- Swagger/OpenAPI

**Infrastructure**
- Docker + Docker Compose
- GitHub Actions (planned)

## Repository Structure
- `server/` — Spring Boot backend API
- `client/` — React + TypeScript frontend (planned)
- `infra/` — Docker (Postgres), tooling

## Local Setup

### 1) Start Postgres
```bash
docker compose -f infra/docker/docker-compose.yml up -d
