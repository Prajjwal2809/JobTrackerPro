package com.jobtracker.modules.jobs.service;

import com.jobtracker.common.exceptions.NotFoundException;
import com.jobtracker.modules.jobs.dto.CreateJobRequest;
import com.jobtracker.modules.jobs.dto.UpdateJobRequest;
import com.jobtracker.modules.jobs.entity.Job;
import com.jobtracker.modules.jobs.entity.JobStatus;
import com.jobtracker.modules.jobs.events.JobCreatedEvent;
import com.jobtracker.modules.jobs.events.JobStatusChangedEvent;
import com.jobtracker.modules.jobs.events.JobUpdatedEvent;
import com.jobtracker.modules.jobs.repository.JobRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;

import com.jobtracker.modules.jobs.events.JobDeletedEvent;
import com.jobtracker.modules.reminders.dto.CreateReminderRequest;
import com.jobtracker.modules.reminders.service.ReminderService;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ApplicationEventPublisher publisher;
    private final ReminderService reminderService;

    public JobService(JobRepository jobRepository, ApplicationEventPublisher publisher, ReminderService reminderService) {
        this.jobRepository = jobRepository;
        this.publisher=publisher;
        this.reminderService=reminderService;
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
        job.setFollowUpAt(req.getFollowUpAt());
        Job saved= jobRepository.save(job);

        publisher.publishEvent(new JobCreatedEvent(userId, saved.getId(), saved.getCompany(), saved.getTitle()));


        if(req.getFollowUpAt()!=null)
        {
            reminderService.create(userId,
                saved.getId(),
                req.getFollowUpAt()
            );
        }
        
        return saved;
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
        job.setFollowUpAt(req.getFollowUpAt());
        
        Job saved = jobRepository.save(job);

        publisher.publishEvent(new JobUpdatedEvent(userId, saved.getId(), saved.getCompany(), saved.getTitle()));
       
         if(req.getFollowUpAt()!=null)
        {
            reminderService.upsert(userId,
                saved.getId(),
                req.getFollowUpAt()
            );
        }
       
        return saved;
    }

    public Job updateStatus(UUID userId, UUID jobId, JobStatus status) {
        Job job = get(userId, jobId);
        job.setStatus(status);

        Job saved = jobRepository.save(job);

        publisher.publishEvent(new JobStatusChangedEvent(userId, saved.getId(), saved.getCompany(), saved.getTitle(), saved.getStatus()));
        return saved;
    }

    public void delete(UUID userId, UUID jobId) {
        Job job = get(userId, jobId);
        jobRepository.delete(job);
        publisher.publishEvent(new JobDeletedEvent(userId, job.getId(), job.getCompany(), job.getTitle()));
        
    }
}
