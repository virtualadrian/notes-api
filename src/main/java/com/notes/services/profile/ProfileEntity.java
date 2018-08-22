package com.notes.services.profile;

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
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="profile_biography")
    private String profileBiography;

    @Column(name="profile_avatar")
    private String profileAvatar;

    @Column(name="created_ts")
    private LocalDateTime created;
}
