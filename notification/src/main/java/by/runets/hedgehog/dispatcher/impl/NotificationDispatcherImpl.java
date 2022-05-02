package by.runets.hedgehog.dispatcher.impl;

import by.runets.hedgehog.dispatcher.NotificationDispatcher;
import by.runets.hedgehog.domain.Notification;
import by.runets.hedgehog.sender.Sender;
import by.runets.hedgehog.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static by.runets.hedgehog.utils.Constants.*;

@Service
public class NotificationDispatcherImpl implements NotificationDispatcher {

    @Autowired
    private NotificationService notificationService;
    private Map<String, Sender> senders;

    @Override
    @Transactional
    public void dispatchNotification(Notification notification) {
        final Sender sender = getSender(notification.getChannel());
        if (sender != null && notificationService.readNotificationById(notification.getId()).isEmpty()) {
            sender.send(notification);
            notificationService.save(notification);
            notificationService.makeNotificationDispatched(notification.getId());
        }
    }

    private Sender getSender(String channel) {
        Sender sender = null;
        switch (channel) {
            case MOBILE_VERIFICATION:
                sender = senders.get(MOBILE_SENDER);
                break;
            case EMAIL_VERIFICATION:
                sender = senders.get(EMAIL_SENDER);
                break;
            default:
                break;
        }
        return sender;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    public void setSenders(Map<String, Sender> senders) {
        this.senders = senders;
    }
}
