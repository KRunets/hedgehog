package by.runets.hedgehog.repository;

import by.runets.hedgehog.domain.Verification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VerificationRepository extends CrudRepository<Verification, UUID> {
    @Query("FROM verification WHERE identity=?1 and confirmed=false")
    Verification readPendingVerificationByIdentity(String identity);

    @Modifying
    @Query("UPDATE verification v SET v.expired=true WHERE v.id =?1 and expired=false")
    Integer expireVerification(UUID id);
}
