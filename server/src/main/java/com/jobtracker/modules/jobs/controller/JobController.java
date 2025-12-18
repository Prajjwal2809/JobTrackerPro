package com.jobtracker.modules.jobs.controller;

import com.jobtracker.common.exceptions.NotFoundException;
import com.jobtracker.modules.jobs.dto.CreateJobRequest;
import com.jobtracker.modules.jobs.dto.UpdateJobRequest;
import com.jobtracker.modules.jobs.dto.UpdateJobStatusRequest;
import com.jobtracker.modules.jobs.entity.Job;
import com.jobtracker.modules.jobs.entity.JobStatus;
import com.jobtracker.modules.jobs.service.JobService;
import com.jobtracker.modules.users.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;
    private final UserRepository userRepository;

    public JobController(JobService jobService, UserRepository userRepository) {
        this.jobService = jobService;
        this.userRepository = userRepository;
    }

    private UUID currentUserId(Principal principal) {
        String email = principal.getName();
        return userRepository.findByEmail(email)
                .map(u -> u.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PostMapping
    public Job create(@Valid @RequestBody CreateJobRequest req, Principal principal) {
        return jobService.create(currentUserId(principal), req);
    }

    @GetMapping
    public Page<Job> list(
            @RequestParam(required = false) JobStatus status,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal
    ) {
        return jobService.list(
                currentUserId(principal),
                status,
                q,
                PageRequest.of(page, size, Sort.by("appliedDate").descending())
        );
    }

    @GetMapping("/{id}")
    public Job get(@PathVariable UUID id, Principal principal) {
        return jobService.get(currentUserId(principal), id);
    }

    @PutMapping("/{id}")
    public Job update(@PathVariable UUID id, @Valid @RequestBody UpdateJobRequest req, Principal principal) {
        return jobService.update(currentUserId(principal), id, req);
    }

    @PatchMapping("/{id}/status")
    public Job updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateJobStatusRequest req, Principal principal) {
        return jobService.updateStatus(currentUserId(principal), id, req.getStatus());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, Principal principal) {
        jobService.delete(currentUserId(principal), id);
    }
}
