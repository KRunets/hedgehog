package by.runets.hedgehog.repository;

import by.runets.hedgehog.domain.Notification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, UUID> {

    @Modifying
    @Query("UPDATE notification n SET n.dispatched=true WHERE id=?1")
    void makeNotificationDispatched(UUID notificationId);
}
