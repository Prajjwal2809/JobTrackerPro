package com.jobtracker.modules.notification.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtracker.modules.notification.entity.Notification;

import org.springframework.data.domain.Pageable;;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findUserByIdOderByCreatedAtDesc(UUID userId, Pageable pageable);
    long countByUserIdAndReadFalse(UUID userId);
}
