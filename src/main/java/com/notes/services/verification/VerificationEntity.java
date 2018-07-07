package com.notes.services.verification;

import com.notes.core.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="verification")
public class VerificationEntity extends BaseEntity {

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
