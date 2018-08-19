package com.notes.services.verification;

import com.notes.core.BaseCrudService;
import com.notes.security.entity.User;
import com.notes.security.repository.UserRepository;
import com.notes.services.account.AccountModel;
import com.notes.services.account.AccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationService extends BaseCrudService<VerificationModel, VerificationEntity, Long>  {

    private static final Integer AUTH_TOKEN_DURATION = 43200;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private UserRepository userRepository;

    public VerificationService() {
        super(VerificationModel.class, VerificationEntity.class);
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

    private SigningKeyResolver resetTokenKeyResolver = new SigningKeyResolverAdapter() {
        @Override
        public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
            return userRepository.findByEmail(claims.getSubject()).getPassword().getBytes();
        }
    };

    public Claims getResetTokenClaims(String token) {
        try {
            String decodedToken = new String(Base64.getUrlDecoder().decode(token));
            return Jwts.parser()
                .setSigningKeyResolver(resetTokenKeyResolver)
                .parseClaimsJws(decodedToken).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getResetJwt(User user) {
        String jwt = Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusSeconds(AUTH_TOKEN_DURATION)))
            .signWith(SignatureAlgorithm.HS256, user.getPassword().getBytes())
            .compact();
        return Base64.getUrlEncoder().encodeToString(jwt.getBytes());
    }
}
