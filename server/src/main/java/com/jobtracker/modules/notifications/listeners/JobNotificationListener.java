package com.jobtracker.modules.notifications.listeners;

import org.springframework.context.event.EventListener;
import com.jobtracker.modules.jobs.events.JobCreatedEvent;
import com.jobtracker.modules.jobs.events.JobUpdatedEvent;
import com.jobtracker.modules.jobs.events.JobStatusChangedEvent;
import com.jobtracker.modules.notifications.domain.NotificationType;
import com.jobtracker.modules.notifications.service.NotificationService;

import org.springframework.stereotype.Component;

import com.jobtracker.modules.jobs.events.JobDeletedEvent;

@Component
public class JobNotificationListener {

    private final NotificationService notificationService;

    public JobNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void onJobCreated(JobCreatedEvent e) {

        String title = "Job added";
        String body = e.company() + " — " + e.title();
        notificationService.create(e.userId(), NotificationType.JOB_CREATED, title, body, e.jobId());
    }

    @EventListener
    public void onJobUpdated(JobUpdatedEvent e) {
        String title = "Job updated";
        String body = e.company() + " — " + e.title();
        notificationService.create(e.userId(), NotificationType.JOB_UPDATED, title, body, e.jobId());
    }

    @EventListener
    public void onJobStatusChanged(JobStatusChangedEvent e) {
        String title = "Job status changed";
        String body = e.company() + " — " + e.title() + " (" + e.status() + ")";
        notificationService.create(e.userId(), NotificationType.JOB_STATUS_CHANGED, title, body, e.jobId());
    }

    @EventListener
    public void onJobDeleted(JobDeletedEvent e) {
        String title = "Job status changed";
        String body = e.company() + " — " + e.title() ;
        notificationService.create(e.userId(), NotificationType.JOB_STATUS_CHANGED, title, body, e.jobId());
    }
}
