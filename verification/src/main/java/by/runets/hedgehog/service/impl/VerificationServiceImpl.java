package by.runets.hedgehog.service.impl;

import by.runets.hedgehog.domain.Subject;
import by.runets.hedgehog.domain.UserInfo;
import by.runets.hedgehog.domain.Verification;
import by.runets.hedgehog.exception.ResourceDuplicationException;
import by.runets.hedgehog.repository.VerificationRepository;
import by.runets.hedgehog.resource.dto.UserInfoDto;
import by.runets.hedgehog.resource.dto.VerificationRequestDto;
import by.runets.hedgehog.service.ScheduleExpirationService;
import by.runets.hedgehog.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private VerificationRepository verificationRepository;
    @Autowired
    private ScheduleExpirationService expirationService;

    @Override
    @Transactional
    public UUID createVerification(VerificationRequestDto verificationDto, UserInfoDto userInfoDto) {
        final Verification verification = mapDto(verificationDto, userInfoDto);

        if (verificationRepository.readPendingVerificationByIdentity(verificationDto.getIdentity()) != null) {
            throw new ResourceDuplicationException("The duplication of verification by identity " + verificationDto.getIdentity());
        }

        final UUID id = verificationRepository.save(verification).getId();
        expirationService.scheduleVerificationExpiration(id, Instant.now());

        return id;
    }

    @Override
    public UUID confirmVerification() {
        return null;
    }

    private Verification mapDto(VerificationRequestDto verificationDto, UserInfoDto userInfoDto) {
        final Subject subject = new Subject.SubjectBuilder()
                .type(verificationDto.getType())
                .identity(verificationDto.getIdentity())
                .build();

        final UserInfo userInfo = new UserInfo.UserInfoBuilder()
                .userAgent(userInfoDto.getUserAgent())
                .ipAddress(userInfoDto.getClientIp().getBytes(StandardCharsets.UTF_8))
                .build();

        return new Verification.VerificationBuilder()
                .subject(subject)
                .confirmed(Boolean.FALSE)
                .userInfo(userInfo)
                .build();
    }

    public void setExpirationService(ScheduleExpirationService expirationService) {
        this.expirationService = expirationService;
    }

    public void setVerificationRepository(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }
}
