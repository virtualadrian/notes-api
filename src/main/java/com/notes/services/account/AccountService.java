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
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends BaseCrudService<AccountModel, AccountEntity, Long> {

    @Value("${spring.webapp.web-url}")
    private String webUrl;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    MailService mailService;

    public AccountService() {
        super(AccountModel.class, AccountEntity.class);
    }

    public AccountModel loadCurrentUser() {
        return this.loadByUsername(SecurityUtil.getCurrentUserName());
    }

    public AccountModel loadByUsername(String userName) {
        return this.toModel(accountRepository.findByUsername(userName));
    }

    AccountModel createNewDisabledAccount(AccountModel newAccount) throws AccountException {
        if (emailExists(newAccount.getEmail())) {
            throw new AccountException("Email address: " + newAccount.getEmail());
        }
        AccountModel created = create(newAccount);
        User user = createUserForAccount(newAccount);
        sendPasswordResetMessage(user);
        return created;
    }

    Boolean updateUserPassword(AccountPasswordModel passwordModel) {
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

    Boolean checkTokenAndEnableUser(AccountConfirmationModel confirmation) {
        if (!SecurityUtil
            .validatePassword(confirmation.getPassword(), confirmation.getConfirmPassword())) {
            return false;
        }
        Claims claims = verificationService.getResetTokenClaims(confirmation.getToken());
        if(claims == null) {
            return false;
        }
        User user = userRepository.findByEmail(claims.getSubject());
        user.setPassword(confirmation.getPassword());
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    private void sendPasswordResetMessage(User user) {

        Map<String, Object> mailbag = new HashMap<>();
        mailbag.put("firstName", user.getFirstName());
        mailbag.put("userName", user.getUsername());
        mailbag.put("email", user.getEmail());
        mailbag.put("webUrl", webUrl);
        mailbag.put("applicationName", "Noteler");
        mailbag.put("supportEmail", "info@noteler.com");
        mailbag.put("token", verificationService.getResetJwt(user));

        mailService.sendTemplatedMessage(user.getEmail(), "system@noteler.com",
            String.format("Welcome! %s!", user.getFirstName()), "newAccountEmail.html",
            mailbag);
    }

    private User createUserForAccount(AccountModel newAccount) {
        final User user = new User();
        String randomPass = DigestUtils.sha256Hex(LocalDateTime.from(Instant.now()).toString());
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
