package com.notes.services.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, Long> {
    VerificationEntity findByToken(String token);
    VerificationEntity findByTokenAndTokenExpirationDateIsAfter(String token, LocalDateTime expirationDate);
}
