package com.notes.services.account;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.Hashing;
import com.notes.core.BaseCrudService;
import com.notes.security.entity.User;
import com.notes.security.repository.RoleRepository;
import com.notes.security.repository.UserRepository;
import com.notes.security.util.SecurityUtil;
import com.notes.services.mail.MailService;
import com.notes.services.verification.VerificationModel;
import com.notes.services.verification.VerificationService;
import io.jsonwebtoken.Claims;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService extends BaseCrudService<AccountModel, AccountEntity, Long> {

    @Value("${spring.webapp.web-url}")
    private String webUrl;

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationService verificationService;
    private final MailService mailService;

    public AccountModel loadByUsername(String userName) {
        return this.toModel(accountRepository.findByUsername(userName));
    }

    Boolean sendPasswordReset(AccountModel account) throws AccountException {
        if (!emailExists(account.getEmail())) {
            throw new AccountException("Email address does not exist: " + account.getEmail());
        }

        User user = userRepository.findByEmail(account.getEmail());
        sendPasswordResetMessage(user, false);
        return true;
    }

    AccountModel createNewDisabledAccount(AccountModel newAccount) throws AccountException {
        if (emailExists(newAccount.getEmail())) {
            throw new AccountException("Email address: " + newAccount.getEmail());
        }
        AccountModel created = create(newAccount);
        User user = createUserForAccount(newAccount);
        sendPasswordResetMessage(user, true);
        return created;
    }

    private Claims validatePasswordChangeRequest(AccountConfirmationModel confirmation) {
        if (!SecurityUtil
            .validatePassword(confirmation.getPassword(), confirmation.getConfirmPassword())) {
            return null;
        }
        return verificationService.getResetTokenClaims(confirmation.getToken());
    }

    private User updateTokenUserPassword(Claims claims, AccountConfirmationModel confirmation) {
        User user = userRepository.findByEmail(claims.getSubject());
        setUserPassword(user, confirmation.getPassword());
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }

    Boolean resetUserPassword(AccountConfirmationModel confirmation) {
        Claims claims = validatePasswordChangeRequest(confirmation);
        if (claims == null) {
            return false;
        }
        updateTokenUserPassword(claims, confirmation);
        return true;
    }

    Boolean updateCurrentUserPassword(AccountPasswordModel passwordModel) {
        if (!SecurityUtil
            .validatePassword(passwordModel.getPassword(), passwordModel.getConfirmPassword())) {
            return false;
        }
        User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName());
        setUserPassword(user, passwordModel.getPassword());
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    private void sendPasswordResetMessage(User user, Boolean isRegistering) {

        Map<String, Object> mailbag = new HashMap<>();
        mailbag.put("firstName", user.getFirstName());
        mailbag.put("userName", user.getUsername());
        mailbag.put("email", user.getEmail());
        mailbag.put("webUrl", webUrl);
        mailbag.put("applicationName", "Noteler");
        mailbag.put("supportEmail", "info@noteler.com");
        mailbag.put("token", verificationService.getResetJwt(user));

        String template = isRegistering ? "newAccountEmail.html" : "passwordResetEmail.html";
        String subject = isRegistering ? String.format("Welcome! %s!", user.getFirstName())
            : "Noteler Password Reset";

        mailService.sendTemplatedMessage(user.getEmail(), "system@noteler.com",
            subject, template, mailbag);
    }

    private User createUserForAccount(AccountModel newAccount) {
        final User user = new User();
        String randomPass = DigestUtils.sha256Hex(Date.from(Instant.now()).toString());
        setUserPassword(user, randomPass);
        user.setUsername(newAccount.getUsername());
        user.setFirstName(newAccount.getFirstName());
        user.setLastName(newAccount.getLastName());
        user.setEmail(newAccount.getEmail());
        user.setEnabled(false);
        user.setLocked(false);
        user.setAccountExpirationDate(LocalDateTime.now().plusYears(99));
        user.setPasswordExpirationDate(LocalDateTime.now().plusYears(99));
        user.setRoles(Collections.singletonList(roleRepository.findByName("STANDARD_USER")));
        return userRepository.save(user);
    }

    private void setUserPassword(User user, String clearTextPass) {
        user.setPassword(passwordEncoder.encode(clearTextPass));
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
}
