package by.runets.hedgehog.service;

import java.util.UUID;

public interface ExpirationService {
    Integer expireVerification(UUID id);
}
