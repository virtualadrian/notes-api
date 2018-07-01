package com.notes.services.verification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerificationModel {
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
