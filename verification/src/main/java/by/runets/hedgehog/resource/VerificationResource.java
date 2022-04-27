package by.runets.hedgehog.resource;

import by.runets.hedgehog.resource.dto.UserInfoDto;
import by.runets.hedgehog.resource.dto.VerificationRequestDto;
import by.runets.hedgehog.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/verifications")
public class VerificationResource {

    private static final String USER_AGENT = "user-agent";

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/")
    public ResponseEntity<UUID> createVerification(@Valid @RequestBody VerificationRequestDto verificationRequestDto, HttpServletRequest request) {

        final UserInfoDto userInfoDto = buildUserInfoFromRequest(request);
        final UUID verificationUUID = verificationService.createVerification(verificationRequestDto, userInfoDto);

        return ResponseEntity.ok(verificationUUID);
    }

    private UserInfoDto buildUserInfoFromRequest(HttpServletRequest httpServletRequest) {

        final String userAgent = httpServletRequest.getHeader(USER_AGENT);
        final String ipAddress = httpServletRequest.getRemoteAddr();

        return new UserInfoDto.UserInfoDtoBuilder()
                .userAgent(userAgent)
                .clientIp(ipAddress)
                .build();
    }

    public void setVerificationService(VerificationService verificationService) {
        this.verificationService = verificationService;
    }
}
