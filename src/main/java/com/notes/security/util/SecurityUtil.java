package com.notes.security.util;

import com.notes.security.helper.AccountAwareOAuth2Request;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.HistoryRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordData.HistoricalReference;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class SecurityUtil {

    public static AccountAwareOAuth2Request getCurrentAccountAuth() {
        OAuth2Authentication authentication =
            (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return ((AccountAwareOAuth2Request) authentication.getOAuth2Request());
    }

    public static String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static Long getCurrentUserAccountId() {
        return getCurrentAccountAuth().getAccountId();
    }


    public static Boolean validatePassword(String password, String confirmPassword) {

        // equality
        if (!password.equals(confirmPassword)) { return false; }

        // validate password form
        PasswordValidator validator = new PasswordValidator(
            new LengthRule(8, 50), new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1));

        PasswordData passwordData = new PasswordData(password);
        PasswordData confirmPasswordData = new PasswordData(confirmPassword);

        RuleResult ruleResult = validator.validate(passwordData);
        RuleResult ruleResultConfirm = validator.validate(confirmPasswordData);

        return ruleResult.isValid() && ruleResultConfirm.isValid();
    }
}
