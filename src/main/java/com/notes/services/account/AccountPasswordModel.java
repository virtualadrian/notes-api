package com.notes.services.account;

import lombok.Data;

@Data
public class AccountPasswordModel {
    private String password;
    private String confirmPassword;
}
