package com.notes.services.account;

import com.notes.core.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="account")
public class AccountEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="username")
    private String username;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="middle_initial")
    private String middleInitial;

    @Column(name="address_line_one")
    private String addressLineOne;

    @Column(name="address_line_two")
    private String addressLineTwo;

    @Column(name="address_line_three")
    private String addressLineThree;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="zip")
    private String zip;

    @Column(name="email")
    private String eMail;
}