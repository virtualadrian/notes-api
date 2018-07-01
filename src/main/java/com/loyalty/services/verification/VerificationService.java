package com.notes.services.verification;

import com.notes.core.BaseCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationService extends BaseCrudService<VerificationModel, VerificationEntity, Long>  {

    @Autowired
    private VerificationRepository verificationRepository;

    public VerificationService() {
        super(VerificationModel.class, VerificationEntity.class);
    }

    public VerificationModel getValidOrNull(String token) {
        return getByTokenAndExpirationDateAfter(token, LocalDateTime.now());
    }

    private VerificationModel getByToken(String token) {
        return toModel(verificationRepository.findByToken(token));
    }

    private VerificationModel getByTokenAndExpirationDateAfter(String token, LocalDateTime expiration) {
        VerificationModel tokenModel = new VerificationModel();

        Optional.ofNullable(verificationRepository.findByTokenAndTokenExpirationDateIsAfter(token, expiration))
            .ifPresent(tokenEntity -> {
                tokenModel.setId(tokenEntity.getId());
                tokenModel.setToken(tokenEntity.getToken());
                tokenModel.setUserId(tokenEntity.getUserId());
                tokenModel.setTokenExpirationDate(tokenEntity.getTokenExpirationDate());
            });

        return StringUtils.isNotBlank(tokenModel.getToken()) ? tokenModel : null;
    }
}
