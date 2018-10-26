package com.notes.services.verification;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Mapping(type = VerificationEntity.class)
public class VerificationModel extends BaseType {
    public VerificationModel() {}
    public VerificationModel(Long userId, String token, LocalDateTime tokenExpirationDate) {
        this.userId = userId;
        this.token = token;
        this.tokenExpirationDate = tokenExpirationDate;
    }
    private long id;
    private Long userId;
    private String token;
    private LocalDateTime tokenExpirationDate;
}
