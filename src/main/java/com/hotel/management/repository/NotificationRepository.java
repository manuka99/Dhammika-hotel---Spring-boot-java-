package com.hotel.management.repository;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.NotificationDB;
import com.hotel.management.model.User;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationDB, String> {

	List<NotificationDB> findByUser(User user, Sort sort);
	NotificationDB findByUserAndNotificationID(User user, String notificationID);

}
