package com.jobtracker.modules.jobs.repository;

import com.jobtracker.modules.jobs.entity.Job;
import com.jobtracker.modules.jobs.entity.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    Optional<Job> findByIdAndUserId(UUID id, UUID userId);

    Page<Job> findByUserId(UUID userId, Pageable pageable);

    Page<Job> findByUserIdAndStatus(UUID userId, JobStatus status, Pageable pageable);

    Page<Job> findByUserIdAndCompanyContainingIgnoreCaseOrUserIdAndTitleContainingIgnoreCase(
            UUID userId1, String companyQ,
            UUID userId2, String titleQ,
            Pageable pageable
    );
}
