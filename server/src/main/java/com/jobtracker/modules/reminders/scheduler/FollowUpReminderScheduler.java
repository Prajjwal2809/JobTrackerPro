package com.jobtracker.modules.reminders.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jobtracker.modules.jobs.entity.Job;
import com.jobtracker.modules.jobs.repository.JobRepository;
import com.jobtracker.modules.reminders.entity.Reminder;
import com.jobtracker.modules.reminders.repository.ReminderRepository;
import com.jobtracker.modules.reminders.service.FollowUpMailService;
import com.jobtracker.modules.users.repository.UserRepository;

import jakarta.transaction.Transactional;

@EnableScheduling
@Component
public class FollowUpReminderScheduler {

  private final ReminderRepository reminderRepository;
  private final JobRepository jobRepository;
  private final UserRepository userRepository;
  private final FollowUpMailService mailService;

  public FollowUpReminderScheduler(
      ReminderRepository reminderRepository,
      JobRepository jobRepository,
      UserRepository userRepository,
      FollowUpMailService mailService
  ) {
    this.reminderRepository = reminderRepository;
    this.jobRepository = jobRepository;
    this.userRepository = userRepository;
    this.mailService = mailService;
  }

  // every 5 minutes (dev). For prod: once daily at 9am.
  @Scheduled(cron = "0 */5 * * * *")
  @Transactional
  public void sendDueFollowUps() {
    LocalDate today = LocalDate.now();

    List<Reminder> due = reminderRepository.findDue(today);

    for (Reminder r : due) {
      Job job = jobRepository.findByIdAndUserId(r.getJobId(), r.getUserId())
          .orElse(null);
      if (job == null) {
        reminderRepository.delete(r);
        continue;
      }

      var user = userRepository.findById(r.getUserId()).orElse(null);
      if (user == null || user.getEmail() == null) {
        // can mark FAILED if you want; for now delete to avoid infinite retries
        reminderRepository.delete(r);
        continue;
      }

      String subject = "Follow-up due: " + job.getCompany() + " - " + job.getTitle();
      String body = """
        Hi,

        Your follow-up is due today for:
        Company: %s
        Role: %s

        Open JobTrackerPro to update status / set next follow-up date.

        Thanks
        """.formatted(job.getCompany(), job.getTitle());

      mailService.send(user.getEmail(), subject, body);

      // cleanup: clear follow-up date and delete reminder so user can set next one
      job.setFollowUpAt(null);
      jobRepository.save(job);

      reminderRepository.delete(r);
    }
  }
}
