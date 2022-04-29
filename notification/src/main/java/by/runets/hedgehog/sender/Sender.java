package by.runets.hedgehog.sender;

import by.runets.hedgehog.domain.Notification;

public interface Sender {
    void send(Notification notification);
}
