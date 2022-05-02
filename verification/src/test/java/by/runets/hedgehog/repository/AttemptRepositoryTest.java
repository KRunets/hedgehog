package by.runets.hedgehog.repository;

import by.runets.hedgehog.domain.Attempt;
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
import java.util.UUID;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AttemptRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AttemptRepository attemptRepository;

    @Test
    @Transactional
    public void testExpireVerification() {
        // given
        final Attempt attempt = new Attempt.AttemptBuilder()
                .verificationId(UUID.randomUUID())
                .userInfo(new UserInfo.UserInfoBuilder().ipAddress("127.0.0.1".getBytes(StandardCharsets.UTF_8)).userAgent("tst_usr_agnt").build())
                .times(200)
                .build();

        entityManager.persist(attempt);
        entityManager.flush();

        // when
        attemptRepository.incrementAttempt(attempt.getId(), 201);
        entityManager.refresh(attempt);
        final Optional<Attempt> actual = attemptRepository.findById(attempt.getId());

        // then
        Assert.assertTrue(actual.isPresent());
    }
}
