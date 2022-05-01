package by.runets.hedgehog.event;

import by.runets.hedgehog.domain.Subject;

import java.io.Serializable;
import java.time.Instant;

public class VerificationConfirmedFailedEvent extends VerificationEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    public VerificationConfirmedFailedEvent(VerificationConfirmedFailedEventBuilder verificationEventBuilder) {
        super(verificationEventBuilder.code, verificationEventBuilder.subject, verificationEventBuilder.occurredAt);
    }

    public static class VerificationConfirmedFailedEventBuilder {

        private String code;
        private Subject subject;
        private Instant occurredAt;

        public VerificationConfirmedFailedEventBuilder code(String code) {
            this.code = code;
            return this;
        }
        public VerificationConfirmedFailedEventBuilder subject(Subject subject) {
            this.subject = subject;
            return this;
        }
        public VerificationConfirmedFailedEventBuilder occurredAt(Instant occurredAt) {
            this.occurredAt = occurredAt;
            return this;
        }

        public VerificationConfirmedFailedEvent build() {
            return new VerificationConfirmedFailedEvent(this);
        }
    }
}
