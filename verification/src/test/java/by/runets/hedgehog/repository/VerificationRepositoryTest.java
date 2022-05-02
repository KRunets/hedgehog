package by.runets.hedgehog.repository;

import by.runets.hedgehog.domain.Subject;
import by.runets.hedgehog.domain.UserInfo;
import by.runets.hedgehog.domain.Verification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static by.runets.hedgehog.utils.Constants.MOBILE_CONFIRMATION;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VerificationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private VerificationRepository verificationRepository;

    @Test
    public void testReadPendingVerificationByIdentity() {
        // given
        final Verification expected = new Verification.VerificationBuilder()
                .code("12345678")
                .subject(new Subject.SubjectBuilder()
                        .type(MOBILE_CONFIRMATION)
                        .identity("test999@gmail.com")
                        .build()
                )
                .userInfo(new UserInfo.UserInfoBuilder()
                        .ipAddress("127.0.0.1".getBytes(StandardCharsets.UTF_8))
                        .userAgent("test_user_agent")
                        .build()
                )
                .build();

        entityManager.persist(expected);
        entityManager.flush();

        // when
        final Verification actual = verificationRepository.readPendingVerificationByIdentity("test999@gmail.com");

        // then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void testExpireVerification() {
        // given
        final Verification expected = new Verification.VerificationBuilder()
                .code("12345678")
                .subject(new Subject.SubjectBuilder()
                        .type(MOBILE_CONFIRMATION)
                        .identity("test999@gmail.com")
                        .build()
                )
                .userInfo(new UserInfo.UserInfoBuilder()
                        .ipAddress("127.0.0.1".getBytes(StandardCharsets.UTF_8))
                        .userAgent("test_user_agent")
                        .build()
                )
                .build();

        entityManager.persist(expected);
        entityManager.flush();

        // when
        verificationRepository.expireVerification(expected.getId());
        entityManager.refresh(expected);
        final Verification actual = verificationRepository.readPendingVerificationByIdentity("test999@gmail.com");

        // then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
        Assert.assertTrue(expected.isExpired());
    }

    @Test
    @Transactional
    public void testConfirmVerification() {
        // given
        final Verification expected = new Verification.VerificationBuilder()
                .code("12345678")
                .subject(new Subject.SubjectBuilder()
                        .type(MOBILE_CONFIRMATION)
                        .identity("test999@gmail.com")
                        .build()
                )
                .userInfo(new UserInfo.UserInfoBuilder()
                        .ipAddress("127.0.0.1".getBytes(StandardCharsets.UTF_8))
                        .userAgent("test_user_agent")
                        .build()
                )
                .build();

        entityManager.persist(expected);
        entityManager.flush();

        // when
        verificationRepository.confirmVerification(expected.getId());
        entityManager.refresh(expected);
        final Optional<Verification> actual = verificationRepository.findById(expected.getId());

        // then
        Assert.assertTrue(actual.isPresent());
        Assert.assertTrue(expected.isConfirmed());
    }
}
