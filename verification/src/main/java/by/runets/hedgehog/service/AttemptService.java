package by.runets.hedgehog.service;


import by.runets.hedgehog.domain.UserInfo;

import java.util.UUID;

public interface AttemptService {
    boolean checkIsAttemptAllowed(UUID verificationId, UserInfo requestUserInfo);
}
