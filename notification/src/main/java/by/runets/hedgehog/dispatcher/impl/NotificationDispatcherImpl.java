package by.runets.hedgehog.dispatcher.impl;

import by.runets.hedgehog.dispatcher.NotificationDispatcher;
import by.runets.hedgehog.domain.Notification;
import by.runets.hedgehog.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationDispatcherImpl implements NotificationDispatcher {

    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public void dispatchNotification(Notification notification) {

        notificationService.makeNotificationDispatched(notification.getId());
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
