package com.jobtracker.modules.jobs.service;

import com.jobtracker.common.exceptions.NotFoundException;
import com.jobtracker.modules.jobs.dto.CreateJobRequest;
import com.jobtracker.modules.jobs.dto.UpdateJobRequest;
import com.jobtracker.modules.jobs.entity.Job;
import com.jobtracker.modules.jobs.entity.JobStatus;
import com.jobtracker.modules.jobs.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job create(UUID userId, CreateJobRequest req) {
        Job job = new Job();
        job.setUserId(userId);
        job.setCompany(req.getCompany());
        job.setTitle(req.getTitle());
        job.setStatus(req.getStatus());
        job.setLocationType(req.getLocationType());
        job.setLocation(req.getLocation());
        job.setAppliedDate(req.getAppliedDate());
        job.setSource(req.getSource());
        job.setNotes(req.getNotes());
        return jobRepository.save(job);
    }

    public Job get(UUID userId, UUID jobId) {
        return jobRepository.findByIdAndUserId(jobId, userId)
                .orElseThrow(() -> new NotFoundException("Job not found"));
        
    }

    public Page<Job> list(UUID userId, JobStatus status, String q, Pageable pageable) {
        if (q != null && !q.trim().isEmpty()) {
            String query = q.trim();
            return jobRepository.findByUserIdAndCompanyContainingIgnoreCaseOrUserIdAndTitleContainingIgnoreCase(
                    userId, query, userId, query, pageable
            );
        }
        if (status != null) {
            return jobRepository.findByUserIdAndStatus(userId, status, pageable);
        }
        return jobRepository.findByUserId(userId, pageable);
    }

    public Job update(UUID userId, UUID jobId, UpdateJobRequest req) {
        Job job = get(userId, jobId);
        job.setCompany(req.getCompany());
        job.setTitle(req.getTitle());
        job.setLocationType(req.getLocationType());
        job.setLocation(req.getLocation());
        job.setAppliedDate(req.getAppliedDate());
        job.setSource(req.getSource());
        job.setNotes(req.getNotes());
        return jobRepository.save(job);
    }

    public Job updateStatus(UUID userId, UUID jobId, JobStatus status) {
        Job job = get(userId, jobId);
        job.setStatus(status);
        return jobRepository.save(job);
    }

    public void delete(UUID userId, UUID jobId) {
        Job job = get(userId, jobId);
        jobRepository.delete(job);
    }
}
