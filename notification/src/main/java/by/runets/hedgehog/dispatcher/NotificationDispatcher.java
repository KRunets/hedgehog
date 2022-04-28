package by.runets.hedgehog.dispatcher;

import by.runets.hedgehog.domain.Notification;

public interface NotificationDispatcher {
    void dispatchNotification(Notification notification);
}
