package com.notes.services.account;

import lombok.Data;

@Data
public class AccountConfirmationModel {
    private String token;
    private String password;
    private String confirmPassword;
}
