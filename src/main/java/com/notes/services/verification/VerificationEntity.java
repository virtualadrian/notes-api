package com.notes.services.verification;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
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
@Mapping(type = VerificationModel.class)
public class VerificationEntity extends BaseType {

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
