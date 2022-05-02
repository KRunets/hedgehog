package by.runets.hedgehog.service;

import by.runets.hedgehog.domain.Notification;

import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    void save(Notification notification);

    void makeNotificationDispatched(UUID id);

    Optional<Notification> readNotificationById(UUID id);
}
