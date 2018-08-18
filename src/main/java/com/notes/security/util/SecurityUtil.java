package com.notes.security.util;

import com.notes.security.helper.AccountAwareOAuth2Request;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class SecurityUtil {

    public static AccountAwareOAuth2Request getCurrentAccountAuth() {
        OAuth2Authentication authentication =
            (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return  ((AccountAwareOAuth2Request) authentication.getOAuth2Request());
    }

    public static String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static Long getCurrentUserAccountId() {
        return getCurrentAccountAuth().getAccountId();
    }
}
