package com.notes.services.account;

import com.notes.core.BaseCrudService;
import com.notes.security.entity.User;
import com.notes.security.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountVerificationService {

    private static final Integer AUTH_TOKEN_DURATION = 43200;

    private final UserRepository userRepository;

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
