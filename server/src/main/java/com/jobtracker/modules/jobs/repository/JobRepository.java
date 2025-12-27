package com.jobtracker.modules.jobs.repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.jobtracker.modules.jobs.entity.Job;
import com.jobtracker.modules.jobs.entity.JobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {


    long countByUserId(UUID userId);
    long countByUserIdAndStatus(UUID userId, JobStatus status);

    @Query("""
        SELECT FUNCTION('to_char', j.appliedDate, 'IYYY-"W"IW') as period, COUNT(j)
        FROM Job j
        WHERE j.userId = :userId AND j.appliedDate IS NOT NULL
        GROUP BY FUNCTION('to_char', j.appliedDate, 'IYYY-"W"IW')
        ORDER BY FUNCTION('to_char', j.appliedDate, 'IYYY-"W"IW')
    """)
    List<Object[]> countByUserIdGroupedByIsoWeek(@Param("userId") UUID userId);

    @Query("""
            SELECT j FROM Job j
            WHERE j.followUpAt IS NOT NULL
            AND j.followUpAt <= : now
            """)
    List<Job> findJobsDueForFollowUp(@Param("now") LocalDate now);

    Optional<Job> findByIdAndUserId(UUID id, UUID userId);

    Page<Job> findByUserId(UUID userId, Pageable pageable);

    Page<Job> findByUserIdAndStatus(UUID userId, JobStatus status, Pageable pageable);

    Page<Job> findByUserIdAndCompanyContainingIgnoreCaseOrUserIdAndTitleContainingIgnoreCase(
            UUID userId1, String companyQ,
            UUID userId2, String titleQ,
            Pageable pageable
    );
}
