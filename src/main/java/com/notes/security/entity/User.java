package com.notes.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name = "app_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String email;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "account_expiration_date")
    private LocalDateTime accountExpirationDate;

    @Column(name = "password_expiration_date")
    private LocalDateTime passwordExpirationDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Optional.ofNullable(accountExpirationDate)
            .map(ad -> LocalDateTime.now().isBefore(ad))
            .orElse(false);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Optional.ofNullable(passwordExpirationDate)
            .map(pd -> LocalDateTime.now().isBefore(pd))
            .orElse(false);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Roles are being eagerly loaded here because
     * they are a fairly small collection of items.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns
        = @JoinColumn(name = "user_id",
        referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id",
            referencedColumnName = "id"))
    private List<Role> roles;
}

