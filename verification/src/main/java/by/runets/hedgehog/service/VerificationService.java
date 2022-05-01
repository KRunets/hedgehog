package by.runets.hedgehog.service;

import by.runets.hedgehog.resource.dto.UserInfoDto;
import by.runets.hedgehog.resource.dto.VerificationRequestDto;

import java.util.UUID;

public interface VerificationService {
    UUID createVerification(VerificationRequestDto verificationDto, UserInfoDto userInfoDto);

    boolean confirmVerification(UUID verificationId, String code, UserInfoDto userInfoDto);
}
