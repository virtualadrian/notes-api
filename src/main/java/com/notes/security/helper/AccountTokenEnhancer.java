package com.notes.security.helper;

import com.notes.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AccountTokenEnhancer extends JwtAccessTokenConverter {

    @Autowired
    private AccountService accountService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken enhancedAccessToken = new DefaultOAuth2AccessToken(accessToken);
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

        Optional.ofNullable(accountService.loadByUsername(authentication.getName()))
            .ifPresent(account -> {
                info.put("accountId", account.getId());
                info.put("firstName", account.getFirstName());
            });

        enhancedAccessToken.setAdditionalInformation(info);

        return super.enhance(enhancedAccessToken, authentication);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        AccountAwareOAuth2Request accountAwareOAuth2Request =
            new AccountAwareOAuth2Request(authentication.getOAuth2Request());

        accountAwareOAuth2Request.setAccountId(Long.valueOf(map.get("accountId").toString()));
        accountAwareOAuth2Request.setFirstName(map.get("firstName").toString());

        return new OAuth2Authentication(accountAwareOAuth2Request,  authentication.getUserAuthentication());
    }
}
