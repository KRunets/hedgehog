package by.runets.hedgehog.service;

import java.time.Instant;
import java.util.UUID;

public interface ScheduleExpirationService {
    void scheduleVerificationExpiration(UUID id, Instant instant);
}
