package com.notes.services.verification;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="verification")
public class VerificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="token")
    private String token;

    @Column(name="token_expiration_date")
    private LocalDateTime tokenExpirationDate;
}
