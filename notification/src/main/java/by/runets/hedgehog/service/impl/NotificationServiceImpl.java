package by.runets.hedgehog.service.impl;

import by.runets.hedgehog.domain.Notification;
import by.runets.hedgehog.repository.NotificationRepository;
import by.runets.hedgehog.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public void makeNotificationDispatched(UUID id) {
        notificationRepository.makeNotificationDispatched(id);
    }

    public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
}
