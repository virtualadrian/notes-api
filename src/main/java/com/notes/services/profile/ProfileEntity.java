package com.notes.services.profile;

import com.notes.core.BaseType;
import com.notes.core.Mapping;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="profile")
@Mapping(type = ProfileModel.class)
public class ProfileEntity extends BaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="profile_biography")
    private String profileBiography;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "profile_company")
    private String profileCompany;

    @Column(name = "profile_location")
    private String profileLocation;

    @Column(name = "profile_twitter")
    private String profileTwitter;

    @Column(name = "profile_linked_in")
    private String profileLinkedIn;

    @Column(name = "profile_facebook")
    private String profileFacebook;

    @Column(name = "profile_google_plus")
    private String profileGooglePlus;

    @Column(name="profile_avatar")
    private String profileAvatar;

    @Column(name="created_ts")
    private LocalDateTime created;
}
