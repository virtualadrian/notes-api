package com.notes.security.helper;

import org.springframework.security.oauth2.provider.OAuth2Request;

public class AccountAwareOAuth2Request extends OAuth2Request {
    public AccountAwareOAuth2Request(OAuth2Request originalOAuth2Request) {
        super(originalOAuth2Request);
    }

    private Long accountId;
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
