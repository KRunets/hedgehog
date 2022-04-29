package by.runets.hedgehog.repository;

import by.runets.hedgehog.domain.Attempt;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttemptRepository extends CrudRepository<Attempt, UUID> {
    @Query("FROM attempt a WHERE a.verificationId=?1")
    Attempt readAttemptByVerificationId(UUID verificationId);

    @Modifying
    @Query("UPDATE attempt a SET times=?2 WHERE a.verificationId=?1")
    void incrementAttempt(UUID verificationId, Integer times);
}
