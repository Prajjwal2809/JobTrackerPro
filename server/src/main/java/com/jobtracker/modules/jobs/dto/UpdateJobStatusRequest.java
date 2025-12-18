package com.jobtracker.modules.jobs.dto;

import com.jobtracker.modules.jobs.entity.JobStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateJobStatusRequest {
    @NotNull
    private JobStatus status;

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }
}
