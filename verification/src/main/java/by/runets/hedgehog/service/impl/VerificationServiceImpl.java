package by.runets.hedgehog.service.impl;

import by.runets.hedgehog.domain.Subject;
import by.runets.hedgehog.domain.UserInfo;
import by.runets.hedgehog.domain.Verification;
import by.runets.hedgehog.event.VerificationConfirmedEvent;
import by.runets.hedgehog.event.VerificationConfirmedFailedEvent;
import by.runets.hedgehog.event.VerificationCreatedEvent;
import by.runets.hedgehog.event.VerificationEvent;
import by.runets.hedgehog.exception.NoPermissionException;
import by.runets.hedgehog.exception.ResourceDuplicationException;
import by.runets.hedgehog.exception.ResourceExpiredException;
import by.runets.hedgehog.exception.ResourceNotFoundException;
import by.runets.hedgehog.producer.EventProducer;
import by.runets.hedgehog.repository.VerificationRepository;
import by.runets.hedgehog.resource.dto.UserInfoDto;
import by.runets.hedgehog.resource.dto.VerificationRequestDto;
import by.runets.hedgehog.service.AttemptService;
import by.runets.hedgehog.service.ScheduleExpirationService;
import by.runets.hedgehog.service.VerificationService;
import by.runets.hedgehog.utils.NumericGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Value("#{new Long('${code.verification.length:8}')}")
    private Long codeVerificationLength;
    @Autowired
    private VerificationRepository verificationRepository;
    @Autowired
    private ScheduleExpirationService expirationService;
    @Autowired
    private AttemptService attemptService;
    @Autowired
    private EventProducer<VerificationEvent> verificationEventProducer;

    @Override
    @Transactional
    public UUID createVerification(VerificationRequestDto verificationDto, UserInfoDto userInfoDto) {
        final Verification verification = mapDto(verificationDto, userInfoDto);

        if (verificationRepository.readPendingVerificationByIdentity(verificationDto.getIdentity()) != null) {
            throw new ResourceDuplicationException("The duplication of verification by identity " + verificationDto.getIdentity());
        }

        final UUID id = verificationRepository.save(verification).getId();
        expirationService.scheduleVerificationExpiration(id, Instant.now());
        verificationEventProducer.fireEvent(buildVerificationCreatedEvent(verification));

        return id;
    }

    @Override
    @Transactional
    public boolean confirmVerification(UUID verificationId, String code, UserInfoDto userInfoDto) {
        final Optional<Verification> optionalVerification = verificationRepository.findById(verificationId);

        if (optionalVerification.isEmpty()) {
            throw new ResourceNotFoundException("Verification by id=" + verificationId + " does not exists.");
        }

        final Verification verification = optionalVerification.get();

        final UserInfo verificationUserInfo = verification.getUserInfo();
        final UserInfo requestUserInfo = buildUserInfo(userInfoDto);

        if (!verificationUserInfo.equals(requestUserInfo)) {
            throw new NoPermissionException("The user is not permitted for verification.");
        }

        if (verification.isExpired()) {
            throw new ResourceExpiredException("Verification by id=" + verificationId + " is expired.");
        }

        final boolean isCodeValid = verification.getCode().equalsIgnoreCase(code);
        if (isCodeValid) {
            verificationEventProducer.fireEvent(buildVerificationConfirmedEvent(verification));
            return verificationRepository.confirmVerification(verificationId) != 0;
        } else {
            if (!attemptService.checkIsAttemptAllowed(verificationId, requestUserInfo)) {
                verificationRepository.expireVerification(verificationId);
                throw new ResourceExpiredException("The resource is expired due to invalid attempts for confirmation");
            }
            verificationEventProducer.fireEvent(buildVerificationConfirmedFailedEvent(verification));
        }

        return false;
    }

    private Verification mapDto(VerificationRequestDto verificationDto, UserInfoDto userInfoDto) {
        final Subject subject = buildSubject(verificationDto);

        final UserInfo userInfo = buildUserInfo(userInfoDto);

        return new Verification.VerificationBuilder()
                .code(generateCode())
                .subject(subject)
                .userInfo(userInfo)
                .build();
    }
    private Subject buildSubject(VerificationRequestDto verificationRequestDto) {
         return new Subject.SubjectBuilder()
                .type(verificationRequestDto.getType())
                .identity(verificationRequestDto.getIdentity())
                .build();
    }
    private UserInfo buildUserInfo(UserInfoDto userInfoDto) {
        return new UserInfo.UserInfoBuilder()
                .userAgent(userInfoDto.getUserAgent())
                .ipAddress(userInfoDto.getClientIp().getBytes(StandardCharsets.UTF_8))
                .build();
    }

    private VerificationCreatedEvent buildVerificationCreatedEvent(Verification verification) {
        return new VerificationCreatedEvent.VerificationCreatedEventBuilder()
                .code(verification.getCode())
                .subject(verification.getSubject())
                .occurredAt(Instant.now())
                .build();
    }
    private VerificationConfirmedEvent buildVerificationConfirmedEvent(Verification verification) {
        return new VerificationConfirmedEvent.VerificationConfirmedEventBuilder()
                .code(verification.getCode())
                .subject(verification.getSubject())
                .occurredAt(Instant.now())
                .build();
    }
    private VerificationConfirmedFailedEvent buildVerificationConfirmedFailedEvent(Verification verification) {
        return new VerificationConfirmedFailedEvent.VerificationConfirmedFailedEventBuilder()
                .code(verification.getCode())
                .subject(verification.getSubject())
                .occurredAt(Instant.now())
                .build();
    }

    private String generateCode() {
        return String.valueOf(new Random().nextInt(NumericGeneratorUtils.generateNumeric(codeVerificationLength)));
    }

    public void setVerificationEventProducer(EventProducer<VerificationEvent> verificationEventProducer) {
        this.verificationEventProducer = verificationEventProducer;
    }
    public void setAttemptService(AttemptService attemptService) {
        this.attemptService = attemptService;
    }
    public void setCodeVerificationLength(Long codeVerificationLength) {
        this.codeVerificationLength = codeVerificationLength;
    }
    public void setExpirationService(ScheduleExpirationService expirationService) {
        this.expirationService = expirationService;
    }
    public void setVerificationRepository(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }
}
