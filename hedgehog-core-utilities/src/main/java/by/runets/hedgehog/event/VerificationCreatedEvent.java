package by.runets.hedgehog.event;

import by.runets.hedgehog.domain.Subject;

import java.io.Serializable;
import java.time.Instant;

public class VerificationCreatedEvent extends VerificationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    public VerificationCreatedEvent(VerificationCreatedEventBuilder verificationEventBuilder) {
        super(verificationEventBuilder.code, verificationEventBuilder.subject, verificationEventBuilder.occurredAt);
    }

    public static class VerificationCreatedEventBuilder {

        private String code;
        private Subject subject;
        private Instant occurredAt;

        public VerificationCreatedEventBuilder code(String code) {
            this.code = code;
            return this;
        }
        public VerificationCreatedEventBuilder subject(Subject subject) {
            this.subject = subject;
            return this;
        }
        public VerificationCreatedEventBuilder occurredAt(Instant occurredAt) {
            this.occurredAt = occurredAt;
            return this;
        }

        public VerificationCreatedEvent build() {
            return new VerificationCreatedEvent(this);
        }
    }
}
