package com.jobtracker.modules.notification.service;

import java.awt.print.Pageable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jobtracker.modules.notification.dto.NotificationResponse;
import com.jobtracker.modules.notification.entity.Notification;
import com.jobtracker.modules.notification.repository.NotificationRepository;
import com.jobtracker.security.CurrentUser;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {

    public final NotificationRepository notificationRepository;
    public final  UserLookupService userLookupService;

    public NotificationService(NotificationRepository notificationRepository, 
        UserLookupService userLookupService)
    {
        this.notificationRepository=notificationRepository;
        this.userLookupService=userLookupService;

    }

    
    private UUID currentUserId() {
        UUID id = CurrentUser.tryUserId();
        if (id != null) return id;
        return userLookupService.requireUserIdByEmail(CurrentUser.username());
    }

    @Transactional
    public void createJobCreated(UUID userId, String company, String title, UUID jobId) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType("JOB_CREATED");
        notification.setTitle("Job added");
        notification.setMessage(company + " — " + title);
        notification.setLink("/board"); 
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    @Transactional
    public void createJobUpdated(UUID userId, String company, String title, UUID jobId) {

        Notification notification=new Notification();
        notification.setUserId(userId);
        notification.setType("JOB_UPDATED");
        notification.setTitle("Job updated");
        notification.setMessage(company + " — " + title);
        notification.setLink("/board");
        notification.setRead(false);
        notificationRepository.save(notification);
    }


    public List<NotificationResponse> listMine(int limit){
        UUID userId=currentUserId();
        int safe=Math.max(1, Math.min(limit, 20));
        return notificationRepository.findUserByIdOderByCreatedAtDesc(userId,PageRequest.of(0, safe))
                .stream()
                .map(notification -> new NotificationResponse(
                        notification.getId(),
                        notification.getType(),
                        notification.getTitle(),
                        notification.getMessage(),
                        notification.getLink(),
                        notification.isRead(),
                        notification.getCreatedAt()
                ))
                .toList();
    } 

    public long unreadCountMine(){
        return notificationRepository.countByUserIdAndReadFalse(currentUserId());
    }

    @Transactional
    public void markRead(UUID notificationId){
        UUID userId=currentUserId();

        Notification notification=notificationRepository.findById(userId).orElseThrow();
        
        if (!notification.getUserId().equals(userId)) throw new RuntimeException("Forbidden");
        if (!notification.isRead()) {
            notification.setRead(true);
            notification.setReadAt(OffsetDateTime.now());
            notificationRepository.save(notification);
        }

    }

    @Transactional
    public void markAllRead() {
        UUID userId = currentUserId();
        var list = notificationRepository.findUserByIdOderByCreatedAtDesc(userId, PageRequest.of(0, 200));
        OffsetDateTime now = OffsetDateTime.now();
        for (Notification notification : list) {
            if (!notification.isRead()) {
                notification.setRead(true);
                notification.setReadAt(now);
            }
        }
        notificationRepository.saveAll(list);
    }

}
