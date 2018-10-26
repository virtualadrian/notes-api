package com.notes.services.account;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
import lombok.Data;

@Data
@Mapping(type = AccountEntity.class)
public class AccountModel extends BaseType {
    private long id;
    private String username;
    private String email;
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
