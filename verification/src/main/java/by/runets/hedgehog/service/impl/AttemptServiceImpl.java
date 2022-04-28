package by.runets.hedgehog.service.impl;

import by.runets.hedgehog.domain.Attempt;
import by.runets.hedgehog.domain.UserInfo;
import by.runets.hedgehog.repository.AttemptRepository;
import by.runets.hedgehog.service.AttemptService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AttemptServiceImpl implements AttemptService {

    private static final Logger LOG = LogManager.getLogger(AttemptServiceImpl.class);

    private static final Integer VALID_ATTEMPTS_AMOUNT = 5;

    @Autowired
    private AttemptRepository attemptRepository;

    @Override
    @Transactional
    public boolean checkIsAttemptAllowed(UUID verificationId, UserInfo requestUserInfo) {
        Attempt attempt = attemptRepository.readAttemptByVerificationId(verificationId);
        if (attempt == null) {
            attempt = buildAttempt(verificationId, requestUserInfo);
            attemptRepository.save(attempt);
            return true;
        } else {
            final AtomicInteger times = new AtomicInteger(attempt.getTimes());
            if (times.get() < VALID_ATTEMPTS_AMOUNT) {
                attemptRepository.writeAttempt(verificationId, times.incrementAndGet());
                return true;
            }
        }
        LOG.warn("The attempts stack for verification={} is overflowed.", verificationId);
        return false;
    }

    public void setAttemptRepository(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    private Attempt buildAttempt(UUID verificationId, UserInfo requestUserInfo) {
        return new Attempt.AttemptBuilder()
                .verificationId(verificationId)
                .userInfo(requestUserInfo)
                .times(1)
                .build();
    }
}
