package by.runets.hedgehog.domain.notification;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public final class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final String recipient;
    private final String channel;
    private final String body;
    private final boolean dispatched;

    public Notification(NotificationBuilder builder) {
        this.id = builder.id;
        this.recipient = builder.recipient;
        this.channel = builder.channel;
        this.body = builder.body;
        this.dispatched = builder.dispatched;
    }

    public UUID getId() {
        return id;
    }
    public String getRecipient() {
        return recipient;
    }
    public String getChannel() {
        return channel;
    }
    public String getBody() {
        return body;
    }
    public boolean isDispatched() {
        return dispatched;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return dispatched == that.dispatched && Objects.equals(id, that.id) && Objects.equals(recipient, that.recipient) && Objects.equals(channel, that.channel) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipient, channel, body, dispatched);
    }

    public static class NotificationBuilder {

        private UUID id;
        private String recipient;
        private String channel;
        private String body;
        private boolean dispatched;

        public NotificationBuilder id(UUID id) {
            this.id = id;
            return this;
        }
        public NotificationBuilder recipient(String recipient) {
            this.recipient = recipient;
            return this;
        }
        public NotificationBuilder channel(String channel) {
            this.channel = channel;
            return this;
        }
        public NotificationBuilder body(String body) {
            this.body = body;
            return this;
        }
        public NotificationBuilder dispatched(boolean dispatched) {
            this.dispatched = dispatched;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
