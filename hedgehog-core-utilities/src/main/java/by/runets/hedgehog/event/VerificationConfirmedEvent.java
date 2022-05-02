package by.runets.hedgehog.event;

import by.runets.hedgehog.domain.Subject;

import java.io.Serializable;
import java.time.Instant;

public class VerificationConfirmedEvent extends VerificationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    public VerificationConfirmedEvent() {
    }

    public VerificationConfirmedEvent(VerificationConfirmedEventBuilder verificationEventBuilder) {
        super(verificationEventBuilder.code, verificationEventBuilder.subject, verificationEventBuilder.occurredAt, EventType.CONFIRMED);
    }

    public static class VerificationConfirmedEventBuilder {

        private String code;
        private Subject subject;
        private Instant occurredAt;

        public VerificationConfirmedEventBuilder code(String code) {
            this.code = code;
            return this;
        }
        public VerificationConfirmedEventBuilder subject(Subject subject) {
            this.subject = subject;
            return this;
        }
        public VerificationConfirmedEventBuilder occurredAt(Instant occurredAt) {
            this.occurredAt = occurredAt;
            return this;
        }

        public VerificationConfirmedEvent build() {
            return new VerificationConfirmedEvent(this);
        }
    }
}
