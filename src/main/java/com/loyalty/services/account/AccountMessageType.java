package com.notes.services.account;

public enum AccountMessageType {
    ACCOUNT_OK("ACCOUNT_OK"),
    ACCOUNT_EXISTS("ACCOUNT_EXISTS"),
    ACCOUNT_EMAIL_INVALID("ACCOUNT_EMAIL_INVALID"),
    ACCOUNT_ACTIVATION_INVALID("ACCOUNT_ACTIVATION_INVALID"),
    ACCOUNT_PASSWORD_INVALID("ACCOUNT_PASSWORD_INVALID"),
    ACCOUNT_PASSWORD_INCORRECT("ACCOUNT_PASSWORD_INCORRECT");

    private String value;

    AccountMessageType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
