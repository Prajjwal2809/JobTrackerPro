package com.jobtracker.modules.reminders.service;

import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class FollowUpMailService {

    private final JavaMailSender mailSender;
    

  public FollowUpMailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send(String to, String subject, String body) {
    SimpleMailMessage msg = new SimpleMailMessage();

    msg.setTo(to);
    msg.setSubject(subject);
    msg.setText(body);
    mailSender.send(msg);
  }
}
