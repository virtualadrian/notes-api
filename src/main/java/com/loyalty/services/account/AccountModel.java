package com.notes.services.account;

import lombok.Data;

@Data
public class AccountModel {
    private long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String addressLineOne;
    private String addressLineTwo;
    private String addressLineThree;
    private String city;
    private String state;
    private String zip;
    private String eMail;
    private Boolean enabled;

}
