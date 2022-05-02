package by.runets.hedgehog.consumer;

import by.runets.hedgehog.event.VerificationEvent;

public interface EventConsumer {
    void consumeVerificationCreatedEvent(VerificationEvent verificationEvent);
}
