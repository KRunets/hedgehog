package by.runets.hedgehog.repository;

import by.runets.hedgehog.domain.verification.Verification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VerificationRepository extends CrudRepository<Verification, UUID> { }
