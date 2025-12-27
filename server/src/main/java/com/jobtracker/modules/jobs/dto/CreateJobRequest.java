package com.jobtracker.modules.jobs.dto;

import com.jobtracker.modules.jobs.entity.JobStatus;
import com.jobtracker.modules.jobs.entity.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateJobRequest {

    @NotBlank
    private String company;

    @NotBlank
    private String title;

    @NotNull
    private JobStatus status;

    @NotNull
    private LocationType locationType;

    private String location;

    @NotNull
    private LocalDate appliedDate;

    private String source;

    private LocalDate followupAt;

    @Size(max = 2000)
    private String notes;

    // getters/setters
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public LocationType getLocationType() { return locationType; }
    public void setLocationType(LocationType locationType) { this.locationType = locationType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getFollowUpAt() { return followupAt; }
    public void setFollowUpAt(LocalDate followUpAt) { this.followupAt=followUpAt; }

}
