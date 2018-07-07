package com.notes.services.account;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.Hashing;
import com.notes.core.BaseCrudService;
import com.notes.security.entity.User;
import com.notes.security.helper.AccountAwareOAuth2Request;
import com.notes.security.repository.RoleRepository;
import com.notes.security.repository.UserRepository;
import com.notes.services.mail.MailService;
import com.notes.services.verification.VerificationModel;
import com.notes.services.verification.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Service
public class AccountService extends BaseCrudService<AccountModel, AccountEntity, Long>  {

    @Value("${site.settings.web-url}")
    private String siteUrl;

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

    public AccountAwareOAuth2Request getCurrentAccountAuth() {
        OAuth2Authentication authentication =
            (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return  ((AccountAwareOAuth2Request) authentication.getOAuth2Request());
    }

    public String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Long getCurrentUserAccountId() {
        return getCurrentAccountAuth().getAccountId();
    }

    public AccountModel loadCurrentUser() {
        return this.loadByUsername(getCurrentUserName());
    }

    public AccountModel loadByUsername(String userName) {
        return this.toModel(accountRepository.findByUsername(userName));
    }

    AccountModel createNewDisabledAccount(AccountModel newAccount) throws AccountException {
        if (emailExists(newAccount.getEmail())) {
            throw new AccountException("Email address: " + newAccount.getEmail());
        }
        sendRegisterWelcomeMessage(newAccount, createVerificationTokenForUser(createUserForAccount(newAccount)));
        return create(newAccount);
    }

    Boolean checkTokenAndEnableUser(String token) {
        VerificationModel verificationToken = verificationService.getValidOrNull(token);
        if(verificationToken == null) return false;
        User user = userRepository.findOne(verificationToken.getUserId());
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    void sendRegisterWelcomeMessage(AccountModel newAccount, String newAccountToken){
        Map<String, Object> modelObject = ImmutableMap.of(
            "firstName", newAccount.getFirstName(),
            "accountConfirmUrl", String.format("%s/account/register/confirm/%s", siteUrl, newAccountToken),
            "userName", newAccount.getFirstName(),
            "userEmail", newAccount.getEmail(),
            "ourURL", siteUrl
        );

        mailService.sendTemplatedMessage(newAccount.getEmail(), "system@llty.com",
            String.format("Welcome! %s", newAccount.getFirstName()), "newAccountEmail.html", modelObject);
    }

    private String createVerificationTokenForUser(User user) {
        String token = Hashing.sha256().hashString(LocalDateTime.now().toString(), StandardCharsets.UTF_8).toString();
        verificationService.create(new VerificationModel(user.getId(), token, LocalDateTime.now().plusDays(3)));
        return token;
    }

    private User createUserForAccount(AccountModel newAccount) {
        final User user = new User();
        user.setPassword(passwordEncoder.encode(newAccount.getPassword()));
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

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
}
