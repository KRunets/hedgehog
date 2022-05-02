package by.runets.hedgehog.service;

import by.runets.hedgehog.domain.Attempt;
import by.runets.hedgehog.domain.Subject;
import by.runets.hedgehog.domain.UserInfo;
import by.runets.hedgehog.domain.Verification;
import by.runets.hedgehog.producer.EventProducer;
import by.runets.hedgehog.repository.AttemptRepository;
import by.runets.hedgehog.repository.VerificationRepository;
import by.runets.hedgehog.resource.dto.UserInfoDto;
import by.runets.hedgehog.resource.dto.VerificationRequestDto;
import by.runets.hedgehog.service.impl.AttemptServiceImpl;
import by.runets.hedgehog.service.impl.ExpirationServiceImpl;
import by.runets.hedgehog.service.impl.ScheduleExpirationServiceImpl;
import by.runets.hedgehog.service.impl.VerificationServiceImpl;
import org.hibernate.mapping.Any;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static by.runets.hedgehog.utils.Constants.EMAIL_CONFIRMATION;
import static by.runets.hedgehog.utils.Constants.MOBILE_CONFIRMATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
public class VerificationServiceTest {

    @TestConfiguration
    static class VerificationServiceImplTestContextConfiguration {



        @Bean
        public VerificationService templateService() {
            return new VerificationServiceImpl();
        }

        @Bean
        public ScheduleExpirationService scheduleExpirationService() {
            return new ScheduleExpirationServiceImpl();
        }

        @Bean
        public ScheduledTaskRegistrar scheduledTaskRegistrar() {
            return new ScheduledTaskRegistrar();
        }

        @Bean
        public ExpirationService expirationService() {
            return new ExpirationServiceImpl();
        }

        @Bean
        public TaskScheduler threadPoolTaskScheduler() {
            ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
            scheduler.setPoolSize(20);
            return scheduler;
        }

        @Bean
        public ConcurrentMap<UUID, ScheduledTask> tasksForTermination() {
            return new ConcurrentHashMap<>();
        }

        @Bean
        public BlockingQueue<UUID> executedVerificationQueue() {
            return new ArrayBlockingQueue<>(32);
        }
    }

    @Autowired
    private VerificationService verificationService;

    @MockBean
    private VerificationRepository verificationRepository;

    @MockBean
    private AttemptRepository attemptRepository;

    @MockBean
    private EventProducer eventProducer;

    @MockBean
    private AttemptService attemptService;

    @Before
    public void setUp() {
    }

    @Test
    public void testRenderTemplateBySlug() {
        final VerificationRequestDto verificationRequestDto = new VerificationRequestDto.VerificationDtoBuilder()
                .type(EMAIL_CONFIRMATION)
                .identity("test@gmail.com")
                .build();

        final UserInfoDto userInfoDto = new UserInfoDto.UserInfoDtoBuilder()
                .clientIp("127.0.0.1")
                .userAgent("test_user_agent")
                .build();

        final Verification expected = new Verification.VerificationBuilder()
                .code("12345678")
                .subject(new Subject.SubjectBuilder()
                        .type(MOBILE_CONFIRMATION)
                        .identity("test@gmail.com")
                        .build()
                )
                .userInfo(new UserInfo.UserInfoBuilder()
                        .ipAddress("127.0.0.1".getBytes(StandardCharsets.UTF_8))
                        .userAgent("test_user_agent")
                        .build()
                )
                .build();


        Mockito.when(verificationRepository.save(any())).thenReturn(expected);
        final UUID actualUUID = verificationService.createVerification(verificationRequestDto, userInfoDto);

        Assert.assertEquals(actualUUID,  expected.getId());
    }
}
