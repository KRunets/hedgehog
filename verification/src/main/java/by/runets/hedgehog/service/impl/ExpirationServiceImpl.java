package by.runets.hedgehog.service.impl;

import by.runets.hedgehog.repository.VerificationRepository;
import by.runets.hedgehog.service.ExpirationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ExpirationServiceImpl implements ExpirationService {

    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    @Transactional
    public Integer expireVerification(UUID id) {
        return verificationRepository.expireVerification(id);
    }

    public void setVerificationRepository(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

}
